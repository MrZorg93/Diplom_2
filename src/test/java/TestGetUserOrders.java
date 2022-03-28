import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestGetUserOrders {

    @DisplayName("Create some count of orders and get them list back")
    @Description("In each iteration we generate new count of User orders")
    @ParameterizedTest(name = "#{index} - In this iteration number of new orders = {0}")
    @ValueSource(ints = {0, 1, 2, 3, 5, 14})
    public void testCreateSomeOrdersWithLogInAndReturnThemList(int orderCount){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = user.getEmail();
        String password = user.getPassword();
        UserAuthMethods.loginUser(email, password);
        String accessToken = UserAuthMethods.loginUser(email, password).path("accessToken").toString();
        Integer oldTotal = Integer.parseInt(OrderMethods.getOrderList(accessToken).path("total").toString());
        for(int i = 0; i < orderCount; i++) {
            OrderDataJson burger = OrderDataJson.randomBurger(true, 2, 4);
            OrderMethods.createOrderLogIn(burger, accessToken);
        }
        Response response = OrderMethods.getOrderList(accessToken);
        String successStatus = response.path("success").toString();
        String orders = response.path("orders").toString();
        Integer total = Integer.parseInt(response.path("total").toString());
        assertAll(
                () -> assertEquals(200, OrderMethods.statusCode),
                () -> assertEquals("true", successStatus),
                () -> assertNotNull(orders),
                () -> assertEquals(orderCount, total - oldTotal)
        );

    }

    @DisplayName("Create some count of orders and get them list back after logging out")
    @Description("In each iteration we generate new count of User orders")
    @ParameterizedTest(name = "#{index} - In this iteration number of new orders = {0}")
    @ValueSource(ints = {0, 1, 2, 3, 5, 14})
    public void testCreateSomeOrdersWithLogInAndReturnThemListAfterLogOut(int orderCount){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = user.getEmail();
        String password = user.getPassword();
        UserAuthMethods.loginUser(email, password);
        String accessToken = UserAuthMethods.loginUser(email, password).path("accessToken").toString();
        String refreshToken = UserAuthMethods.loginUser(email, password).path("refreshToken").toString();
        for(int i = 0; i < orderCount; i++) {
            OrderDataJson burger = OrderDataJson.randomBurger(true, 2, 4);
            OrderMethods.createOrderLogIn(burger, accessToken);
        }
        UserAuthMethods.userLogout(refreshToken);
        Response response = OrderMethods.getOrderList("");
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals(401, OrderMethods.statusCode),
                () -> assertEquals("false", successStatus),
                () -> assertEquals("You should be authorised", message)
        );

    }
}
