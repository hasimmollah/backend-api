package com.nutmeg;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerAcceptanceTest {

    @LocalServerPort
    private int port;
    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/backend-api/v1.0";
    }
    @Test
    void shouldReturnUserDetails() {
        RestAssured
                .given()
                .accept("application/json")
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }
}
