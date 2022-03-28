import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiSpecification {
    public static RequestSpecification setUp() {
        return given()
                .baseUri("https://stellarburgers.nomoreparties.site/api/")
                .header("Content-type", "application/json")
                .auth().none();
    }
    public static RequestSpecification setUpWithAuthorization(String accessToken) {
        return given()
                .baseUri("https://stellarburgers.nomoreparties.site/api/")
                .header("Content-type", "application/json")
                .header("Authorization", accessToken);
    }
}


