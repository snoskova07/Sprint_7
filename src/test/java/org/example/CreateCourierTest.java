package org.example;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.client.CourierApiClient;
import org.example.api.helper.CourierGeneratorHelper;
import org.example.api.model.CreateCourierRequest;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class TestCourier {
    CreateCourierRequest createCourierRequest;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
}

// Успешное создание курьера
    @Test
    public void createCourier() {
        createCourierRequest = CourierGeneratorHelper.getRandomCourier();
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(HttpStatus.SC_CREATED);
    }

    // Невозможность Создания 2 курьеров с одинаковым логином
    @Test
    public void createTwoEqualsCouriersIsNotPossible() {
        createCourierRequest = CourierGeneratorHelper.getRandomCourier();
        courierApiClient.createCourier(createCourierRequest);
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and().statusCode(HttpStatus.SC_CONFLICT);
    }

    //Создание курьера без логина не возможно
    @Test
    public void createCourierWithoutLoginIsNotPossible() {
        createCourierRequest = CourierGeneratorHelper.getRandomCourier();
        createCourierRequest.setLogin("");
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //Создание курьера без пароля не возможно
    @Test
    public void createCourierWithoutPasswordIsNotPossible() {
        createCourierRequest = CourierGeneratorHelper.getRandomCourier();
        createCourierRequest.setPassword("");
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //Создание курьера без FirstName возможно
    @Test
    public void createCourierWithoutFirstNameIsPossible() {
        createCourierRequest = CourierGeneratorHelper.getRandomCourier();
        createCourierRequest.setFirstName("");
        Response response = courierApiClient.createCourier(createCourierRequest);
        response.then().assertThat().body("ok", equalTo(true))
                .and().statusCode(HttpStatus.SC_CREATED);
    }
}
