package com.jsonplaceholder.user.delete;

import com.jsonplaceholder.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteTest extends BaseTest {

    @Test
    public void deleteUser(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + "/" + USERS + "/" + 1)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void deleteAllUsers(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + "/" + USERS)
                .then()
                .statusCode(404)
                .extract()
                .response();
    }

}
