package org.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.api.CourierApi;
import org.example.api.helper.CourierGenerator;
import org.example.api.helper.CourierHelper;
import org.example.api.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    CreateCourierRequest createCourierRequest;
    LoginCourierRequest loginCourierRequest;
    LoginCourierResponse loginCourierResponse;
    CourierApi courierApi;
    CourierHelper courierHelper;

    DeleteCourierRequest deleteCourierRequest;
    DeleteCourierResponse deleteCourierResponse;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
}

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourier() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        Response createResponse = courierApi.createCourier(createCourierRequest);
   //     createResponse.then().assertThat().body("ok", equalTo(true))
    //            .and().statusCode(SC_CREATED);
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        Assert.assertTrue(createCourierResponse.ok);

        courierHelper = new CourierHelper();
        loginCourierRequest  = new LoginCourierRequest(createCourierRequest.login, createCourierRequest.password);
        loginCourierResponse = courierHelper.loginSuccess(loginCourierRequest);

        String id = Integer.toString(loginCourierResponse.id);
        deleteCourierRequest = new DeleteCourierRequest(id);
        deleteCourierResponse = courierHelper.deleteSuccess(deleteCourierRequest, id);
    }

    @Test
    @DisplayName("Невозможность создания 2 курьеров с одинаковым логином")
    public void createTwoEqualsCouriersIsNotPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        courierApi.createCourier(createCourierRequest);
        Response response = courierApi.createCourier(createCourierRequest);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина не возможно")
    public void createCourierWithoutLoginIsNotPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        createCourierRequest.setLogin("");
        Response response = courierApi.createCourier(createCourierRequest);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля не возможно")
    public void createCourierWithoutPasswordIsNotPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        createCourierRequest.setPassword("");
        Response response = courierApi.createCourier(createCourierRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без FirstName возможно")
    public void createCourierWithoutFirstNameIsPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        createCourierRequest.setFirstName("");
        Response response = courierApi.createCourier(createCourierRequest);
        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(SC_CREATED);

        courierHelper = new CourierHelper();
        loginCourierRequest  = new LoginCourierRequest(createCourierRequest.login, createCourierRequest.password);
        loginCourierResponse = courierHelper.loginSuccess(loginCourierRequest);

        String id = Integer.toString(loginCourierResponse.id);
        deleteCourierRequest = new DeleteCourierRequest(id);
        deleteCourierResponse = courierHelper.deleteSuccess(deleteCourierRequest, id);

    }
}
