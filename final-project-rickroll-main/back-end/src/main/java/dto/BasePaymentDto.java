package dto;

import org.bson.Document;

public abstract class BasePaymentDto {

  private String uniqueId;
  public Double amount;

  public BasePaymentDto() {
  }

  public BasePaymentDto(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getUniqueId(){
    return uniqueId;
  }

  public BasePaymentDto setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return this;
  }

  public Double getAmount() {
    return amount;
  }

  public BasePaymentDto setAmount(Double amount) {
    this.amount = amount;
    return this;
  }

  public abstract Document toDocument();

  public static BasePaymentDto toDto(Document document){
    if(document.getString("type").equals("cash")){
      return CashPayment.fromDocument(document);
    }else{
      return CreditCardPayment.fromDocument(document);
    }
  }
}
