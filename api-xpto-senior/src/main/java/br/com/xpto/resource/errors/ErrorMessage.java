package br.com.xpto.resource.errors;

import java.io.Serializable;

public class ErrorMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String userMessage;
  private String developerMessage;
  private int errorCode;
  private final String moreInfo;
  
  public ErrorMessage(String userMessage, String developerMessage, int errorCode)
  {
    this.userMessage = userMessage;
    this.developerMessage = developerMessage;
    this.errorCode = errorCode;
    moreInfo = "Consultar os desenvolvedores";
  }
  
  public String getUserMessage() {
    return userMessage;
  }
  
  public String getDeveloperMessage() {
    return developerMessage;
  }
  
  public int getErrorCode() {
    return errorCode;
  }
  
  public String getMoreInfo() {
    return moreInfo;
  }
}
