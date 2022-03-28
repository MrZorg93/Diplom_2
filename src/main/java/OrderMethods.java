import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderMethods {
    public static Integer statusCode;

    @Step("Create Order to random burger without login")
    public static Response createOrder(OrderDataJson burger) {
        Response response = ApiSpecification.setUp()
                .when()
                .body(burger)
                .when()
                .post("orders");
        statusCode = response.getStatusCode();
        return response;
    }
    @Step("Create Order to random burger with login")
    public static Response createOrderLogIn(OrderDataJson burger, String accessToken) {
        Response response = ApiSpecification.setUpWithAuthorization(accessToken)
                .when()
                .body(burger)
                .when()
                .post("orders");
        statusCode = response.getStatusCode();
        return response;
    }
    @Step("Get orders list after login")
    public static Response getOrderList(String accessToken) {
        Response response = ApiSpecification.setUpWithAuthorization(accessToken)
                .when()
                .get("orders");
        statusCode = response.getStatusCode();
        return response;
    }
}
