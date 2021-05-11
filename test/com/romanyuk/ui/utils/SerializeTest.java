package com.romanyuk.ui.utils;

import com.romanyuk.domain.entity.Person;
import com.romanyuk.domain.entity.PersonWrapper;
import com.romanyuk.ui.main.utils.PersonSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SerializeTest {

    @Test
    void test() {
        PersonWrapper personWrapper = new PersonWrapper(List.of(new Person().setFirstName("Andrew").setSecondName("Romanyuk")));
        File file = new File("junit.test");
        PersonSerializer.serialize(personWrapper, file);
        var deserialize = PersonSerializer.deserialize(file);

        assertNotNull(deserialize);
        assertEquals(deserialize.getPersons().size(), 1);
    }
}