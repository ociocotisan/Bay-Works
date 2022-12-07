package dao;

import com.mongodb.client.MongoCollection;
import dto.BasePaymentDto;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentDao implements BaseDao<BasePaymentDto> {

  private static PaymentDao instance;
  public MongoCollection<Document> collection;

  private PaymentDao(MongoCollection<Document> collection){
    this.collection = collection;
  }

  public static PaymentDao getInstance() {

    return instance;
  }

  public static PaymentDao getInstance(MongoCollection<Document> collection) {

    return instance;
  }

  @Override
  public void put(BasePaymentDto basePaymentDto) {
    collection.insertOne(basePaymentDto.toDocument());
  }

  @Override
  public BasePaymentDto get(String id) {
    var doc= collection.find(new Document("_id", new ObjectId(id))).first();
    return BasePaymentDto.toDto(doc);
  }

  @Override
  public List<BasePaymentDto> getAll(){
    return collection.find().into(new ArrayList<>()).stream()
        .map(BasePaymentDto::toDto)
        .collect(Collectors.toList());
  }
}
