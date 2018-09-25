package br.com.xpto.resource.exceptions;

public class UnauthorizedException extends BaseResourceException
{
  private static final long serialVersionUID = -5542156554962899803L;
  
  public UnauthorizedException(Object... parameters) {
    super(parameters);
  }
}
