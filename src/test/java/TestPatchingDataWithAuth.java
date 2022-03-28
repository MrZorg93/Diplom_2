import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPatchingDataWithAuth {
    @DisplayName("Check user can log in and change them data")
    @Description("")
    @Test
    public void testLogInLegalUserReturnStatus200AndCorrectBodyWhenPatchData() {
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        Response response = UserAuthMethods.loginUser(user.getEmail(), user.getPassword());
        String email = user.getEmail();
        String name = user.getName();
        String accessToken = response.path("accessToken").toString();
        response = UserAuthMethods.patchUserData(user.randomUser(10, 10, 10), accessToken);
        Integer statusCode = response.getStatusCode();
        String successStatus = response.path("success").toString();
        String emailChanged = response.path("user.email").toString();
        String nameChanged = response.path("user.name").toString();
        assertAll(
                () -> assertEquals(200, statusCode),
                () -> assertNotEquals(email, emailChanged),
                () -> assertNotEquals(name, nameChanged),
                () -> assertEquals("true", successStatus)
        );
    }

    @DisplayName("Check user can log out and change them data")
    @Description("try to patch data without logging in")
    @Test
    public void testNotLoggedInUserReturnStatus401AndErrorMessageWhenPatchData() {
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = user.getEmail();
        String password = user.getPassword();
        Response response = UserAuthMethods.loginUser(user.getEmail(), user.getPassword());
        String accessToken = response.path("accessToken").toString();
        UserAuthMethods.userReloadAccessToken(new UserAuthJson(email, password));
        System.out.println(new UserAuthJson(email, password));
        response = UserAuthMethods.patchUserData(user.randomUser(10, 10, 10), accessToken);
        Integer statusCode = response.getStatusCode();
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals(401, statusCode),
                () -> assertEquals("You should be authorised", message),
                () -> assertEquals("false", successStatus)
        );
        // Эта хренобора падает, потому что неверно исполнена api обновления Токена, либо корявое описание
    }
}

