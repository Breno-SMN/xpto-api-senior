package br.com.xpto.resource.exceptions.advice;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.xpto.resource.errors.ErrorMessage;
import br.com.xpto.resource.errors.ErrorMessageBuilder;
import br.com.xpto.resource.exceptions.CityException;
import br.com.xpto.resource.exceptions.CsvConvertException;
import br.com.xpto.resource.exceptions.CsvDirectoryFailCreateException;
import br.com.xpto.resource.exceptions.CsvException;
import br.com.xpto.resource.exceptions.CsvUnsuportedException;
import br.com.xpto.resource.exceptions.EntityNotFoundException;
import br.com.xpto.resource.exceptions.InternalServerErrorException;
import br.com.xpto.resource.exceptions.UnauthorizedException;




@ControllerAdvice(basePackages={"com.ciandt"})
public class ExceptionResourceAdvice
{
  @Autowired
  private ErrorMessageBuilder errorBuilder;
  
  public ExceptionResourceAdvice() {}
  
  @ResponseBody
  @ExceptionHandler({UnauthorizedException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorMessage exceptionHandler(UnauthorizedException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("Unauthorized - {0}", ex.getParameters())).withUserMessage("You are not authorized to perform this operation").withErrorCode(30001).build();
  }
  
  @ResponseBody
  @ExceptionHandler({CityException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage exceptionHandler(CityException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("Internal Server Error - {0}", ex.getParameters())).withUserMessage("Houve um erro ao listar os estados").withErrorCode(30001).build();
  }
  
  @ResponseBody
  @ExceptionHandler({CsvException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage exceptionHandler(CsvException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("Internal Server Error - {0}", ex.getParameters())).withUserMessage("Houve um erro e o arquivo nao foi salvo.").withErrorCode(30001).build();
  }
  
  @ResponseBody
  @ExceptionHandler({CsvDirectoryFailCreateException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage exceptionHandler(CsvDirectoryFailCreateException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("Internal Server Error - {0}", ex.getParameters())).withUserMessage("Houve um erro e não foi possivel criar o diretorio para salvar o arquivo.").withErrorCode(30001).build();
  }
  
  @ResponseBody
  @ExceptionHandler({CsvConvertException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage exceptionHandler(CsvConvertException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("Internal Server Error - {0}", ex.getParameters())).withUserMessage("Houve um erro e não foi possivel converter o arquivo.").withErrorCode(30001).build();
  }
  
  @ResponseBody
  @ExceptionHandler({CsvUnsuportedException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage exceptionHandler(CsvUnsuportedException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("BAD REQUEST - {0}", ex.getParameters())).withUserMessage("Arquivo nao suportado - Esperado '.CSV'.").withErrorCode(30001).build();
  }
  
  @ResponseBody
  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage exceptionHandler(EntityNotFoundException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("{0} not found", ex.getParameters())).withUserMessage(MessageFormat.format("You attempted to get a {0}, but did not find any", ex.getParameters())).withErrorCode(20023).build();
  }
  
  @ResponseBody
  @ExceptionHandler({InternalServerErrorException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage exceptionHandler(InternalServerErrorException ex)
  {
    return errorBuilder.withDeveloperMessage(MessageFormat.format("Internal server error {0}", ex.getParameters())).withUserMessage(MessageFormat.format("Was encountered an error when processing your request. We apologize for the inconvenience.", ex.getParameters())).withErrorCode(10000).build();
  }
}
