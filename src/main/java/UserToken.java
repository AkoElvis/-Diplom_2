public class UserToken {
    private String email;
    private String password;
    private String refreshToken;
    private String accessToken;

    public UserToken(String email, String password, String refreshToken) {
        this.email = email;
        this.password = password;
        this.refreshToken = refreshToken;
    }

    public UserToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
