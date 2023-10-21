package org.example;
import org.example.api.model.CreateCourierRequest;
import org.example.api.model.LoginCourierResponse;
import io.restassured.RestAssured;
import org.example.api.model.LoginCourierRequest;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
public class TestLogin {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    // Создание курьера
    @Test
    public void loginByCourier() {
        String login = "login" + new Random().nextInt(10000);
        String password = "password" + new Random().nextInt(10000);
        String firstName ="Fedor" + new Random().nextInt(10000);
        CreateCourierRequest courier  = new CreateCourierRequest(login, password, firstName); // создай объект, который соответствует JSON
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        LoginCourierRequest loginToSystem  = new LoginCourierRequest(login, password);
        LoginCourierResponse delogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginToSystem)
                .when()
                .post("/api/v1/courier/login").as(LoginCourierResponse.class);
        MatcherAssert.assertThat(delogin, notNullValue());
        int id = delogin.getId();
        System.out.println(id);
    }

}
