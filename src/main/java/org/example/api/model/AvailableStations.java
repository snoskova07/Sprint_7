package org.example.api.model;

import io.qameta.allure.Step;

public class AvailableStations {

    private String name;

    @Step("Получение названия станции метро")
    public String getName() {
        return name;
    }

    @Step("Установка названия станции метро")
    public void setName(String name) {
        this.name = name;
    }
}
