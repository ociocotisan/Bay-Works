import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dto.BasePaymentDto;
import dto.CashPayment;
import org.bson.Document;
import org.eclipse.jetty.server.Authentication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static spark.Spark.*;

// use to convert between Java Object and Json
class UserDto{
  public String username;
  public String password;
  public double amount;

  public UserDto(String username, String password, double amount) {
    this.username = username;
    this.password = password;
    this.amount = amount;
  }

  public UserDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Document toDocument() {
    return new Document("username", username)
            .append("password", password).append("amount",amount);
  }
}

class resultDto{
  Boolean isSuccess;
  String message;
  double amount;

  public resultDto(Boolean isSuccess, String message) {
    this.isSuccess = isSuccess;
    this.message = message;
  }

  public resultDto(Boolean isSuccess, double amount){
    this.isSuccess = isSuccess;
    this.amount = amount;
  }
}

class currentUser{
  Boolean isSuccess;
  public String username;
  public String password;
  public double amount;

  public currentUser(Boolean isSuccess, String username, String password, double amount) {
    this.isSuccess = isSuccess;
    this.username = username;
    this.password = password;
    this.amount = amount;
  }
}

public class SparkDemo {

  public static Gson gson = new Gson();
  public static List<UserDto> userCollection = new ArrayList<>();
  public static List<BasePaymentDto> someList = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    port(1234);
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    System.out.println("Connected to mongodb");
    MongoDatabase db = mongoClient.getDatabase("MyDatabase");
    MongoCollection<Document> myCollection = db.getCollection("Users"); // myDatabase/Users
    MongoCollection<Document> myTransactions = db.getCollection("Transactions");


    post("/api/Transactions" , (req, res) -> {
      String body = req.body();
      System.out.println("This is the body: " + body);
      String[] strArray = body.split("\"");
      List<BasePaymentDto> testList = new ArrayList<>();
//      FindIterable<Document> iterable = myTransactions.find((eq("from", strArray[1])));
//      if (iterable == null){
//        return new resultDto(false, "Currently there is no transaction");
//      }
//      MongoCursor<Document> cursor = iterable.iterator();
//      while(cursor.hasNext()) {
//        System.out.println(cursor.next());
//        //someList.add(CashPayment.fromDocument(cursor.next()));
//      }

      List<Document> documentList =  myTransactions.find(eq("from",strArray[1])).into(new ArrayList<>());
      if (documentList.isEmpty()){
        return new resultDto(false, "Currently there is no transaction");
      }
      for(int i = 0; i < documentList.size(); i++){
        Document potentialDocuments = documentList.get(i);
        CashPayment cash = CashPayment.fromDocument(potentialDocuments);
        testList.add(cash);
      }
      return gson.toJson(testList);
//      System.out.println("This is the somelist size: " + someList.size());
//      Document a = myTransactions.find(eq("from", strArray[1])).first();
//      System.out.println(a);
//      System.out.println("Transaction pressed!!!");
//      //System.out.println("from: " + a.toString());
//      var b = CashPayment.fromDocument(a);
//      System.out.println("Transaction pressed223!!!");
//      var result = new resultDto(false,"Test");
//      var cash = new CashPayment(123.3);
//      System.out.println("Transaction pressed22!!!");
//      return gson.toJson(b);
    });
//    FindIterable<Document> iterDoc = myTransactions.find();
//    System.out.println("Listing all trans");
//    Iterator it = iterDoc.iterator();
//    while (it.hasNext()) {
//      System.out.println(it.next());
//    }
//    Document a = myTransactions.find(eq("from", "awzum")).first();
//    System.out.println("from: " + a.toString());
//    CashPayment b = CashPayment.fromDocument(a);
//    System.out.println(b.getUniqueId());
//    System.out.println(b.getFrom());
//    System.out.println(b.getTo());
//    System.out.println(b.getNotes());
//    System.out.println(b.getType());
//    System.out.println(b.getAmount());

    post("/api/refresh" , (req, res) -> {
      String body = req.body();
      System.out.println("This is the body: " + body);
      String[] strArray = body.split("\\{|}|\\\\|\"");
//                for(int i = 0; i < strArray.length; i ++){
//            System.out.println(i +":" +strArray[i]);
//          }
      Document userDoc = myCollection.find(eq("username",strArray[6])).first();
      //System.out.println(userDoc);
      var resultDto = new resultDto(true, (Double) userDoc.get("amount"));
      return gson.toJson(resultDto);
    });

        post("/api/SignUp", (req, res) -> {   // "SignUp" is case sensitive
      String body = req.body();
      System.out.println("This is the body: " + body);
      UserDto userDto = gson.fromJson(body, UserDto.class);
//          System.out.println(userDto.getUsername());
//          System.out.println(userDto.getPassword());
//          System.out.println(userDto.getAmount());
          userDto.setAmount(1000.0); // give a default amount of 1000.0 to every new user
//          System.out.println(userDto.getUsername());
//          System.out.println(userDto.getPassword());
//          System.out.println(userDto.getAmount());

      // handle duplicates
          Document potentialUser = myCollection.find(eq("username",userDto.getUsername())).first();
          if(potentialUser != null){
            System.out.println("Username duplicate found, this account will not be created.");
            var result = new resultDto(false,"User already exists!");
            return gson.toJson(result);
          }
      userCollection.add(userDto);
      myCollection.insertOne(userDto.toDocument());
          System.out.println("Sign-up succeed");
      var result = new resultDto(true, null);
      return gson.toJson(result); // must turn java object to json string before sending
 });

        post("/api/login", (req,res) -> {
          String body = req.body();
          System.out.println("This is login information: " + body);
          String[] strArray = body.split(" |\\?|=|\n|,|\"");
          Document potentialUsername = myCollection.find(eq("username",strArray[3])).first();
          if (potentialUsername != null){
            if(potentialUsername.get("password").toString().equals(strArray[8]) ){
              System.out.println("We find the username and password!!!!");
              var result = new currentUser(true, String.valueOf(potentialUsername.get("username")),
                      String.valueOf(potentialUsername.get("password"))
              ,((Double) potentialUsername.get("amount")));
              return gson.toJson(result);
            }
            else{
              System.out.println("The password is wrong, try again!!!!");
              var result = new resultDto(false, "password is wrong");
              return gson.toJson(result);
            }
          }
          else {
            System.out.println("We do not find the username for the account");
            var result = new resultDto(false, "username does not exist");
            return gson.toJson(result);
          }
    });

        // first button of cashPayment page
        post("/api/cashPayment-verifyAccount", (req, res) -> {
          String body = req.body();
          System.out.println("This is the Payer's account info: " + body);
          String[] strArray = body.split(" |\\?|=|\n|,|\"");
//          for(int i = 0; i < strArray.length; i ++){
//            System.out.println(i +":" +strArray[i]);
//          }
          // str[3] is the username,  str[8] is the password
          Document potentialUsername = myCollection.find(eq("username",strArray[3])).first();
          if (potentialUsername != null){
            if(potentialUsername.get("password").toString().equals(strArray[8]) ){
              System.out.println("We find the username and password!!!!");
              var result = new resultDto(true, "❀We found the user❀");
              return gson.toJson(result);
            }
            else{
              System.out.println("The password is wrong, try again!!!!");
              var result = new resultDto(false, "×Password is wrong×");
              return gson.toJson(result);
            }
          }
          //System.out.println("The guys password: " + potentialUsername.get("password"));
          var result = new resultDto(false, "×Can not find user, try again×");
          return gson.toJson(result);
    });

    post("/api/cashPayment-verifyRecipient", (req, res) -> {
      String body = req.body();
      System.out.println("This is the recipient's account info: " + body);
      String[] strArray = body.split(" |\\?|=|\n|,|\"");
      Document potentialUsername = myCollection.find(eq("username",strArray[3])).first();
      if(potentialUsername != null){
        System.out.println("We found the recipient account!!!");
        var result = new resultDto(true, "❀We found the recipient!❀");
        return gson.toJson(result);
      }
      var result = new resultDto(false, "×Can not find the recipient×");
      return gson.toJson(result);
    });

        // last button of cashPayment page
        post("/api/cashPayment", (req,res) -> {
          boolean verifyAccount = false;
          boolean checkAmount = false;
          boolean checkRecipient = false;

          String body = req.body();
          //System.out.println("This is whole body: " + body);
          String[] strArray = body.split(" |\\?|=|\n|,|\"");
//          for(int i = 0; i < strArray.length; i ++){
//            System.out.println(i +":" +strArray[i]);
//          }
          // str[3] is payer, str[8] is pw, str[13] is recipient, str[23] is amount, str[28] is notes
          // str[28] notes probably needs to delete all "\n"

          if((strArray[3]==null) || (strArray[8] == null)){
            return "The input is missing" + false;
          }

          // find the document based on the input
          else{
            Document potentialUsername = myCollection.find(eq("username",strArray[3])).first();
            if (potentialUsername != null){
              if(potentialUsername.get("password").toString().equals(strArray[8]) ){
                verifyAccount = true;
              }
              else{
                System.out.println("The password is wrong, try again!!!!");
                return false;
              }
            }
          }
          if(!strArray[23].isEmpty()){
            checkAmount = true;
          }
          else{
            System.out.println("The amount is empty!");
            return gson.toJson(new resultDto(false, "The amount is missing"));
          }

          if(strArray[13] != null){
            Document potentialRecipient = myCollection.find(eq("username",strArray[3])).first();
            if(potentialRecipient != null){
              checkRecipient = true;
            }
            else{
              System.out.println("We can not find the recipient");
              return gson.toJson(new resultDto(false, "We can not find the recipient"));
            }
          }

          // if the previous steps pass, then store the cashPayment into Database
          if(verifyAccount && checkAmount && checkRecipient){
            //System.out.println("All pass!!!!!!!!!!!!!!!!!!!");

            // Grab the documents of payer and recipient
            Document payer = myCollection.find(eq("username",strArray[3])).first();
            Document recipient = myCollection.find(eq("username",strArray[13])).first();

            System.out.println("The payer has: " + payer.get("amount"));
            System.out.println("The recipient has: " + recipient.get("amount"));
            //System.out.println(payer.get("amount").getClass());  // this is double type

            System.out.println("After transferring ------------------------------");
            Double transfer = Double.valueOf(strArray[23]);
            if((Double)payer.get("amount") < transfer){
              System.out.println("The payer does not have enough money to transfer");
              return gson.toJson(new resultDto(false, "Your don't have enough in your pocket right now"));
            }

            Double loss =  ((Double)payer.get("amount") - transfer);
            payer.replace("amount",loss);
            System.out.println("Now the payer has: " + payer.get("amount"));
            Double get =  ((Double)recipient.get("amount") + transfer);
            recipient.replace("amount", get);
            System.out.println("Now the recipient has: " + recipient.get("amount"));
//            Document newPayer = new Document().append("username", payer.get("username"))
//                                              .append("password", payer.get("password"))
//                                              .append("amount",payer.get("amount"));
//            Document newRecipient = new Document().append("username", recipient.get("username"))
//                    .append("password", recipient.get("password"))
//                    .append("amount",recipient.get("amount"));

//            myCollection.deleteOne(payer);
//            myCollection.deleteOne(recipient);
//            myCollection.insertOne(newPayer);
//            myCollection.insertOne(newRecipient);

           // myCollection.updateOne(payer,"amount")

            myCollection.updateOne(Filters.eq("username", strArray[3]), Updates.set("amount",loss));
            myCollection.updateOne(Filters.eq("username", strArray[13]),Updates.set("amount",get));

            CashPayment cashDto = gson.fromJson(body, CashPayment.class);
//            System.out.println(cashDto.getFrom());
//            System.out.println(cashDto.getTo());
//            System.out.println(cashDto.getNotes());
            myTransactions.insertOne(cashDto.toCashDocument());
            return gson.toJson(new resultDto(true, "You made the transfer successfully"));
          }
          return gson.toJson(new resultDto(false, "Oppos, something is wrong, check the helper buttons"));
        });


//    //connect "MyDatabase/Users" in Robot 3T
//    MongoCollection<Document> userCollection = db.getCollection("Users");
//    ServerSocket ding;
//    Socket dong = null;
//    try {
//      ding = new ServerSocket(1299);
//      System.out.println("Opened socket " + 1299);
//      while (true) {
//        // keeps listening for new clients, one at a time
//        try {
//          dong = ding.accept(); // waits for client here
//        } catch (IOException e) {
//          System.out.println("Error opening socket");
//          System.exit(1);
//        }
//
//        InputStream stream = dong.getInputStream();
//        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
//        String firstLine = null;
//
//        try {
//          // read the first line to get the request method, URI and HTTP version
//          String line = in.readLine();
//          firstLine = line;
//          System.out.println("----------REQUEST START---------");
//          System.out.println(line);
//          // read only headers
//          line = in.readLine();
//          while (line != null && line.trim().length() > 0) {
//            int index = line.indexOf(": ");
//            if (index > 0) {
//              System.out.println(line);
//            } else {
//              break;
//            }
//            line = in.readLine();
//          }
//          System.out.println("----------REQUEST END---------\n\n");
//        } catch (IOException e) {
//          System.out.println("Error reading");
//          System.exit(1);
//        }
//
//        BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
//        PrintWriter writer = new PrintWriter(out, true);  // char output to the client
//
//        // every response will always have the status-line, date, and server name
//        writer.println("HTTP/1.1 200 OK");
//        writer.println("Server: TEST");
//        writer.println("Connection: close");
//        writer.println("Content-type: text/html");
//        writer.println("");
//        if(firstLine != null){
//          String path = firstLine.split(" ")[1];
//          String[] parts = path.split("\\?");
//
//          // in hw1 this is factory
//          ///getUser?username=user123&password=123
//          Map<String,String> queryArgs =  Arrays.stream(parts[1].split("&"))
//                  .map(keyValueString -> keyValueString.split("="))
//                  .collect(Collectors.toMap(array -> array[0], array -> array[1]));// stream of array of strings
//          if(parts[0].equals("/getUser")) {
//            String username = queryArgs.get("username");
//            String password = queryArgs.get("password");
//            System.out.println("Username " + username + " password: " + password);
//
//            Document potentialuser = userCollection.find(eq("username",username)).first();
//            if(potentialuser == null) {
//              writer.println("<h1>User Not Found</h1>");
//            } else{
//              if(potentialuser.getString("password").equals(password)) {
//                writer.println("<h1>UserFound</h1>");}
//              else{
//                writer.println("<h1>Invalid password! </h1>");
//              }
//
//            }
//
//          }else if(parts[0].equals("/createUser")){
//            String username = queryArgs.get("username");
//            String password = queryArgs.get("password");
//            System.out.println("Username " + username + " password: " + password);
//            Document potentialUser = userCollection.find(eq("username",username)).first();
//
//            // can not make 2 users with same username
//            if(potentialUser != null) {
//              writer.println("<h1>Username already taken</h1>");
//            } else{
//              // create brand new user
//              Document newUser = new Document().append("username",username)
//                      .append("password", password);
//              userCollection.insertOne(newUser);
//              System.out.println("<h1>User has been created</h1>");
//
//            }
//
//          }else{
//            writer.println("<h1>404<h1>");
//          }
//        }else{
//          // fallback
//          writer.println("<h1>Hello World</h1>");
//        }
//
//        dong.close();
//      }
//
//    } catch (IOException e) {
//      System.out.println("Error opening socket");
//      System.exit(1);
//
//    }
  }
}