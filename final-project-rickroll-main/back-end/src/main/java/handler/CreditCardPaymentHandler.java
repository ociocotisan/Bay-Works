package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.CreditCardPayment;
import request.ParsedRequest;
import response.ResponseBuilder;

public class CreditCardPaymentHandler implements BaseHandler{

  private static final Gson gson = new Gson();

  // Only Post
  @Override
  public String handleRequest(ParsedRequest request) {
    CreditCardPayment payment = gson.fromJson(request.getBody(), CreditCardPayment.class);
    payment.setUniqueId(String.valueOf(Math.random()));
    PaymentDao.getInstance().put(payment);
    return new ResponseBuilder().setStatus("200 OK").setVersion("HTTP/1.1").build().toString();
  }

}
