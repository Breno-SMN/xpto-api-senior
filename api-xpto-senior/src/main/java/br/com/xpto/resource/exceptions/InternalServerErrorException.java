package br.com.xpto.resource.exceptions;

public class InternalServerErrorException extends BaseResourceException
{
  private static final long serialVersionUID = 2308524399006039239L;
  
  public InternalServerErrorException(Object... parameters) {
    super(parameters);
  }
}
