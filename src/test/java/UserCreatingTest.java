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
import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreatingTest {
    private String email;
    private String password;
    private String name;
    private UserRequest user;
    private UserResponse userResponse;

    @Step("Check that the user is created. The response should contain 'success:true' and status code 200")
    public void checkResponseContainsSuccessTrue(Response response) {
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Step("Check that the user isn't created. " +
            "The response should contain the expected warning message and status code 403")
    public void checkResponseContainsWarningMessageExistedLogin(Response response) {
        response.then().assertThat().body("message", equalTo(Messages.EXISTED_LOGIN))
                .and()
                .statusCode(403);
    }

    @Step("Check that the user isn't created. " +
            "The response should contain the expected warning message and status code 403")
    public void checkResponseContainsWarningMessageRequiredFields(Response response) {
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }

    // Перед каждым тестом формируем случайные тестовые данные
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomAlexeyKolyaevEmail();
        this.password = CreatingRandomData.getRandomAlexeyKolyaevString();
        this.name = CreatingRandomData.getRandomAlexeyKolyaevString();
    }

    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        this.user = new UserRequest(email,password);
        this.userResponse = UserResponse.getLoginUserResponse(user);
        if (userResponse.getSuccess()) {
           UserResponse.deleteUser(userResponse);}
    }

    @Test
    @DisplayName("Checking the ability to register a user")
    public void checkSuccessfulCreating() {
        this.user = new UserRequest(email,password,name);
        Response response = UserRequest.getResponseRegisterUser(user);
        checkResponseContainsSuccessTrue(response);
    }

    @Test
    @DisplayName("Checking the inability to register two identical users")
    public void checkExistedUserCreatingUnable() {
        this.user = new UserRequest(email,password,name);
        UserRequest.registerUser(user);
        Response response = UserRequest.getResponseRegisterUser(user);
        checkResponseContainsWarningMessageExistedLogin(response);
    }

    @Test
    @DisplayName("Checking the inability to register a user without an email")
    public void checkNoEmailCreatingUserUnable() {
        this.user = new UserRequest("",password,name);
        Response response = UserRequest.getResponseRegisterUser(user);
        checkResponseContainsWarningMessageRequiredFields(response);
    }

    @Test
    @DisplayName("Checking the inability to register a user without a password")
    public void checkNoPasswordCreatingUserUnable() {
        this.user = new UserRequest(email,"",name);
        Response response = UserRequest.getResponseRegisterUser(user);
        checkResponseContainsWarningMessageRequiredFields(response);
    }

    @Test
    @DisplayName("Checking the inability to register a user without a name")
    public void checkNoNameCreatingUserUnable() {
        this.user = new UserRequest(email,password,"");
        Response response = UserRequest.getResponseRegisterUser(user);
        checkResponseContainsWarningMessageRequiredFields(response);
    }
}
