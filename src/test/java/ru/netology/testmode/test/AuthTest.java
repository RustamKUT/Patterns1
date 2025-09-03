package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.*;


public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLogIn() {
        var RegistrationInfo = generateUser("en");
        $("[data-test-id='login'] [name='login']").setValue(RegistrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(RegistrationInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[id=root]").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldLogInNoAuth() {
        var RegistrationInfo = generateUserNoAuth("en");
        $("[data-test-id='login'] [name='login']").setValue(RegistrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(RegistrationInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldBlockedUser() {
        var RegistrationInfo = generateBlockedUser("en");
        $("[data-test-id='login'] [name='login']").setValue(RegistrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(RegistrationInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldInvalidLogIn() {
        var RegistrationInfo = generateInvalidLoginUser("en");
        $("[data-test-id='login'] [name='login']").setValue(RegistrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(RegistrationInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPassword() {
        var RegistrationInfo = generateInvalidPasswordUser("en");
        $("[data-test-id='login'] [name='login']").setValue(RegistrationInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(RegistrationInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

}