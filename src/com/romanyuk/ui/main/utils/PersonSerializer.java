package com.romanyuk.ui.main.utils;

import com.romanyuk.domain.entity.PersonWrapper;

import java.io.*;
import java.util.List;

/**
 * Сериализатор и десериализатор для Person
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class PersonSerializer {
    /**
     * Сериализация Person в File
     *
     * @param personWrapper - объект для сериализации
     * @param path          - путь
     */
    static public void serialize(PersonWrapper personWrapper, File path) {
        personWrapper.setPersons(List.copyOf(personWrapper.getPersons()));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(personWrapper);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Десериализация.
     *
     * @param path - путь
     * @return - десериализированный объект или null, если десериализация прошла неудачно.
     */
    static public PersonWrapper deserialize(File path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (PersonWrapper) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
