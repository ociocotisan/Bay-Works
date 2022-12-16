package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.CashPayment;
import request.ParsedRequest;
import response.ResponseBuilder;

public class CashPaymentHandler implements BaseHandler{

  private static final Gson gson = new Gson();

  // Only Post
  @Override
  public String handleRequest(ParsedRequest request) {
    CashPayment payment = gson.fromJson(request.getBody(), CashPayment.class);
    payment.setUniqueId(String.valueOf(Math.random()));
    PaymentDao.getInstance().put(payment);
    return new ResponseBuilder().setStatus("200 OK").setVersion("HTTP/1.1").build().toString();
  }
}
