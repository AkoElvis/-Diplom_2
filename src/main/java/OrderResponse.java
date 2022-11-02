import static io.restassured.RestAssured.given;

public class OrderResponse {
    private Boolean success;
    private String name;
    private Order order;

    public OrderResponse(Boolean success, String name, Order order) {
        this.success = success;
        this.name = name;
        this.order = order;
    }

    public OrderResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
