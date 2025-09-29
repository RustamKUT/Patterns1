package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static Random random = new Random();

    private DataGenerator() {
    }

    public static String generateDate(int shift, int range) {
        return LocalDate.now().plusDays(0 + shift).plusDays(random.nextInt(range))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] city = new String[]{"Москва", "Санкт-Петербург", "Сочи", "Калининград", "Казань", "Нижний Новгород",
                "Екатеринбург", "Ярославль", "Владивосток", "Геленджик"};
        return city[random.nextInt(city.length)];
    }

    public static String generateInvalidCity() {
        String[] city = new String[]{"Готэм-Сити", "Спрингфилд", "Капитолий", "Мистик Фоллс", "Шир", "Хогсмид",
                "Старз Холлоу", "Облачный город", "Литтл Уингинг", "Изумрудный город"};
        return city[random.nextInt(city.length)];
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String generateComplexName() {
        String[] name = new String[]{"Петров Александр Дмитриевич", "Иванова Мария Сергеевна",
                "Смирнов Дмитрий Алексеевич", "Васильева Анна Михайловна", "Канюхов Фёдор Викторович"};
        return name[random.nextInt(name.length)];
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

}