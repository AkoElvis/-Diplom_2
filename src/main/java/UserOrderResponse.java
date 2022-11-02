import constants.TestStandEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserOrderResponse {
    private Boolean success;
    private List<UserOrders> orders;
    private int total;
    private int totalToday;

    public UserOrderResponse(Boolean success, List<UserOrders> orders, int total, int totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public UserOrderResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<UserOrders> getOrders() {
        return orders;
    }

    public void setOrders(List<UserOrders> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }

    @Step("Get response of an authorized user's orders list")
    public static Response getResponseGetAuthorizedUserOrdersList(UserResponse userResponse) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(userResponse.getAccessToken())
                .get(TestStandEndpoints.ORDER);
    }

    @Step("Get response of an unauthorized user's orders list")
    public static Response getResponseGetUnauthorizedUserOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .get(TestStandEndpoints.ORDER);
    }
}
