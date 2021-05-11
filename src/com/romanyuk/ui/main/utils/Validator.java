package com.romanyuk.ui.main.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 12;
    private static final String phoneRegex = "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?" +
            "[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$";

    public static boolean isNotValidName(String name) {
        return name == null || name.isBlank() || name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH;
    }

    public static boolean isNotValidDate(LocalDate date) {
        return date == null || date.isAfter(LocalDate.now()) ||
                date.isBefore(LocalDate.now().minusYears(100));
    }

    public static boolean isNotValidPhone(String phone) {
        return phone == null || !Pattern.compile(phoneRegex).matcher(phone).matches();
    }
}
