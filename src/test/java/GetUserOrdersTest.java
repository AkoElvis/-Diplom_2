import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
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
    private List<String> ingredients;
    private String hash;
    private OrderRequest orderRequest;
    private String email;
    private String password;
    private String name;
    private UserResponse userResponse;
    private UserRequest user;
    private OrderResponse orderResponse;
    private UserOrderResponse userOrderResponse;

    // Перед каждым тестом формируем случайные тестовые данные и создаем пользователя
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomAlexeyKolyaevEmail();
        this.password = CreatingRandomData.getRandomAlexeyKolyaevString();
        this.name = CreatingRandomData.getRandomAlexeyKolyaevString();
        this.user = new UserRequest(email,password,name);
        this.userResponse = UserResponse.getRegisterUserResponse(user);
        // А также создаем тело запроса со случайным хэшем из списка ингредиентов
        this.hash = IngredientsResponse.getHash();
        this.ingredients = Arrays.asList(hash);
        this.orderRequest = new OrderRequest(ingredients);
        // И после создаем заказ для только что созданного пользователя
        orderRequest.createAuthorizedUserOrder(orderRequest, userResponse);
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
        response.then().assertThat().body("total", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability to get the orders list of an unauthorized user")
    public void checkUnauthorizedUserGetOrdersList() {
        Response response = UserOrderResponse.getResponseGetUnauthorizedUserOrdersList();
        response.then().assertThat().body("message", equalTo(Messages.UNAUTHORIZED))
                .and()
                .statusCode(401);
    }
}
