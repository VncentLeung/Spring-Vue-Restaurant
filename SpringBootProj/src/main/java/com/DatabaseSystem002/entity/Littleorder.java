package com.DatabaseSystem002.entity;


public class Littleorder {

  private long oid;
  private long cid;
  private long kid;
  private long uid;
  private java.sql.Timestamp ctime;
  private long consume;
  private String did;
  private String dname;
  private long dnums;
  private long oconsume;
  private String delieverystatus;


  public long getOid() {
    return oid;
  }

  public void setOid(long oid) {
    this.oid = oid;
  }


  public long getCid() {
    return cid;
  }

  public void setCid(long cid) {
    this.cid = cid;
  }


  public long getKid() {
    return kid;
  }

  public void setKid(long kid) {
    this.kid = kid;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public java.sql.Timestamp getCtime() {
    return ctime;
  }

  public void setCtime(java.sql.Timestamp ctime) {
    this.ctime = ctime;
  }


  public long getConsume() {
    return consume;
  }

  public void setConsume(long consume) {
    this.consume = consume;
  }


  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }


  public String getDname() {
    return dname;
  }

  public void setDname(String dname) {
    this.dname = dname;
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
