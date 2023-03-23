package csf.day35.server.models;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order {
    
    private String orderId = "";
    private String name;
    private String email;
    private List<LineItem> lineItems = new LinkedList<>();

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<LineItem> getLineItems() {
        return lineItems;
    }
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    } 

    public JsonObject toJson() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        lineItems.stream()
            .forEach(v -> arrBuilder.add(v.toJson()));
        
        return Json.createObjectBuilder()
            .add("orderId", orderId)
            .add("name", name)
            .add("email", email)
            .add("lineItems", arrBuilder.build())
            .build();
    }   

    public static Order toOrder(String payload) {
        JsonReader reader = Json.createReader(new StringReader(payload));
        return toOrder(reader.readObject());
    }

    public static Order toOrder(JsonObject obj) {
        Order order = new Order();
        if (obj.containsKey("orderId") && (!obj.isNull("orderId")))
            order.setOrderId(obj.getString("orderId"));

        order.setName(obj.getString("name"));
        order.setEmail(obj.getString("email"));

        List<LineItem> lineItems = obj.getJsonArray("lineItems").stream()
            .map(i -> i.asJsonObject())
            .map(LineItem::toLineItem)
            .toList();
        order.setLineItems(lineItems);

        return order;
    }
}
