import Constants.Messages;
import Constants.TestStandEndpoints;
import TestData.CreatingRandomData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {
    private String email;
    private String password;
    private String name;
    private UserResponse userResponse;
    private UserRequest user;

    // Перед каждым тестом формируем случайные тестовые данные и создаем пользователя
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomKolyaevAlexeyEmail();
        this.password = CreatingRandomData.getRandomKolyaevAlexeyString();
        this.name = CreatingRandomData.getRandomKolyaevAlexeyString();
        this.user = new UserRequest(email,password,name);
        this.userResponse = UserResponse.getRegisterUserResponse(user);
    }

    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        UserResponse.deleteUser(userResponse);
    }

    @Test
    @DisplayName("Checking the ability of an existing user to log in")
    public void checkSuccessfulLogin() {
        UserRequest user = new UserRequest(email,password);
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. Incorrect password")
    public void checkIncorrectPasswordUnsuccessfulLogin() {
        UserRequest user = new UserRequest(email,password + new Random().nextInt(10));
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("message", equalTo(Messages.INCORRECT_FIELD))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. Incorrect email")
    public void checkIncorrectEmailUnsuccessfulLogin() {
        UserRequest user = new UserRequest(new Random().nextInt(10) + email,password);
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("message", equalTo(Messages.INCORRECT_FIELD))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. No password")
    public void checkNoPasswordUnsuccessfulLogin() {
        UserRequest user = new UserRequest(email,"");
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("message", equalTo(Messages.INCORRECT_FIELD))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. No email")
    public void checkNoEmailUnsuccessfulLogin() {
        UserRequest user = new UserRequest("",password);
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("message", equalTo(Messages.INCORRECT_FIELD))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. No email, no password")
    public void checkNoEmailNoPasswordUnsuccessfulLogin() {
        UserRequest user = new UserRequest("","");
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("message", equalTo(Messages.INCORRECT_FIELD))
                .and()
                .statusCode(401);
    }
}
