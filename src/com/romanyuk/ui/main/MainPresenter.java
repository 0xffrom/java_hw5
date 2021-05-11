package com.romanyuk.ui.main;

import com.romanyuk.domain.entity.Person;

import java.io.File;
import java.util.List;

public interface MainPresenter {
    void loadPersons();

    void importFile(final File file, final List<Person> personList);

    void exportFile(final File file, final List<Person> personList);

    void addPerson(final Person person, final List<Person> personList);

    void removePerson(final Person person);

    void updatePerson(final Person person);
}
