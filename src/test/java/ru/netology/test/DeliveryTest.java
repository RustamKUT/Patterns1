package ru.netology.test;

import com.codeborne.selenide.Selectors;
import lombok.Value;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {
    private String firstMeetingInvalidDate;

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {

        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstMeetingDate))
                .shouldBe(visible);

        //$("[data-test-id='success-notification'] button[type='button'] .icon_name_close").click();
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(Selectors.byText("Запланировать")).click();

        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
    }

    @Test
    void shouldCityNotInList() {

        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        //$("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldEmptyInCityField() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);

        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        //$("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldDateLessThreeDays() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingInvalidDate);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        //$("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldEmptyInDateField() {
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[name='name']").setValue(DataGenerator.generateName("ru"));
        //$("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldEmptyNameField() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);

        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[name='name']").setValue("");
        //$("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldLatinLettersInNameField() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);

        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("en"));
        //$("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEmptyPhoneField() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);

        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("Игорь"));
        $("[name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldMoreElevenCharactersInPhone() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);


        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(firstMeetingDate);
        $("[name='name']").setValue(DataGenerator.generateName("Иван"));
        $("[name='phone']").setValue("+79012345678999");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
    }


    }




