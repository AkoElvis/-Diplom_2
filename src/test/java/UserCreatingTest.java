import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
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

    // Перед каждым тестом формируем случайные тестовые данные
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomKolyaevAlexEmail();
        this.password = CreatingRandomData.getRandomKolyaevAlexString();
        this.name = CreatingRandomData.getRandomKolyaevAlexString();
    }

    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        this.user = new UserRequest(email,password);
        this.userResponse = UserResponse.getLoginUserResponse(user);
        if (userResponse.getSuccess() != false) {
            UserResponse.deleteUser(userResponse);}
    }

    @Test
    @DisplayName("Checking the ability to register a user")
    public void checkSuccessfulCreating() {
        this.user = new UserRequest(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability to register two identical users")
    public void checkExistedUserCreatingUnable() {
        this.user = new UserRequest(email,password,name);
        UserRequest.registerUser(user);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.EXISTED_LOGIN))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Checking the inability to register a user without an email")
    public void checkNoEmailCreatingUserUnable() {
        this.email = "";
        this.user = new UserRequest(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Checking the inability to register a user without a password")
    public void checkNoPasswordCreatingUserUnable() {
        this.password = "";
        this.user = new UserRequest(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Checking the inability to register a user without a name")
    public void checkNoNameCreatingUserUnable() {
        this.name = "";
        this.user = new UserRequest(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }
}
