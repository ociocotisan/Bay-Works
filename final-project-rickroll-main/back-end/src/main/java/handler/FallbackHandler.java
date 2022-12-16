package handler;

import request.ParsedRequest;
import response.ResponseBuilder;

public class FallbackHandler implements BaseHandler {

  @Override
  public String handleRequest(ParsedRequest request) {
    return new ResponseBuilder()
        .setStatus("404 Not Found")
        .setVersion("HTTP/1.1")
        .build()
        .toString();
  }
}
