package org.example.api.model;

import io.qameta.allure.Step;

public class DeleteCourierRequest {
    private String id;

    public DeleteCourierRequest(String id) {
        this.id = id;
    }

    @Step("Получение id курьера")
    public String getId() {
        return id;
    }

    @Step("Установка id курьера")
    public void setId(String id) {
        this.id = id;
    }
}
