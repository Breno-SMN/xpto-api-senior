package br.com.xpto.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Meta
{
  @JsonIgnore
  private final Log LOGGER = LogFactory.getLog(Meta.class);
  
  @JsonIgnore
  private String version;
  
  private String server;
  
  private Integer limit;
  
  private Integer offset;
  
  private Integer recordCount;
  
  @JsonIgnore
  private Integer totalRecords;
  
  public Meta(String server, Integer limit, Integer offset, Integer recordCount, Integer totalRecords)
  {
    this.server = server;
    this.limit = limit;
    this.offset = offset;
    this.recordCount = recordCount;
    this.totalRecords = totalRecords;
  }
  
  public Meta(String version, String server, Integer limit, Integer offset, Integer recordCount, Integer totalRecords)
  {
    this(server, limit, offset, recordCount, totalRecords);
    this.version = version;
  }
  
  public String getVersion() {
    return version;
  }
  
  public String getServer() {
    return server;
  }
  
  public Integer getLimit() {
    return limit;
  }
  
  public Integer getOffset() {
    return offset;
  }
  
  public Integer getRecordCount() {
    return recordCount;
  }
  
  public Integer getTotalRecords() {
    return totalRecords;
  }
}