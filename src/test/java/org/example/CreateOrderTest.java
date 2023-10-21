package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.example.api.helper.OrderGenerator;
import org.example.api.model.CreateOrderRequest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.api.api.OrderApi;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    CreateOrderRequest createOrderRequest;
    OrderApi orderApi;
    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] setColor() {
        return new Object[][] {
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("")},
                {Arrays.asList("BLACK", "GRAY")},
        };
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Создание заказов с различными значениями color")
    public void createOrder() throws Exception {
        createOrderRequest = OrderGenerator.getRandomOrder(color);
        Response response = orderApi.createOrder(createOrderRequest);
        response.then().assertThat().body("track", notNullValue())
                .and().statusCode(HttpStatus.SC_CREATED);
    }
}
