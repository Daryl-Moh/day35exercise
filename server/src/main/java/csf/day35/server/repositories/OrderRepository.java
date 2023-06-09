package csf.day35.server.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import csf.day35.server.models.Order;
import jakarta.json.JsonObject;

@Repository
public class OrderRepository {
    
    @Autowired
    private MongoTemplate template;

    public void createOrder(Order order) {

        JsonObject j = order.toJson();
        Document doc = Document.parse(j.toString());

        template.insert(doc, "orders");
    }
}
