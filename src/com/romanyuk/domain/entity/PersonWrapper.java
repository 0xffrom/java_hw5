package com.romanyuk.domain.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Класс-обёртка для списка Person.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class PersonWrapper implements Serializable {
    private List<Person> persons;

    public PersonWrapper(final List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(final List<Person> persons) {
        this.persons = persons;
    }
}
