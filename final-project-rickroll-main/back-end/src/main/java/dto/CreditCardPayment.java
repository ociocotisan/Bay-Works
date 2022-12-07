package dto;

import org.bson.Document;

public class CreditCardPayment extends BasePaymentDto {

  private String number;
  private String securityCode;
  private static String type = "credit";

  public CreditCardPayment(Double amount, String number, String securityCode) {
    super();
    this.amount = amount;
    this.number = number;
    this.securityCode = securityCode;
  }

  @Override
  public Document toDocument() {
    return new Document("amount", amount)
        .append("number", number)
        .append("securityCode", securityCode)
        .append("type", type);
  }

  public static CreditCardPayment fromDocument(Document document){
    var payment =  new CreditCardPayment(document.getDouble("amount"),
        document.getString("number"),
        document.getString("securityCode"));
    payment.setUniqueId(document.getObjectId("_id").toHexString());
    return payment;
  }

  public String getNumber() {
    return number;
  }

  public String getSecurityCode() {
    return securityCode;
  }

  public String getType() {
    return type;
  }
}
