package request;

public class CustomParser {

  public static ParsedRequest parse(String request){
    String[] lines = request.split("\n");
    String requestLine = lines[0];
    String[] requestParts = requestLine.split(" ");
    System.out.println(requestLine);
    var result = new ParsedRequest();
    result.setMethod(requestParts[0]);

    var parts = requestParts[1].split("\\?");
    result.setPath(parts[0]);

    if(parts.length == 2){
      System.out.println(parts[1]);
      String[] queryParts = parts[1].split("&");
      for (int i = 0; i < queryParts.length; i++) {
        String[] pair = queryParts[i].split("=");
        result.setQueryParam(pair[0], pair[1]);
      }
    }

    String body = "";
    boolean emptyLine = false;
    for (String line: lines){
      if(line.trim().equals("")){
        emptyLine = true;
      }
      if(emptyLine){
        body += line;
      }
    }
    result.setBody(body);
    return result;
  }
}
