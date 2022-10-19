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
import static org.junit.Assert.assertEquals;

public class CreateOrderAuthorizedUserTest {

    private List<String> ingredients;
    private String hash;
    private OrderRequest orderRequest;
    private String email;
    private String password;
    private String name;
    private UserResponse userResponse;
    private UserRequest user;
    private OrderResponse orderResponse;

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
    }
    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        UserResponse.deleteUser(userResponse);
    }

    @Test
    @DisplayName("Checking the ability to create an order for an authorized user")
    public void checkAuthorizedUserSuccessfulOrder() {
        this.orderResponse = orderRequest.getResponseBodyCreateAuthorizedUserOrder(orderRequest, userResponse);
        assertEquals(orderResponse.getOrder().getOwner().getEmail(), email.toLowerCase());
    }

    @Test
    @DisplayName("Checking the inability to create an an empty order for an authorized user")
    public void checkAuthorizedUserEmptyOrder() {
        this.orderRequest = new OrderRequest();
        Response response = orderRequest.getResponseCreateAuthorizedUserOrder(orderRequest, userResponse);
        response.then().assertThat().body("message", equalTo(Messages.EMPTY_ORDER))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Checking the inability to create an order with an invalid ingredient id for an authorized user")
    public void checkAuthorizedUserInvalidHashOrder() {
        this.hash = hash + CreatingRandomData.getRandomAlexeyKolyaevString();
        this.ingredients = Arrays.asList(hash);
        this.orderRequest = new OrderRequest(ingredients);
        Response response = orderRequest.getResponseCreateAuthorizedUserOrder(orderRequest, userResponse);
        response.then().assertThat().statusCode(500);
    }
}
