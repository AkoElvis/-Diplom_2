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

    // Перед каждым тестом формируем случайные тестовые данные
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomKoliaevEmail();
        this.password = CreatingRandomData.getRandomKoliaevString();
        this.name = CreatingRandomData.getRandomKoliaevString();
    }

    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        User user = new User(email,password);
        UserLogin userLogin = UserLogin.loginUser(user);
        if (userLogin.getSuccess() != "false") {
        UserLogin.deleteUser(userLogin);}
    }

    @Test
    @DisplayName("Checking the ability to register a user")
    public void checkSuccessfulCreating() {
        User user = new User(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability to register two identical users")
    public void checkExistedUserCreatingUnable() {
        User user = new User(email,password,name);
        User.registerUser(user);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.EXISTED_LOGIN))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Checking the inability to register a user without an email")
    public void checkNoEmailCreatingUserUnable() {
        this.email = "";
        User user = new User(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Checking the inability to register a user without a password")
    public void checkNoPasswordCreatingUserUnable() {
        this.password = "";
        User user = new User(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Checking the inability to register a user without a name")
    public void checkNoNameCreatingUserUnable() {
        this.name = "";
        User user = new User(email,password,name);
        Response response = user.getResponseRegisterUser(user);
        response.then().assertThat().body("message", equalTo(Messages.REQUIRED_FIELDS))
                .and()
                .statusCode(403);
    }
}
