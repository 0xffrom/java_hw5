package com.romanyuk.data.db;

import com.romanyuk.data.db.dto.PersonDto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DatabaseConnImpl implements DatabaseConn {
    private static DatabaseConn instance;

    private DatabaseConnImpl() {
        try (Connection conn = getConnection();
             Statement stat = conn.createStatement()) {
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "PERSONS", null);
            if (!resultSet.next()) {
                stat.executeUpdate("CREATE TABLE Persons (" +
                        "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                        "FIRST_NAME VARCHAR(255) not null," +
                        "SECOND_NAME VARCHAR(255) not null," +
                        "THIRD_NAME VARCHAR(255)," +
                        "MOBILE_PHONE VARCHAR(255)," +
                        "HOME_PHONE VARCHAR(255)," +
                        "ADDRESS VARCHAR(255)," +
                        "BIRTH_DAY DATE," +
                        "COMMENT VARCHAR(255)" +
                        ")");
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    // Double-checked locking Singleton. [См. Effective Java].
    public static DatabaseConn getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnImpl.class) {
                if (instance == null) {
                    instance = new DatabaseConnImpl();
                }
                return instance;
            }
        }
        return instance;
    }

    @Override
    public void insert(final PersonDto personDto) {
        try (Connection conn = getConnection();
             PreparedStatement prep = conn.prepareStatement(
                     "INSERT INTO PERSONS(FIRST_NAME, SECOND_NAME, THIRD_NAME, MOBILE_PHONE, HOME_PHONE, ADDRESS, " +
                             "BIRTH_DAY, COMMENT) VALUES (?,?, ?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            setToStatement(personDto, prep);

            prep.executeUpdate();

            try (ResultSet generatedKeys = prep.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    personDto.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setToStatement(final PersonDto personDto, final PreparedStatement prep) throws SQLException {
        prep.setString(1, personDto.getFirstName());
        prep.setString(2, personDto.getSecondName());
        prep.setString(3, personDto.getThirdName());
        prep.setString(4, personDto.getMobilePhone());
        prep.setString(5, personDto.getHomePhone());
        prep.setString(6, personDto.getAddress());
        prep.setTimestamp(7, personDto.getBirthDay());
        prep.setString(8, personDto.getComment());
    }

    @Override
    public void update(final PersonDto personDto) {
        try (Connection conn = getConnection();
             PreparedStatement prep = conn.prepareStatement(
                     "UPDATE Persons SET first_name = ?, second_name = ?, third_name = ?, mobile_phone = ?, " +
                             "home_phone = ?, address = ?, birth_day =?, comment =? WHERE id = ?")) {

            setToStatement(personDto, prep);
            prep.setObject(9, personDto.getId());
            prep.executeUpdate();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(final PersonDto personDto) {
        try (Connection conn = getConnection();
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(String.format("DELETE FROM Persons WHERE id = %d", personDto.getId()));
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public synchronized List<PersonDto> getAll() {
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            List<PersonDto> personDtos = new ArrayList<>();

            ResultSet rs = stat.executeQuery("SELECT * FROM Persons");
            while (rs.next()) {
                PersonDto personDto = getPersonByResultSet(rs);
                personDtos.add(personDto);
            }
            return personDtos;
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    private PersonDto getPersonByResultSet(final ResultSet rs) throws SQLException {
        return new PersonDto(rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getTimestamp(8),
                rs.getString(9));
    }

    private Connection getConnection() throws SQLException, IOException {
        var props = new Properties();
        try (InputStream in = DatabaseConnImpl.class.getResourceAsStream("database.properties")) {
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}
