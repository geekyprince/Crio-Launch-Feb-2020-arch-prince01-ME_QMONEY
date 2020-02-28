
package com.crio.warmup.stock;

public class Employee {

  public Employee() {  }
  
  private String symbol;
  private int quantity;
  private String tradeType;
  private String purchaseDate;

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public void setPurchaseDate(String purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  //solution
  public String getSymbol() {
    return symbol;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getPurchaseDate() {
    return purchaseDate;
  }

  public String getTradeType() {
    return tradeType;
  }
  //solution

}
