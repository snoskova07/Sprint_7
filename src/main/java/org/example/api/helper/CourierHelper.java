package org.example.api.helper;

import io.qameta.allure.Step;
import org.example.api.api.CourierApi;
import org.example.api.model.LoginCourierRequest;
import org.example.api.model.LoginCourierResponse;
import org.example.api.model.DeleteCourierRequest;
import org.example.api.model.DeleteCourierResponse;

public class CourierHelper {
    CourierApi courierApi = new CourierApi();

    @Step("Логин в систему под курьером")
    public LoginCourierResponse loginSuccess(LoginCourierRequest loginCourierRequest) {
        return courierApi.loginCourier(loginCourierRequest)
                .then()
                .statusCode(200)
                .and()
                .extract()
                .as(LoginCourierResponse.class);
    }
    @Step("Удаление курьера")
    public DeleteCourierResponse deleteSuccess(DeleteCourierRequest deleteCourierRequest, String id) {
        return courierApi.deleteCourier(deleteCourierRequest, id)
                .then()
                .statusCode(200)
                .and()
                .extract()
                .as(DeleteCourierResponse.class);
    }

}
