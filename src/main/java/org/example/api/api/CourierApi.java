package org.example.api.client;

import io.restassured.response.Response;
import org.example.api.model.CreateCourierRequest;
import org.example.api.model.LoginCourierRequest;
import static org.example.api.config.Config.BASE_URL;

public class CourierApi extends BaseApi {

    public Response createCourier(CreateCourierRequest createCourierRequest) {
       return getPostSpec()
                .body(createCourierRequest)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
            .body(loginCourierRequest)
            .when()
            .post(BASE_URL + "/api/v1/courier/login");
    }




}
