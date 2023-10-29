package org.example.api.model;

import io.qameta.allure.Step;

public class CreateCourierRequest {
    public String login;
    public String password;
    public String firstName;

    public CreateCourierRequest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Получение логина курьера")
    public String getLogin() {
        return login;
    }

    @Step("Установка логина курьера")
    public void setLogin(String login) {
        this.login = login;
    }

    @Step("Получение пароля курьера")
    public String getPassword() {
        return password;
    }

    @Step("Установка пароля курьера")
    public void setPassword(String password) {
        this.password = password;
    }

    @Step("Установка имени курьера")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
