package org.example.api.model;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class LoginCourierResponse {
    public int id;

    @Step("Получение id курьера")
    public int getId() {
        return id;
    }
    @Step("Установка id курьера")
    public void setId(int id) {
        this.id = id;
    }
}
