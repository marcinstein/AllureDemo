package com.jsonplaceholder.user.post;

import com.jsonplaceholder.base.BaseTest;
import com.jsonplaceholder.model.Address;
import com.jsonplaceholder.model.Company;
import com.jsonplaceholder.model.Geo;
import com.jsonplaceholder.model.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class PostTest extends BaseTest {

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
    public void createNewUser(){

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(BASE_URL + "/" + USERS)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo(user.getName());

    }

    @DisplayName("Create user with specific email")
    @ParameterizedTest(name = "Create user with specific email: {0} ")
    @MethodSource("createUserEmailData")
    public void createUserWithSpecificEmail(String email){
        user.setEmail(email);

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(BASE_URL + "/" + USERS)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo(user.getName());
        assertThat(json.getString("email")).isEqualTo(user.getEmail());

    }

    private static Stream<Arguments> createUserEmailData(){
        return Stream.of(
                Arguments.of("test@testemail.com"),
                Arguments.of("test@testemail.pl"),
                Arguments.of("test@testemail.eu")
        );

    }
}
