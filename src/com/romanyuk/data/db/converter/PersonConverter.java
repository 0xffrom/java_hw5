package com.romanyuk.data.db.converter;

import com.romanyuk.data.db.dto.PersonDto;
import com.romanyuk.domain.entity.Person;

import java.sql.Timestamp;
import java.util.Objects;

public class PersonConverter implements Converter<Person, PersonDto> {
    /**
     * @throws NullPointerException - Если имя или фамилия или адрес или ДР равны null.
     * @throws RuntimeException     - Требование к присуствутвию хотя бы одного телефона.
     */
    @Override
    public PersonDto from(final Person person) {
        Objects.requireNonNull(person.getFirstName());
        Objects.requireNonNull(person.getSecondName());
        Objects.requireNonNull(person.getAddress());
        Objects.requireNonNull(person.getBirthDay());

        if ((person.getHomePhone() == null || person.getHomePhone().isBlank()) &&
                (person.getMobilePhone() == null || person.getMobilePhone().isBlank())) {
            throw new RuntimeException("Mobile phone or home phone must be not empty");
        }

        Timestamp timestamp = Timestamp.valueOf(person.getBirthDay().atStartOfDay());

        return new PersonDto(
                person.getId(),
                person.getFirstName(),
                person.getSecondName(),
                person.getThirdName(),
                person.getMobilePhone(),
                person.getHomePhone(),
                person.getAddress(),
                timestamp,
                person.getComment()
        );
    }

    @Override
    public Person to(final PersonDto personDTO) {
        return new Person(personDTO.getId(),
                personDTO.getFirstName(),
                personDTO.getSecondName(),
                personDTO.getThirdName(),
                personDTO.getMobilePhone(),
                personDTO.getHomePhone(),
                personDTO.getAddress(),
                personDTO.getBirthDay().toLocalDateTime().toLocalDate(),
                personDTO.getComment()
        );
    }
}
