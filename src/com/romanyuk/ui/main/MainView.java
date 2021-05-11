package com.romanyuk.ui.main;

import com.romanyuk.domain.entity.Person;

import java.util.List;

public interface MainView {
    void setPeople(final List<Person> people);

    void addPeople(final Person person);

    void successImportPeople(final List<Person> people);

    void failedOperation(final String message);

    void showFailedPeopleAfterImport(final List<Person> people);
}
