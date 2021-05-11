package com.romanyuk.data.db.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class PersonDto {
    private final String firstName;
    /**
     * Фамилия
     */
    private final String secondName;
    /**
     * Отчество
     */
    private final String thirdName;
    /**
     * Мобильный телефон
     */
    private final String mobilePhone;
    /**
     * Домашний телефон.
     */
    private final String homePhone;
    /**
     * Адрес.
     */
    private final String address;
    /**
     * День рождение.
     */
    private final Timestamp birthDay;
    /**
     * Комментарий.
     */
    private final String comment;
    private int id;

    public PersonDto(int id, String firstName, String secondName, String thirdName, String mobilePhone,
                     String homePhone, String address, Timestamp birthDay, String comment) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        if (firstName == null)
            return "";
        return firstName;
    }

    public String getSecondName() {
        if (secondName == null)
            return "";
        return secondName;
    }

    public String getThirdName() {
        if (thirdName == null)
            return "";
        return thirdName;
    }

    public String getMobilePhone() {
        if (mobilePhone == null)
            return "";
        return mobilePhone;
    }

    public String getHomePhone() {
        if (homePhone == null)
            return "";
        return homePhone;
    }

    public String getAddress() {
        if (address == null)
            return "";
        return address;
    }

    public Timestamp getBirthDay() {
        if (birthDay == null)
            return Timestamp.from(Instant.MIN);
        return birthDay;
    }

    public String getComment() {
        if (comment == null)
            return "";
        return comment;
    }

}
