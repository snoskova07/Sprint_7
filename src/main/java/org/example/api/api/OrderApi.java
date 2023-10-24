package org.example.api.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.model.CreateOrderRequest;
import org.example.api.model.OrdersListRequest;

import static org.example.api.config.ConfigUrl.*;

public class OrderApi extends BaseApi {

    @Step("Отправка запроса на создание заказа")
    public Response createOrder(CreateOrderRequest createOrderRequest) {
        return getPostSpec()
                .body(createOrderRequest)
                .when()
                .post(ORDERS_URL);
    }

    @Step("Отправка запроса на получение списка заказов")
    public Response getOrdersList() {
        return getGetSpec()
                .get(ORDERS_URL);
    }

}
