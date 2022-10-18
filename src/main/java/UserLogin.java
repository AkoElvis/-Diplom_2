import Constants.TestStandEndpoints;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class UserLogin {
    private String success;
    private String accessToken;
    private String refreshToken;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Step("Login a user")
    public static UserLogin loginUser(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(TestStandEndpoints.LOGIN).as(UserLogin.class);
    }

    @Step("Delete a user")
    public static void deleteUser(UserLogin userLogin) {
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(userLogin.getAccessToken())
                .delete(TestStandEndpoints.USER);
    }
}
