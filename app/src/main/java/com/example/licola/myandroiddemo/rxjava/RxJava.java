package com.example.licola.myandroiddemo.rxjava;

import android.support.annotation.NonNull;
import com.licola.llogger.LLogger;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;

/**
 * Created by LiCola on 2018/5/29.
 */
public class RxJava {

  public static void main() {

//    createObservableZipAndMap().subscribe(getObserver());
//    createObservableFlatMap().subscribe(getObserver());
//    Observable.just("1","2","1","2","3","1").distinct().subscribe(getObserver());//hast 去重
//    singleJust();
//    testScheduler();
    Observable.fromCallable(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return "123";
      }
    }).subscribe(getObserver());
  }

  private static void testScheduler() {
    Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("1");
        LLogger.d(Thread.currentThread());
        LLogger.trace();
        emitter.onComplete();
      }
    })
        .subscribeOn(Schedulers.newThread())//subscribeOn 被订阅者发生线程 即事件发生现在
        .observeOn(AndroidSchedulers.mainThread())//observeOn 观察者发生 即定义下游Observe回调发生的线程
        .subscribe(getObserver());
  }

  private static void singleJust() {
    Disposable subscribe = Single.just("1").subscribe(new Consumer<String>() {
      @Override
      public void accept(String s) throws Exception {
        LLogger.d(s);
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
        LLogger.d(throwable);
      }
    });
  }

  private static Observable<String> createObservableFlatMap() {
    return createBaseObservable1().flatMap(new Function<String, ObservableSource<String>>() {
      @Override
      public ObservableSource<String> apply(final String s) throws Exception {
        LLogger.d(s);

        return Observable.create(new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("flat map 1:" + s);
            emitter.onNext("flat map 2:" + s);
          }
        });
      }
    });
  }

  private static Observable<String> createObservableZipAndMap() {
    return Observable
        .zip(createBaseObservable1(), Observable.create(new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("value second 3");
            emitter.onNext("value second 2");
            emitter.onComplete();
          }
        }), new BiFunction<String, String, String>() {
          @Override
          public String apply(String integer, String integer2) throws Exception {
            LLogger.d(integer, integer2);
            return integer + " zip " + integer2;
          }
        }).map(new Function<String, String>() {
          @Override
          public String apply(String s) throws Exception {
            LLogger.d(s);
            return "map:" + s;
          }
        });
  }

  private static Observable<String> createBaseObservable1() {
    return Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("value first 1");
        emitter.onNext("value first 2");
        emitter.onNext("value first 3");
        emitter.onComplete();
      }
    });
  }

  @NonNull
  private static Observer<String> getObserver() {
    return new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {
        LLogger.d(d);
//        d.dispose();//切断事件流
      }

      @Override
      public void onNext(String s) {
        LLogger.d(s, Thread.currentThread());
//        LLogger.trace();
      }

      @Override
      public void onError(Throwable e) {
        LLogger.e(e);
      }

      @Override
      public void onComplete() {
        LLogger.d();

      }
    };
  }
}
