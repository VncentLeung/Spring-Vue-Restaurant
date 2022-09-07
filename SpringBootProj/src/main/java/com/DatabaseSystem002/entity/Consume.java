package com.DatabaseSystem002.entity;


public class Consume {

  private long oid;
  private long cid;
  private long kid;
  private java.sql.Timestamp ctime;
  private long consume;


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

}
