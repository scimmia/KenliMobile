package com.jiayusoft.mobile.kenli.tongxunlu;

/**
 * Created by Hi on 2016-1-17.
 */
public class Tongxunlu {
    String office;
    String name;
    String phoneNumber;

    public Tongxunlu(String office, String name, String phoneNumber) {
        this.office = office;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getOffice() {
        return office;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Tongxunlu{" +
                "office='" + office + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
