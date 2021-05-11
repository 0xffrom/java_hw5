package com.romanyuk.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс для описания сущности в виде контакта.
 * <p>
 * Методы equals и hashcode переопределены только под ФИО Person.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class Person implements Serializable {
    private int id;
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String secondName;
    /**
     * Отчество
     */
    private String thirdName;
    /**
     * Мобильный телефон
     */
    private String mobilePhone;
    /**
     * Домашний телефон.
     */
    private String homePhone;
    /**
     * Адрес.
     */
    private String address;
    /**
     * День рождение.
     */
    private LocalDate birthDay;
    /**
     * Комментарий.
     */
    private String comment;

    public Person() {
        id = 0;
    }

    public Person(int id, final String firstName, final String secondName, final String thirdName, final String mobilePhone,
                  final String homePhone, final String address, final LocalDate birthDay, final String comment) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.birthDay = birthDay;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Person setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public Person setHomePhone(final String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    /**
     * Получить полный номер телефона на основе мобильного и домашнего номеров.
     */
    public String getPhone() {
        String phone = "";

        if (mobilePhone != null && !mobilePhone.isBlank()) {
            phone += mobilePhone;
        }
        if (homePhone != null && !homePhone.isBlank()) {
            if (!phone.isBlank()) {
                phone += "/" + homePhone;
            } else {
                phone += homePhone;
            }
        }

        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Person setAddress(final String address) {
        this.address = address;
        return this;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Person setBirthDay(final LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Person setComment(final String comment) {
        this.comment = comment;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getSecondName() {
        return secondName;
    }

    public Person setSecondName(final String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getThirdName() {
        return thirdName;
    }

    public Person setThirdName(final String thirdName) {
        this.thirdName = thirdName;
        return this;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(secondName, person.secondName)
                && Objects.equals(thirdName, person.thirdName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, thirdName);
    }
}
