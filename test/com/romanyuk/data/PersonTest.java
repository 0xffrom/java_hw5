package com.romanyuk.data;

import com.romanyuk.domain.entity.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void getPhone() {
        Person person = new Person().setHomePhone("home").setMobilePhone("mobile");

        assertEquals("mobile/home", person.getPhone());

        person = new Person().setHomePhone("home");

        assertEquals("home", person.getPhone());

        person = new Person().setMobilePhone("mobile");

        assertEquals("mobile", person.getPhone());
    }
}