package org.example.api.client;

import io.restassured.response.Response;
import org.example.api.model.CreateCourierRequest;
import org.example.api.model.CreateOrderRequest;

import static org.example.api.config.Config.BASE_URL;
public class OrderApi extends BaseApi {

    public Response createOrder(CreateOrderRequest createOrderRequest) {
        return getPostSpec()
                .body(createOrderRequest)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }


}
