package org.example.api.helper;

import org.example.api.api.CourierApi;
import org.example.api.model.LoginCourierRequest;
import org.example.api.model.LoginCourierResponse;

public class CourierHelper {
    CourierApi courierApiClient = new CourierApi();

    public LoginCourierResponse loginSuccess(LoginCourierRequest loginCourierRequest) {
        return courierApiClient.loginCourier(loginCourierRequest)
                .then()
                .statusCode(200)
                .and()
                .extract()
                .as(LoginCourierResponse.class);
    }
}
