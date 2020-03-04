package com.crio.warmup.stock;

public class JsonData {
  private String date;
  private float close;
  private float high;
  private float low;
  private float open;
  private float volume;
  private float adjClose;
  private float adjHigh;
  private float adjLow;
  private float adjOpen;
  private float adjVolume;
  private float divCash;
  private float splitFactor;

  public String getdate() {
    return date;
  }

  public void setdate(String date) {
    this.date = date;
  }

  public Float getclose() {
    return close;    
  }

  public void setclose(Float close) {
    this.close = close;
  }

  public Float gethigh() {
    return high;
  }

  public void sethigh(Float high) {
    this.high = high;
  }
  
  public Float getlow() {
    return low;
  }

  public void setlow(Float low) {
    this.low = low;
  } 

  public Float getopen() {
    return open;
  }

  public void setopen(Float open) {
    this.open = open;
  }

  public Float getvolume() {
    return volume;
  }

  public void setvolume(Float volume) {
    this.volume = volume;
  }

  public Float getadjClose() {
    return adjClose;
  }

  public void setadjClose(Float adjClose) {
    this.adjClose = adjClose;
  }

  public Float getadjHigh() {
    return adjHigh;
  }

  public void setadjHigh(Float adjHigh) {
    this.adjHigh = adjHigh;
  }

  public Float getadjLow() {
    return adjLow;
  }

  public void setadjLow(Float adjLow) {
    this.adjLow = adjLow;
  }
  
  public Float getadjOpen() {
    return adjOpen;
  }

  public void setadjOpen(Float adjOpen) {
    this.adjOpen = adjOpen;
  }

  public Float getadjVolume() {
    return adjVolume;
  }

  public void setadjVolume(Float adjVolume) {
    this.adjVolume = adjVolume;
  }

  public Float getdivCash() {
    return divCash;
  }

  public void setdivCash(Float divCash) {
    this.divCash = divCash;
  }

  public Float getsplitFactor() {
    return splitFactor;
  }

  public void setsplitFactor(Float splitFactor) {
    this.splitFactor = splitFactor;
  }

}