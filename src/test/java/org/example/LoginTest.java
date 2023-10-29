package org.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.example.api.api.CourierApi;
import org.example.api.helper.CourierGenerator;
import org.example.api.helper.CourierHelper;
import org.example.api.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginTest {

    CreateCourierRequest courierRequest;
    LoginCourierRequest loginCourierRequest;
    LoginCourierResponse loginCourierResponse;
    DeleteCourierRequest deleteCourierRequest;
    DeleteCourierResponse deleteCourierResponse;
    CourierApi courierApiClient;
    CourierHelper courierHelper;
    Response response;
    String login;
    String password;
    @Before
    public void setup() {
        courierApiClient = new CourierApi();
        courierRequest = CourierGenerator.getRandomCourier();
        courierApiClient.createCourier(courierRequest);
        login = courierRequest.getLogin();
        password = courierRequest.getPassword();
    }

    @After
    public void deleteCourier() {

        String id = Integer.toString(loginCourierResponse.id);
        deleteCourierRequest = new DeleteCourierRequest(id);
        deleteCourierResponse = courierHelper.deleteSuccess(deleteCourierRequest, id);

    }

    @Test
    @DisplayName("Успешный логин под курьером")
    public void successLoginByCourier() {
        loginCourierRequest = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        response = courierApiClient.loginCourier(loginCourierRequest);
        loginCourierResponse = courierHelper.loginSuccess(loginCourierRequest);
        assertEquals(SC_OK, response.statusCode());
        assertNotNull(loginCourierResponse.id);
    }

    @Test
    @DisplayName("Вход с пустым полем login невозможен")
    public void cannotLoginWithoutLogin() {
        courierRequest.setLogin("");
        loginCourierRequest = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginCourierRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
        courierRequest.setLogin(login);
        successLoginByCourier();
    }

    @Test
    @DisplayName("Вход с пустым полем password невозможен")
    public void cannotLoginWithoutPassword() {
        courierRequest.setPassword("");
        loginCourierRequest = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginCourierRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
        courierRequest.setPassword(password);
        successLoginByCourier();
    }

    @Test
    @DisplayName("Вход с неверным login невозможен")
    public void cannotLoginWithWrongLogin() {
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        loginCourierRequest = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginCourierRequest);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(HttpStatus.SC_NOT_FOUND);
        courierRequest.setLogin(login);
        successLoginByCourier();
    }

    @Test
    @DisplayName("Вход с неверным password невозможен")
    public void cannotLoginWithWrongPassword() {
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        loginCourierRequest = new LoginCourierRequest(courierRequest.getLogin(), courierRequest.getPassword());
        courierHelper = new CourierHelper();
        Response response = courierApiClient.loginCourier(loginCourierRequest);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and().statusCode(HttpStatus.SC_NOT_FOUND);
        courierRequest.setPassword(password);
        successLoginByCourier();
    }

}
