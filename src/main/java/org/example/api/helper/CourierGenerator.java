package org.example.api.helper;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.api.model.CreateCourierRequest;

public class CourierGenerator {

    @Step("Генерация данных курьера")
    public static CreateCourierRequest getRandomCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CreateCourierRequest(login, password, firstName);
    }
}
