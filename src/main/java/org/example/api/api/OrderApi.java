package org.example.api.api;

import io.restassured.response.Response;
import org.example.api.model.CreateOrderRequest;
import org.example.api.model.OrdersListRequest;

import static org.example.api.config.ConfigUrl.BASE_URL;
public class OrderApi extends BaseApi {

    public Response createOrder(CreateOrderRequest createOrderRequest) {
        return getPostSpec()
                .body(createOrderRequest)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    public Response getOrdersList() {
        return getGetSpec()
                .get(BASE_URL + "/api/v1/orders");
    }

}
