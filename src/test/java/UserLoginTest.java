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

    // Перед каждым тестом формируем случайные тестовые данные и создаем пользователя
    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = CreatingRandomData.getRandomKoliaevEmail();
        this.password = CreatingRandomData.getRandomKoliaevString();
        this.name = CreatingRandomData.getRandomKoliaevString();
        User user = new User(email,password,name);
        User.registerUser(user);
    }

    // После окончания теста удаляем созданного пользователя
    @After
    public void deleteCreatedUser() {
        User user = new User(email,password);
        UserLogin userLogin = UserLogin.loginUser(user);
        UserLogin.deleteUser(userLogin);
    }

    @Test
    @DisplayName("Checking the ability of an existing user to log in")
    public void checkSuccessfulLogin() {
        User user = new User(email,password);
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. Incorrect password")
    public void checkIncorrectPasswordUnsuccessfulLogin() {
        User user = new User(email,password + new Random().nextInt(10));
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. Incorrect email")
    public void checkIncorrectEmailUnsuccessfulLogin() {
        User user = new User(new Random().nextInt(10) + email,password);
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. No password")
    public void checkNoPasswordUnsuccessfulLogin() {
        User user = new User(email,"");
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. No email")
    public void checkNoEmailUnsuccessfulLogin() {
        User user = new User("",password);
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }

    @Test
    @DisplayName("Checking the inability of a non-existent user to log in. No email, no password")
    public void checkNoEmailNoPasswordUnsuccessfulLogin() {
        User user = new User("","");
        Response response = user.getResponseLoginUser(user);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);
    }
}
