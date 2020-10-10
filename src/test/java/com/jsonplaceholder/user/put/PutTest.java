package com.jsonplaceholder.user.put;

import com.jsonplaceholder.base.BaseTest;
import com.jsonplaceholder.model.Address;
import com.jsonplaceholder.model.Company;
import com.jsonplaceholder.model.Geo;
import com.jsonplaceholder.model.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class PutTest extends BaseTest {

    private User user;
    private Address address;
    private Geo geo;
    private Company company;

    @BeforeEach
    public void beforeEach(){

        user = new User();
        user.setName("Marcin Test");
        user.setUsername("Marcin");
        user.setEmail("email@email.pl");
        user.setPhone("123123");
        user.setWebsite("www.test123.com");

        geo = new Geo();
        geo.setLat("-123");
        geo.setLng("432");

        address = new Address();
        address.setStreet("Somestreet");
        address.setSuite("Suit 1");
        address.setCity("SomeCity");
        address.setZipcode("11-111");
        address.setGeo(geo);

        user.setAddress(address);

        company = new Company();
        company.setName("SomeCompany");
        company.setCatchPhrase("Some company is the best");
        company.setBs("BS test");

        user.setCompany(company);

    }

    @Test
    public void updateUser(){

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .put(BASE_URL + "/" + USERS + "/" + 1)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo(user.getName());


    }
}
