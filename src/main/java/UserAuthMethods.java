import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserAuthMethods {
    public static Integer statusCode;

    @Step("Register {user} and get status code and get user data from body")
    public static Response registerUser(UserAuthJson user) {
        Response response = ApiSpecification.setUp()
                .when()
                .body(user)
                .when()
                .post("auth/register");
        statusCode = response.getStatusCode();
        System.out.println(response.getBody().toString());
        return response;
    }

    @Step("Login {user} and get status code and get user data from body")
    public static Response loginUser(String email, String password) {
        Response response = ApiSpecification.setUp()
                .when()
                .body(new UserAuthJson(email, password))
                .when()
                .post("auth/login");
        statusCode = response.getStatusCode();
        System.out.println(response.getBody().toString());
        return response;
    }

    @Step("Get data about {user}")
    public static Response getUserData(UserAuthJson user, String accessToken) {
        Response response = ApiSpecification.setUpWithAuthorization(accessToken)
                .when()
                .body(user)
                .when()
                .get("auth/user");
        statusCode = response.getStatusCode();
        System.out.println(response.getBody().toString());
        return response;
    }

    @Step("Patch user data for {user}")
    public static Response patchUserData(UserAuthJson user, String accessToken) {
        Response response = ApiSpecification.setUpWithAuthorization(accessToken)
                .when()
                .body(user)
                .when()
                .patch("auth/user");
        statusCode = response.getStatusCode();
        System.out.println(response.getBody().toString());
        return response;
    }

    @Step("Logout from site")
    public static void userLogout(String refreshToken) {
        ApiSpecification.setUp()
                .when()
                .body("{\n" +
                        "\"token\": \"" + refreshToken + "\"\n" +
                        "}")
                .when()
                .post("auth/logout");
    }

    @Step("Get new access token")
    public static void userReloadAccessToken(UserAuthJson user) {
        ApiSpecification.setUp()
                .when()
                .body(user)
                .when()
                .post("auth/token");
    }
}
