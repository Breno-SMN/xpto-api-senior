package br.com.xpto.resource.exceptions;

public class CityFieldInvalidException extends BaseResourceException
{
  private static final long serialVersionUID = 2308524399006039239L;
  
  public CityFieldInvalidException(Object... parameters) {
    super(parameters);
  }
}
