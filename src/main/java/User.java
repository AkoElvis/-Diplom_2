import Constants.TestStandEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Step("Get response for registration")
    public Response getResponseRegisterUser(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(TestStandEndpoints.REGISTER);
    }

    @Step("Register a user")
    public static void registerUser(Object body) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(TestStandEndpoints.REGISTER);
    }

    @Step("Get response for login a user")
    public Response getResponseLoginUser(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(TestStandEndpoints.LOGIN);
    }
}
