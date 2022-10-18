import Constants.TestStandEndpoints;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class UserResponse {
    private String success;
    private String accessToken;
    private String refreshToken;
    private UserRequest user;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAccessToken() {
        String subAccessToken = accessToken.substring(7);
        return subAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    @Step("Login a user")
    public static UserResponse getLoginUserResponse(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(TestStandEndpoints.LOGIN).as(UserResponse.class);
    }

    @Step("Register a user")
    public static UserResponse getRegisterUserResponse(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(TestStandEndpoints.REGISTER).as(UserResponse.class);
    }

    @Step("Delete a user")
    public static void deleteUser(UserResponse userLogin) {
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(userLogin.getAccessToken())
                .delete(TestStandEndpoints.USER);
    }
}
