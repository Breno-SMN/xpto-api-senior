package br.com.xpto.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import br.com.xpto.resource.exceptions.EntityNotFoundException;

@org.springframework.web.bind.annotation.CrossOrigin
public class BaseResource
{
  public BaseResource() {}
  
  protected void checkNotNull(Optional<?> optional, String resourceName)
  {
    if ((!optional.isPresent()) || 
      (((optional.get() instanceof Collection)) && (((Collection)optional.get()).isEmpty())) || (
      ((optional.get() instanceof Page)) && (!((Page)optional.get()).hasContent()))) {
      throw new EntityNotFoundException(new Object[] { resourceName });
    }
  }
  
  protected ResponseEntity<?> buildResponse(HttpStatus status) {
    return buildResponse(status, Optional.empty());
  }
  
  protected ResponseEntity<?> buildResponse(HttpStatus status, Optional<?> entity) {
    if ((entity.isPresent()) && ((entity.get() instanceof Page))) {
      Page<?> page = (Page)entity.get();
      return buildResponse(status, page);
    }
    return buildResponse(status, entity, Integer.valueOf(0), Integer.valueOf(50));
  }
  
  private ResponseEntity<?> buildResponse(HttpStatus status, Page<?> page) {
    return buildResponse(status, null, Optional.ofNullable(page.getContent()), Integer.valueOf(page.getNumber()), 
      Integer.valueOf(page.getNumberOfElements()), null);
  }
  
  protected ResponseEntity<?> buildResponse(HttpStatus status, Optional<?> entity, Integer page, Integer size)
  {
    return buildResponse(status, null, entity, page, size, null);
  }
  

  private ResponseEntity<?> buildResponse(HttpStatus status, MultiValueMap<String, String> headers, Optional<?> entity, Integer offset, Integer limit, Integer totalRecords)
  {
    List<Object> records = new ArrayList();
    
    if (entity.isPresent()) {
      if ((entity.get() instanceof Collection)) {
        records.addAll((Collection)entity.get());
      } else {
        records.add(entity.get());
      }
    }
    
    offset = Integer.valueOf(offset == null ? 0 : offset.intValue());
    limit = Integer.valueOf(limit == null ? records.size() : limit.intValue());
    
    ResponseMeta body = new ResponseMeta();
    body.setMeta(new Meta(getServer(), limit, offset, Integer.valueOf(records.size()), totalRecords));
    

    body.setRecords(records);
    
    return new ResponseEntity(body, headers, status);
  }
  
  private static String getServer() {
    try {
      return InetAddress.getLocalHost().toString();
    } catch (Exception e) {}
    return "unkown";
  }
}
