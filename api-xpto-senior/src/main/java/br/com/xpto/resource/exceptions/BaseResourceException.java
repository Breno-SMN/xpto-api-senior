package br.com.xpto.resource.exceptions;

public class BaseResourceException
  extends RuntimeException
{
  private static final long serialVersionUID = 6011730611878683208L;
  private final Object[] parameters;
  
  public BaseResourceException(Object... parameters)
  {
    this.parameters = parameters;
  }
  
  public Object[] getParameters() {
    return parameters;
  }
}
