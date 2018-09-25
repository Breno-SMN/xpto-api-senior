package br.com.xpto.resource.errors;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessageBuilder {
  private String userMessage;
  private String developerMessage;
  private int errorCode;
  
  public ErrorMessageBuilder() {}
  
  public ErrorMessageBuilder withUserMessage(String userMessage) { this.userMessage = userMessage;
    return this;
  }
  
  public ErrorMessageBuilder withDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
    return this;
  }
  
  public ErrorMessageBuilder withErrorCode(int errorCode) {
    this.errorCode = errorCode;
    return this;
  }
  
  public ErrorMessage build() {
    return new ErrorMessage(userMessage, developerMessage, errorCode);
  }
}
