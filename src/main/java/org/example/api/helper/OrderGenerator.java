package org.example.api.helper;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.api.model.CreateCourierRequest;
import org.example.api.model.CreateOrderRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderGenerator {
    public static CreateOrderRequest getRandomOrder(List<String> color) {
        String firstName = "fName" + RandomStringUtils.randomAlphabetic(10);
        String lastName = "lName" + RandomStringUtils.randomAlphabetic(10);
        String address = "address" + RandomStringUtils.randomAlphabetic(10);
        String metroStation = "metroStation" + RandomStringUtils.randomAlphabetic(10);
        String phone = "phone" + RandomStringUtils.randomAlphabetic(10);
        int rentTime = new Random().nextInt(5);
        String deliveryDate = "2020-06-06";
        String comment = "comment" + RandomStringUtils.randomAlphabetic(10);
        color = color;
        return new CreateOrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
