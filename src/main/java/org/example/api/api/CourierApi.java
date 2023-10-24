package org.example.api.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.model.CreateCourierRequest;
import org.example.api.model.DeleteCourierRequest;
import org.example.api.model.LoginCourierRequest;

import static org.example.api.config.ConfigUrl.*;

public class CourierApi extends BaseApi {

    @Step("Отправка запроса на создание курьера")
    public Response createCourier(CreateCourierRequest createCourierRequest) {
       return getPostSpec()
                .body(createCourierRequest)
                .when()
                .post(COURIER_URL);
    }
    @Step("Отправка запроса на логин курьера")
    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
            .body(loginCourierRequest)
            .when()
            .post(LOGIN_URL);
    }
    @Step("Отправка запроса на удаление курьера")
    public Response deleteCourier(DeleteCourierRequest deleteCourierRequest, String id) {
        return getPostSpec()
                .body(deleteCourierRequest)
                .when()
                .delete(DELETE_URL + id);
    }
}
