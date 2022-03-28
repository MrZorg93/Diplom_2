import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreateOrder {

    @DisplayName("Create order with burger with all types of components after logging in")
    @Description("In each iteration check what our burger will have: with buns?; how many sauces?; how many fillings?")
    @ParameterizedTest()
    @CsvSource({
            "true, 0, 0",
            "true, 0, 1",
            "true, 1, 0",
            "true, 1, 1",
            "true, 2, 2",
            "true, 5, 5",
    })
    public void testCreateOrderWithAllFieldsWithLogIn(boolean withBun, int withSauce, int withFillings){
        UserAuthJson user = UserAuthJson.randomUser(10, 10, 10);
        UserAuthMethods.registerUser(user);
        String email = user.getEmail();
        String password = user.getPassword();
        UserAuthMethods.loginUser(email, password);
        String accessToken = UserAuthMethods.loginUser(email, password).path("accessToken").toString();
        OrderDataJson burger = OrderDataJson.randomBurger(withBun, withSauce, withFillings);
        Response response = OrderMethods.createOrderLogIn(burger, accessToken);
        String successStatus = response.path("success").toString();
        String name = response.path("name").toString();
        String orderNumber = response.path("order.number").toString();
        assertAll(
                () -> assertEquals(200, OrderMethods.statusCode),
                () -> assertEquals("true", successStatus),
                () -> assertNotNull(name),
                () -> assertNotNull(orderNumber)
        );

    }

    @DisplayName("Create order with burger with all types of components WITHOUT logging in")
    @Description("In each iteration check what our burger will have: with buns?; how many sauces?; how many fillings?")
    @ParameterizedTest()
    @CsvSource({
            "true, 0, 0",
            "true, 0, 1",
            "true, 1, 0",
            "true, 1, 1",
            "true, 2, 2",
            "true, 5, 5",
    })
    public void testCreateOrderWithAllFieldsWithoutLogIn(boolean withBun, int withSauce, int withFillings){
        OrderDataJson burger = OrderDataJson.randomBurger(withBun, withSauce, withFillings);
        Response response = OrderMethods.createOrder(burger);
        String successStatus = response.path("success").toString();
        String name = response.path("name").toString();
        String orderNumber = response.path("order.number").toString();
        assertAll(
                () -> assertEquals(200, OrderMethods.statusCode),
                () -> assertEquals("true", successStatus),
                () -> assertNotNull(name),
                () -> assertNotNull(orderNumber)
        );

    }

    @DisplayName("Create order without burger components and WITHOUT logging in")
    @Description("Check that we can't create order without components")
    @Test()
    public void testCreateOrderWithoutIngredients(){
        Response response = ApiSpecification.setUp()
                .when()
                .body("{\"ingredients\": \"\"}")
                .when()
                .post("orders");
        String successStatus = response.path("success").toString();
        String message = response.path("message").toString();
        assertAll(
                () -> assertEquals(400, response.getStatusCode()),
                () -> assertEquals("false", successStatus),
                () -> assertEquals("Ingredient ids must be provided", message)
        );

    }

    @DisplayName("Create order with INVALID burger components and WITHOUT logging in")
    @Description("Check, that we can't use invalid component's hash")
    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 5})
    public void testCreateOrderWithInvalidHashOfIngredients(int count){
        OrderDataJson burger = OrderDataJson.invalidRandomBurger(count);
        OrderMethods.createOrder(burger);
        assertEquals(500, OrderMethods.statusCode);

    }
}
