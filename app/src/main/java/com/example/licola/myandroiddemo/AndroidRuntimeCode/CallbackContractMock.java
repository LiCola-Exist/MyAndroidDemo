package com.example.licola.myandroiddemo.AndroidRuntimeCode;

import java.util.List;

/**
 * Created by 李可乐 on 2017/4/21.
 */

public class CallbackContractMock {
  interface Callback  {
    void failure( int code, String errorMsg);
  }

  interface CallbackWithList<T> extends Callback {
    void success(List<T> result, Object... extra);
  }

  interface CallbackWithModel<T> extends Callback {
    void success(T result);
  }

  interface CallbackWithVoid extends Callback {

    void success();
  }
}
