package org.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.api.CourierApi;
import org.example.api.helper.CourierGenerator;
import org.example.api.model.CreateCourierRequest;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    CreateCourierRequest createCourierRequest;
    CourierApi courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApi();
}

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourier() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Невозможность Создания 2 курьеров с одинаковым логином")
    public void createTwoEqualsCouriersIsNotPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        courierApiClient.createCourier(createCourierRequest);
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина не возможно")
    public void createCourierWithoutLoginIsNotPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        createCourierRequest.setLogin("");
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля не возможно")
    public void createCourierWithoutPasswordIsNotPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        createCourierRequest.setPassword("");
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без FirstName возможно")
    public void createCourierWithoutFirstNameIsPossible() {
        createCourierRequest = CourierGenerator.getRandomCourier();
        createCourierRequest.setFirstName("");
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(HttpStatus.SC_CREATED);
    }
}
