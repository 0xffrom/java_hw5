package com.romanyuk.ui.utils;

import com.romanyuk.ui.main.utils.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void isNotValidName() {
        assertTrue(Validator.isNotValidName("       "));
        assertTrue(Validator.isNotValidName("  "));
        assertTrue(Validator.isNotValidName(null));
        assertTrue(Validator.isNotValidName("a"));
        assertTrue(Validator.isNotValidName("1"));
        assertTrue(Validator.isNotValidName("-"));
        assertFalse(Validator.isNotValidName("KDA"));
        assertFalse(Validator.isNotValidName("Max"));
        assertFalse(Validator.isNotValidName("max"));
        assertFalse(Validator.isNotValidName("Andrew"));
        assertFalse(Validator.isNotValidName("ALik"));
        assertFalse(Validator.isNotValidName("51251251251"));
        assertFalse(Validator.isNotValidName("asdsad"));
    }

    @Test
    void isNotValidDate() {
        assertTrue(Validator.isNotValidDate(LocalDate.MAX));
        assertTrue(Validator.isNotValidDate(LocalDate.MIN));
        assertTrue(Validator.isNotValidDate(LocalDate.now().minusYears(101)));
        assertTrue(Validator.isNotValidDate(LocalDate.now().plusDays(1)));
        assertTrue(Validator.isNotValidDate(LocalDate.now().plusMonths(1)));
        assertFalse(Validator.isNotValidDate(LocalDate.now().minusYears(4)));
        assertFalse(Validator.isNotValidDate(LocalDate.now().minusYears(80)));
        assertFalse(Validator.isNotValidDate(LocalDate.now().minusDays(14).minusYears(5).minusMonths(142)));
    }

    @Test
    void isNotValidPhone() {
        assertFalse(Validator.isNotValidPhone("+79887026936"));
        assertFalse(Validator.isNotValidPhone("+79007006031"));
        assertFalse(Validator.isNotValidPhone("79117521236"));
        assertFalse(Validator.isNotValidPhone("89887026936"));
        assertFalse(Validator.isNotValidPhone("88005553535"));
        assertTrue(Validator.isNotValidPhone("+7-081-702 64 32"));
        assertTrue(Validator.isNotValidPhone("+81-400-212-32-44"));
        assertTrue(Validator.isNotValidPhone("888888888888"));
        assertTrue(Validator.isNotValidPhone("+799999999999"));
        assertTrue(Validator.isNotValidPhone("70000000000"));
        assertTrue(Validator.isNotValidPhone("ffdsfsdf"));
        assertTrue(Validator.isNotValidPhone("Maxim"));
        assertTrue(Validator.isNotValidPhone("12"));
        assertTrue(Validator.isNotValidPhone("62612612616162"));
    }
}