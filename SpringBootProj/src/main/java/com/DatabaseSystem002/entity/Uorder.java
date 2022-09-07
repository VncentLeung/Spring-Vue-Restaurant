package com.DatabaseSystem002.entity;

public class Uorder {

  private long uid;
  private long oid;
  private String did;
  private long dnums;
  private long oconsume;
  private String delieverystatus;


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public long getOid() {
    return oid;
  }

  public void setOid(long oid) {
    this.oid = oid;
  }


  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }


  public long getDnums() {
    return dnums;
  }

  public void setDnums(long dnums) {
    this.dnums = dnums;
  }


  public long getOconsume() {
    return oconsume;
  }

  public void setOconsume(long oconsume) {
    this.oconsume = oconsume;
  }


  public String getDelieverystatus() {
    return delieverystatus;
  }

  public void setDelieverystatus(String delieverystatus) {
    this.delieverystatus = delieverystatus;
  }

}
