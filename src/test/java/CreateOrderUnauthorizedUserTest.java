import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderUnauthorizedUserTest {

    private List<String> ingredients;
    private String hash;
    private OrderRequest orderRequest;

    @Step("Check that the order is created. The response should contain 'success:true' and status code 200")
    public void checkResponseContainsSuccessTrue(Response response) {
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Step("Check that the order isn't created. The response should contain the expected message and status code 400")
    public void checkResponseContainsErrorMessageEmptyOrder(Response response) {
        response.then().assertThat().body("message", equalTo(Messages.EMPTY_ORDER))
                .and()
                .statusCode(400);
    }

    @Step("Check that the order isn't created. The response should contain the expected status code 500")
    public void checkResponseContainsStatusCode500(Response response) {
        response.then().assertThat().statusCode(500);
    }

    // Перед каждым тестом создаем тело запроса со случайным хэшем из списка ингредиентов
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.hash = IngredientsResponse.getHash();
        this.ingredients = Arrays.asList(hash);
        this.orderRequest = new OrderRequest(ingredients);
    }

    @Test
    @DisplayName("Checking the ability to create an order for an unauthorized user")
    public void checkUnauthorizedUserSuccessfulOrder() {
        Response response = OrderRequest.getResponseCreateUnauthorizedUserOrder(orderRequest);
        checkResponseContainsSuccessTrue(response);
    }

    @Test
    @DisplayName("Checking the inability to create an empty order for an unauthorized user")
    public void checkUnauthorizedUserEmptyOrder() {
        this.orderRequest = new OrderRequest();
        Response response = OrderRequest.getResponseCreateUnauthorizedUserOrder(orderRequest);
        checkResponseContainsErrorMessageEmptyOrder(response);
    }

    @Test
    @DisplayName("Checking the inability to create an order with an invalid ingredient id for an unauthorized user")
    public void checkUnauthorizedUserInvalidHashOrder() {
        this.hash = hash + CreatingRandomData.getRandomAlexeyKolyaevString();
        this.ingredients = Arrays.asList(hash);
        this.orderRequest = new OrderRequest(ingredients);
        Response response = OrderRequest.getResponseCreateUnauthorizedUserOrder(orderRequest);
        checkResponseContainsStatusCode500(response);
    }
}