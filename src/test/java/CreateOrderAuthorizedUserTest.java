import constants.Messages;
import constants.TestStandEndpoints;
import testdata.CreatingRandomData;
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
import static org.junit.Assert.assertEquals;

public class CreateOrderAuthorizedUserTest {

    private List<String> ingredients;
    private String hash;
    private OrderRequest orderRequest;
    private String email;
    private UserResponse userResponse;

    @Step("Check that the order is created.The response should contain the user's email")
    public void checkResponseContainsUsersEmail(OrderResponse orderResponse, String email) {
        assertEquals(orderResponse.getOrder().getOwner().getEmail(), email.toLowerCase());
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

    // Перед каждым тестом формируем случайные тестовые данные и создаем пользователя
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomAlexeyKolyaevEmail();
        String password = CreatingRandomData.getRandomAlexeyKolyaevString();
        String name = CreatingRandomData.getRandomAlexeyKolyaevString();
        UserRequest user = new UserRequest(email, password, name);
        this.userResponse = UserResponse.getRegisterUserResponse(user);
        // А также создаем тело запроса со случайным хэшем из списка ингредиентов
        this.hash = IngredientsResponse.getHash();
        this.ingredients = Arrays.asList(hash);
        this.orderRequest = new OrderRequest(ingredients);
    }
    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        UserResponse.deleteUser(userResponse);
    }

    @Test
    @DisplayName("Checking the ability to create an order for an authorized user")
    public void checkAuthorizedUserSuccessfulOrder() {
        OrderResponse orderResponse = OrderRequest.getResponseBodyCreateAuthorizedUserOrder(orderRequest, userResponse);
        checkResponseContainsUsersEmail(orderResponse,email);
    }

    @Test
    @DisplayName("Checking the inability to create an an empty order for an authorized user")
    public void checkAuthorizedUserEmptyOrder() {
        this.orderRequest = new OrderRequest();
        Response response = OrderRequest.getResponseCreateAuthorizedUserOrder(orderRequest, userResponse);
        checkResponseContainsErrorMessageEmptyOrder(response);
    }

    @Test
    @DisplayName("Checking the inability to create an order with an invalid ingredient id for an authorized user")
    public void checkAuthorizedUserInvalidHashOrder() {
        this.hash = hash + CreatingRandomData.getRandomAlexeyKolyaevString();
        this.ingredients = Arrays.asList(hash);
        this.orderRequest = new OrderRequest(ingredients);
        Response response = OrderRequest.getResponseCreateAuthorizedUserOrder(orderRequest, userResponse);
        checkResponseContainsStatusCode500(response);
    }
}
