import Constants.TestStandEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreatingTest {
    private String email;
    private String password;
    private String name;

    @Before
    public void setUp() {
        RestAssured.baseURI = TestStandEndpoints.BASE_URL;
        this.email = "Kolyaev221" + new Random().nextInt(10) + "@example.com";
        this.password = "Kolyaev221" + new Random().nextInt(10);
        this.name = "Kolyaev221" + new Random().nextInt(10);
    }

    @After
    public void deleteCreatedUser() {
        User user = new User(email,password);
        UserLogin userLogin = given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(TestStandEndpoints.LOGIN).as(UserLogin.class);
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(userLogin.getAccessToken())
                .delete(TestStandEndpoints.USER);
    }

    @Test
    public void checkSuccessfulCreating() {
        User user = new User(email,password,name);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(TestStandEndpoints.REGISTER);
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
}
