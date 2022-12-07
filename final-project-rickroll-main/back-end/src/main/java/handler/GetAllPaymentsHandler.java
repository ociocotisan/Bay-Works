package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import request.ParsedRequest;
import response.ResponseBuilder;

public class GetAllPaymentsHandler implements BaseHandler {

  private static final Gson gson = new Gson();

  @Override
  public String handleRequest(ParsedRequest request) {
    return new ResponseBuilder()
        .setStatus("200 OK")
        .setVersion("HTTP/1.1")
        .setBody(gson.toJson(PaymentDao.getInstance().getAll()))
        .build().toString();
  }
}
