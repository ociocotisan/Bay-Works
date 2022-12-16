package dto;

import org.bson.Document;

public class CashPayment extends BasePaymentDto {

  public String getType() {
    return type;
  }

  public CashPayment setType(String type) {
    this.type = type;
    return this;
  }

  private String type = "cash";
  String from;
  String to;

  public CashPayment setFrom(String from) {
    this.from = from;
    return this;
  }

  public CashPayment setTo(String to) {
    this.to = to;
    return this;
  }

  public CashPayment setNotes(String notes) {
    this.notes = notes;
    return this;
  }

  String notes;

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getNotes(){
    return notes;
  }

  public CashPayment() {
  }

  public CashPayment(String from, String to, String type, double amount){
    this.from = from;
    this.to = to;
    this.type = type;
    this.amount = amount;
  }

  public CashPayment(String from, String to, String type, double amount, String notes){
    this.from = from;
    this.to = to;
    this.type = type;
    this.amount = amount;
    this.notes = notes;
  }

  public CashPayment(String uniqueId, Double amount) {
    super(uniqueId);
    this.amount = amount;
  }

  public CashPayment(Double amount) {
    super();
    this.amount = amount;
  }

  @Override
  public Document toDocument() {
    return new Document("amount", amount)
        .append("type", type);
  }

  public Document toCashDocument(){
    return new Document("from", from).append("to",to).append("type",type).append("amount",amount)
            .append("notes", notes);
  }

  public static CashPayment fromDocument(Document document) {
    var payment = new CashPayment(document.get("from").toString(), document.get("to").toString(),
            document.get("type").toString(), document.getDouble("amount"), document.get("notes").toString());

    return payment;
  }
}
