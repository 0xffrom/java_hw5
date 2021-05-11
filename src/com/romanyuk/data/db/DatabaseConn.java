package com.romanyuk.data.db;

import com.romanyuk.data.db.dto.PersonDto;

import java.util.List;

public interface DatabaseConn {
    void insert(final PersonDto personDto);

    void update(final PersonDto personDto);

    void delete(final PersonDto personDto);

    List<PersonDto> getAll();
}
