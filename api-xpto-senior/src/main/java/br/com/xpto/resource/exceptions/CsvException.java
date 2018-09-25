package br.com.xpto.resource.exceptions;

public class CsvException extends BaseResourceException
{
  private static final long serialVersionUID = 2308524399006039239L;
  
  public CsvException(Object... parameters) {
    super(parameters);
  }
}
