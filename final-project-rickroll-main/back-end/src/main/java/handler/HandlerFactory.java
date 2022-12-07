package handler;

import request.ParsedRequest;

public class HandlerFactory {

  public static BaseHandler getHandler(ParsedRequest request) {
    switch (request.getPath()) {
      case "/makeCreditCardPayment":
        if (!request.getMethod().equals("POST")) {
          return new FallbackHandler();
        }
        return new CreditCardPaymentHandler();
      case "/makeCashPayment":
        if (!request.getMethod().equals("POST")) {
          return new FallbackHandler();
        }
        return new CashPaymentHandler();
      case "/getPayment":
        if (!request.getMethod().equals("GET")) {
          return new FallbackHandler();
        }
        return new GetPaymentHandler();
      case "/getAllPayments":
        if (!request.getMethod().equals("GET")) {
          return new FallbackHandler();
        }
        return new GetAllPaymentsHandler();
      default:
        return new FallbackHandler();
    }
  }

}
