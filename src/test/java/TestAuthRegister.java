import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAuthRegister {


    @DisplayName("Check user creation with correct email")
    @Description("Checking status code 200")
    @ParameterizedTest(name = "#{index} - email length = {0}")
    @ValueSource(ints = {2, 3, 5, 10, 50})
    public void testRegistrationNewUserReturn200WhenEmailOfDifferentLength(Integer arg){
        UserAuthJson user = UserAuthJson.randomUser(arg, 10, 10);
        UserAuthMethods.registerUser(user);
        assertEquals(200, UserAuthMethods.statusCode);
    }

    @DisplayName("Check user creation with correct password")
    @Description("Checking status code 200")
    @ParameterizedTest(name = "#{index} - password length = {0}")
    @ValueSource(ints = {2, 3, 5, 10, 50})
    public void testRegistrationNewUserReturn200WhenPasswordOfDifferentLength(Integer arg){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, arg);
        UserAuthMethods.registerUser(user);
        assertEquals(200, UserAuthMethods.statusCode);
    }

    @DisplayName("Check user creation with correct name")
    @Description("Checking status code 200")
    @ParameterizedTest(name = "#{index} - name length = {0}")
    @ValueSource(ints = {2, 3, 5, 10, 50})
    public void testRegistrationNewUserReturn200WhenNameOfDifferentLength(Integer arg){
        UserAuthJson user = UserAuthJson.randomUser(10, arg, 10);
        UserAuthMethods.registerUser(user);
        assertEquals(200, UserAuthMethods.statusCode);
    }

    @DisplayName("Check user creation with correct body fields")
    @Description("Checking fields Success is not null")
    @Test
    public void testRegistrationNewUserReturnFieldSuccessNotNull(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        assertNotNull(UserAuthMethods.registerUser(user).path("success"));
    }

    @DisplayName("Check user creation with correct body fields")
    @Description("Checking fields User is not null")
    @Test
    public void testRegistrationNewUserReturnFieldUserNotNull(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        assertNotNull(UserAuthMethods.registerUser(user).path("user"));
    }

    @DisplayName("Check user creation with correct body fields")
    @Description("Checking fields accessToken is not null")
    @Test
    public void testRegistrationNewUserReturnFieldAccessTokenNotNull(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        assertNotNull(UserAuthMethods.registerUser(user).path("accessToken"));
    }

    @DisplayName("Check user creation with correct body fields")
    @Description("Checking fields refreshToken is not null")
    @Test
    public void testRegistrationNewUserReturnFieldRefreshTokenNotNull(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        assertNotNull(UserAuthMethods.registerUser(user).path("refreshToken"));
    }

    @DisplayName("Check user can not register twice")
    @Description("Checking status is 403 and field success equal 'false'")
    @Test
    public void testRegistrationNewUserTwice(){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        Response response = UserAuthMethods.registerUser(user);
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals("false", successStatus),
                () -> assertEquals(403, UserAuthMethods.statusCode),
                () -> assertEquals("User already exists", message)
        );
    }

    @DisplayName("Check user can not register without one of required fields")
    @Description("Checking status is 403 and field success equal 'false'")
    @ParameterizedTest(name = "#{index} - try to auth without = {0}")
    @ValueSource(strings = {"email", "name", "password"})
    public void testRegistrationNewUserWithoutRequiredFields(String parameter){
        UserAuthJson user = switch (parameter) {
            case ("email") -> UserAuthJson.randomUser(0, 10, 10);
            case ("name") -> UserAuthJson.randomUser(10, 0, 10);
            case ("password") -> UserAuthJson.randomUser(10, 10, 0);
            default -> UserAuthJson.randomUser(0,0,0);
        };
        UserAuthMethods.registerUser(user);
        Response response = UserAuthMethods.registerUser(user);
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals("false", successStatus),
                () -> assertEquals(403, UserAuthMethods.statusCode),
                () -> assertEquals("Email, password and name are required fields", message)
        );
    }
}
