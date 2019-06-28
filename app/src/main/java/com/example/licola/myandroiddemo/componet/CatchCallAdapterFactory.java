package com.example.licola.myandroiddemo.componet;

import com.licola.llogger.LLogger;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author LiCola
 * @date 2019-06-28
 */
public class CatchCallAdapterFactory extends CallAdapter.Factory {

  final Executor callbackExecutor;

  public CatchCallAdapterFactory(Executor callbackExecutor) {
    this.callbackExecutor = callbackExecutor;
  }

  @Nullable
  @Override
  public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
    if (getRawType(returnType) != Call.class) {
      return null;
    }
    final Type responseType = getCallResponseType(returnType);
    return new CallAdapter<Object, Call<?>>() {
      @Override
      public Type responseType() {
        return responseType;
      }

      @Override
      public Call<Object> adapt(Call<Object> call) {
        return new CatchExecutorCallbackCall<>(callbackExecutor, call);
      }
    };
  }


  static final class CatchExecutorCallbackCall<T> implements Call<T> {

    final Executor callbackExecutor;
    final Call<T> delegate;

    public CatchExecutorCallbackCall(Executor callbackExecutor, Call<T> delegate) {
      this.callbackExecutor = callbackExecutor;
      this.delegate = delegate;
    }

    @Override
    public void enqueue(Callback<T> callback) {
      delegate.enqueue(new Callback<T>() {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
          callbackExecutor.execute(new Runnable() {
            @Override
            public void run() {
              if (delegate.isCanceled()) {
                callback.onFailure(CatchExecutorCallbackCall.this, new IOException("Canceled"));
              } else {

                try {
                  callback.onResponse(CatchExecutorCallbackCall.this, response);
                } catch (RuntimeException e) {
                  LLogger.e(e);
                }
              }
            }
          });
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {

          callbackExecutor.execute(new Runnable() {
            @Override
            public void run() {
              callback.onFailure(call, t);
            }
          });
        }
      });
    }

    @Override
    public boolean isExecuted() {
      return delegate.isExecuted();
    }

    @Override
    public Response<T> execute() throws IOException {
      return delegate.execute();
    }

    @Override
    public void cancel() {
      delegate.cancel();
    }

    @Override
    public boolean isCanceled() {
      return delegate.isCanceled();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone") // Performing deep clone.
    @Override
    public Call<T> clone() {
      return new CatchExecutorCallbackCall<>(callbackExecutor, delegate.clone());
    }

    @Override
    public Request request() {
      return delegate.request();
    }

  }

  static Type getCallResponseType(Type returnType) {
    if (!(returnType instanceof ParameterizedType)) {
      throw new IllegalArgumentException(
          "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
    }
    return getParameterUpperBound(0, (ParameterizedType) returnType);
  }
}
