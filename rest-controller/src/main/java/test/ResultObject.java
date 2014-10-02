package test;

public class ResultObject {

  private String method;
  private boolean result;
  
  public ResultObject() {
    
  }
  
  public ResultObject(String method, boolean result) {
    super();
    this.method = method;
    this.result = result;
  }
  
  public String getMethod() {
    return this.method;
  }
  public void setMethod(String method) {
    this.method = method;
  }
  public boolean isResult() {
    return this.result;
  }
  public void setResult(boolean result) {
    this.result = result;
  }
  
}
