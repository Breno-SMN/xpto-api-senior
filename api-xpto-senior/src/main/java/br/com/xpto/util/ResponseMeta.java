package br.com.xpto.util;


import java.util.List;

public class ResponseMeta implements java.io.Serializable
{
  private static final long serialVersionUID = 1323278432780170679L;
  private Meta meta;
  private List<?> records;
  
  public ResponseMeta() {}
  
  public Meta getMeta() {
    return meta;
  }
  
  public void setMeta(Meta meta) {
    this.meta = meta;
  }
  
  public List<?> getRecords() {
    return records;
  }
  
  public void setRecords(List<?> records) {
    this.records = records;
  }
}
