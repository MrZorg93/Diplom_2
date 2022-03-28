import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAuthLogin {
    @DisplayName("Check user log in with correct email/password")
    @Description("Checking status 200, not null access token and success == true")
    @Test
    public void testLogInLegalUserReturnStatus200AndCorrectBody(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = user.getEmail();
        String password = user.getPassword();
        Response response = UserAuthMethods.loginUser(email, password);
        String successStatus = response.path("success").toString();
        String accessToken = response.path("accessToken").toString();
        assertAll(
                () -> assertEquals(200, UserAuthMethods.statusCode),
                () -> assertEquals("true", successStatus),
                () -> assertNotNull(accessToken)
        );

    }

    @DisplayName("Check user cant log in with illegal email")
    @Description("Checking status 401, message and success == false")
    @Test
    public void testLogInWsIllegalEmailReturnStatus401AndCorrectBody(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = RandomStringUtils.randomAlphabetic(10) + "@google.com";
        String password = user.getPassword();
        Response response = UserAuthMethods.loginUser(email, password);
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals(401, UserAuthMethods.statusCode),
                () -> assertEquals("false", successStatus),
                () -> assertEquals("email or password are incorrect", message)
        );

    }

    @DisplayName("Check user cant log in with illegal password")
    @Description("Checking status 401, message and success == false")
    @Test
    public void testLogInWsIllegalPasswordReturnStatus401AndCorrectBody(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = user.getEmail();
        String password = RandomStringUtils.randomAlphabetic(10);
        Response response = UserAuthMethods.loginUser(email, password);
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals(401, UserAuthMethods.statusCode),
                () -> assertEquals("false", successStatus),
                () -> assertEquals("email or password are incorrect", message)
        );

    }
}
