import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetUserOrdersTest {
    private UserResponse userResponse;

    @Step("Check that a response body contains user's order data. " +
            "The 'total' value should be not null. Status code should be 200")
    public void checkResponseBodyContainsTotal(Response response) {
        response.then().assertThat().body("total", notNullValue())
                .and()
                .statusCode(200);
    }

    @Step("Check that a response body contains the expected warning message. " +
            "Status code should be 401")
    public void checkResponseBodyContainsWarningMessageUnauthorized(Response response) {
        response.then().assertThat().body("message", equalTo(Messages.UNAUTHORIZED))
                .and()
                .statusCode(401);
    }

    // Перед каждым тестом формируем случайные тестовые данные и создаем пользователя
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        String email = CreatingRandomData.getRandomAlexeyKolyaevEmail();
        String password = CreatingRandomData.getRandomAlexeyKolyaevString();
        String name = CreatingRandomData.getRandomAlexeyKolyaevString();
        UserRequest user = new UserRequest(email, password, name);
        this.userResponse = UserResponse.getRegisterUserResponse(user);
        // А также создаем тело запроса со случайным хэшем из списка ингредиентов
        String hash = IngredientsResponse.getHash();
        List<String> ingredients = Arrays.asList(hash);
        OrderRequest orderRequest = new OrderRequest(ingredients);
        // И после создаем заказ для только что созданного пользователя
        OrderRequest.createAuthorizedUserOrder(orderRequest, userResponse);
    }

    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        UserResponse.deleteUser(userResponse);
    }

    @Test
    @DisplayName("Checking the ability to get the orders list of an authorized user")
    public void checkAuthorizedUserGetOrdersList() {
        Response response = UserOrderResponse.getResponseGetAuthorizedUserOrdersList(userResponse);
        checkResponseBodyContainsTotal(response);
    }

    @Test
    @DisplayName("Checking the inability to get the orders list of an unauthorized user")
    public void checkUnauthorizedUserGetOrdersList() {
        Response response = UserOrderResponse.getResponseGetUnauthorizedUserOrdersList();
        checkResponseBodyContainsWarningMessageUnauthorized(response);
    }
}
