package br.com.xpto.resource.exceptions;

public class CsvUnsuportedException extends BaseResourceException
{
  private static final long serialVersionUID = 2308524399006039239L;
  
  public CsvUnsuportedException(Object... parameters) {
    super(parameters);
  }
}
