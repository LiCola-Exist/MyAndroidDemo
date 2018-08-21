package com.example.licola.myandroiddemo.data.model;

import java.util.List;

/**
 * Created by LiCola on 2018/5/19.
 */
public class JsonType {

  /**
   * status : 0
   * msg :
   * resultArray : [{"type":"JsonArrayItem"}]
   * resultObj : {"type":"JsonObject"}
   * resultStr : String
   */

  private int status;
  private String msg;
  private ResultObjBean resultObj;
  private String resultStr;
  private List<ResultArrayBean> resultArray;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("JsonType{");
    sb.append("status=").append(status);
    sb.append(", msg='").append(msg).append('\'');
    sb.append(", resultObj=").append(resultObj);
    sb.append(", resultStr='").append(resultStr).append('\'');
    sb.append(", resultArray=").append(resultArray);
    sb.append('}');
    return sb.toString();
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ResultObjBean getResultObj() {
    return resultObj;
  }

  public void setResultObj(ResultObjBean resultObj) {
    this.resultObj = resultObj;
  }

  public String getResultStr() {
    return resultStr;
  }

  public void setResultStr(String resultStr) {
    this.resultStr = resultStr;
  }

  public List<ResultArrayBean> getResultArray() {
    return resultArray;
  }

  public void setResultArray(List<ResultArrayBean> resultArray) {
    this.resultArray = resultArray;
  }

  public static class ResultObjBean {

    /**
     * type : JsonObject
     */

    private String type;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("ResultObjBean{");
      sb.append("type='").append(type).append('\'');
      sb.append('}');
      return sb.toString();
    }
  }

  public static class ResultArrayBean {

    /**
     * type : JsonArrayItem
     */

    private String type;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("ResultArrayBean{");
      sb.append("type='").append(type).append('\'');
      sb.append('}');
      return sb.toString();
    }
  }
}
