import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
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
    private Order order;

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.hash = IngredientsResponse.getHash();
        this.ingredients = Arrays.asList(hash);
        this.order = new Order(ingredients);
    }

    @Test
    @DisplayName("Checking the ability to create an order for an unauthorized user")
    public void checkUnauthorizedUserSuccessfulOrder() {
        Response response = order.getResponseCreateUnauthorizedUserOrder(order);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability to create an empty order for an unauthorized user")
    public void checkUnauthorizedUserEmptyOrder() {
        this.order = new Order();
        Response response = order.getResponseCreateUnauthorizedUserOrder(order);
        response.then().assertThat().body("message", equalTo(Messages.EMPTY_ORDER))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to create an order with an invalid ingredient id for an unauthorized user")
    public void checkUnauthorizedUserInvalidHashOrder() {
        this.hash = hash + CreatingRandomData.getRandomKoliaevString();
        this.ingredients = Arrays.asList(hash);
        this.order = new Order(ingredients);
        Response response = order.getResponseCreateUnauthorizedUserOrder(order);
        response.then().assertThat().statusCode(500);
    }


}