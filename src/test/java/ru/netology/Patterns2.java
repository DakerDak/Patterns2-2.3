package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getUser;


public class Patterns2 {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[name=\"login\"]").setValue(registeredUser.getLogin());
        $("[name=\"password\"]").setValue(registeredUser.getPassword());
        $(".button__text").click();
        $(".heading.heading_size_l").shouldHave(Condition.text("  Личный кабинет"));

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[name=\"login\"]").setValue(notRegisteredUser.getLogin());
        $("[name=\"password\"]").setValue(notRegisteredUser.getPassword());
        $(".button__text").click();
        $(".notification__title").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[name=\"login\"]").setValue(blockedUser.getLogin());
        $("[name=\"password\"]").setValue(blockedUser.getPassword());
        $(".button__text").click();
        $(".notification__title").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[name=\"login\"]").setValue(wrongLogin);
        $("[name=\"password\"]").setValue(registeredUser.getPassword());
        $(".button__text").click();
        $(".notification__title").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[name=\"login\"]").setValue(registeredUser.getLogin());
        $("[name=\"password\"]").setValue(wrongPassword);
        $(".button__text").click();
        $(".notification__title").should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15));
    }
}


//java -jar "D:\IDEA\Projects\Patterns-2\artifacts\app-ibank.jar" -P:profile=test