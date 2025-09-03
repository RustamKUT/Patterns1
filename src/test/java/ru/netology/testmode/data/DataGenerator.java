package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUpAll(RegistrationInfo info) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new RegistrationInfo(info.getLogin(), info.getPassword(), info.getStatus())) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static RegistrationInfo generateUser(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().username();
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "active"));
        return new RegistrationInfo(login, password, "active");
    }

    public static RegistrationInfo generateUserNoAuth(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().username();
        String password = faker.internet().password();
        return new RegistrationInfo(login, password, "active");
    }

    public static RegistrationInfo generateBlockedUser(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().username();
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "blocked"));
        return new RegistrationInfo(login, password, "blocked");
    }

    public static RegistrationInfo generateInvalidLoginUser(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().username();
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "active"));
        return new RegistrationInfo(faker.name().username(), password, "active");
    }

    public static RegistrationInfo generateInvalidPasswordUser(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().username();
        String password = faker.internet().password();
        setUpAll(new RegistrationInfo(login, password, "active"));
        return new RegistrationInfo(login, faker.internet().password(), "active");
    }

}