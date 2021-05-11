package com.romanyuk.data.repositories;

import com.romanyuk.data.db.DatabaseConn;
import com.romanyuk.data.db.converter.Converter;
import com.romanyuk.data.db.converter.PersonConverter;
import com.romanyuk.data.db.dto.PersonDto;
import com.romanyuk.domain.entity.Person;
import com.romanyuk.domain.repositories.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PersonRepositoryImpl implements PersonRepository {
    private final DatabaseConn databaseConn;
    private final Converter<Person, PersonDto> personConverter = new PersonConverter();

    public PersonRepositoryImpl(DatabaseConn databaseConn) {
        this.databaseConn = databaseConn;
    }

    @Override
    public List<Person> getPersons() {
        return databaseConn.getAll()
                .stream()
                .map(personConverter::to)
                .collect(Collectors.toList());
    }

    @Override
    public void savePersons(final List<Person> persons) {
        persons.forEach(person ->
        {
            PersonDto personDto = personConverter.from(person);
            databaseConn.insert(personDto);
            person.setId(personDto.getId());
        });
    }


    @Override
    public void updatePerson(final Person person) {
        databaseConn.update(personConverter.from(person));
    }

    @Override
    public void savePerson(final Person person) {
        PersonDto personDto = personConverter.from(person);
        databaseConn.insert(personDto);
        person.setId(person.getId());
    }

    @Override
    public void removePerson(final Person person) {
        databaseConn.delete(personConverter.from(person));
    }
}
