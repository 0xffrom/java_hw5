package com.romanyuk.domain.repositories;

import com.romanyuk.domain.entity.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> getPersons();

    void savePersons(final List<Person> persons);

    void updatePerson(final Person person);

    void savePerson(final Person person);

    void removePerson(final Person person);
}
