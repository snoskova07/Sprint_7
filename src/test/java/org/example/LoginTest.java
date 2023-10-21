package org.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.api.api.CourierApi;
import org.example.api.helper.CourierGenerator;
import org.example.api.helper.CourierHelper;
import org.example.api.model.CreateCourierRequest;
import org.example.api.model.LoginCourierRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class LoginTest {

    CreateCourierRequest courierRequest;
    LoginCourierRequest loginRequest;
    CourierApi courierApiClient;
    CourierHelper courierHelper;

    @Before
    public void setup() {
        courierApiClient = new CourierApi();
        courierRequest = CourierGenerator.getRandomCourier();
        courierApiClient.createCourier(courierRequest);
    }

    @Test
    @DisplayName("Успешный логин под курьером")
    public void successLoginByCourier() {
        loginRequest  = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginRequest);
        response.then().assertThat().body("id", notNullValue())
                .and().statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Вход с пустым полем login")
    public void cannotLoginWithoutLogin() {
        courierRequest.setLogin("");
        loginRequest  = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Вход с пустым полем password")
    public void cannotLoginWithoutPassword() {
        courierRequest.setPassword("");
        loginRequest  = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Вход с неверным login")
    public void cannotLoginWithWrongLogin() {
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        loginRequest  = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginRequest);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Вход с неверным password")
    public void cannotLoginWithWrongPassword() {
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        loginRequest  = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginRequest);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
