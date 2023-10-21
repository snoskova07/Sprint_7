package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.api.CourierApi;
import org.example.api.api.OrderApi;
import org.example.api.helper.CourierGenerator;
import org.example.api.model.CreateCourierRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class OrdersListTest {

    OrderApi orderApi;
    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersList() {
        Response response = orderApi.getOrdersList();
        response.then().assertThat()
                .body("orders.limit", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

}
