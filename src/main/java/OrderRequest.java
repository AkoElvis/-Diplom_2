import constants.TestStandEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderRequest {
    private List<String> ingredients;

    public OrderRequest(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public OrderRequest() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Get response of an unauthorized user's order")
    public static Response getResponseCreateUnauthorizedUserOrder(OrderRequest orderRequest) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderRequest)
                .when()
                .post(TestStandEndpoints.ORDER);
    }

    @Step("Get response of an authorized user's order")
    public static Response getResponseCreateAuthorizedUserOrder(OrderRequest orderRequest, UserResponse userResponse) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(userResponse.getAccessToken())
                .and()
                .body(orderRequest)
                .when()
                .post(TestStandEndpoints.ORDER);
    }

    @Step("Get response body of an authorized user's order")
    public static OrderResponse getResponseBodyCreateAuthorizedUserOrder(OrderRequest orderRequest, UserResponse userResponse) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(userResponse.getAccessToken())
                .and()
                .body(orderRequest)
                .when()
                .post(TestStandEndpoints.ORDER).as(OrderResponse.class);
    }

    @Step("Create an order of an authorized user")
    public static void createAuthorizedUserOrder(OrderRequest orderRequest, UserResponse userResponse) {
                given()
                .header("Content-type", "application/json")
                .auth().oauth2(userResponse.getAccessToken())
                .and()
                .body(orderRequest)
                .when()
                .post(TestStandEndpoints.ORDER);
    }
}
