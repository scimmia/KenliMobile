package com.jiayusoft.mobile.kenli.tongxunlu;

/**
 * Created by Hi on 2016-1-17.
 */
public class Tongxunlu {
    String jiedao;
    String juweihui;
    String name;
    String phoneNumber;
    String phoneNumberB;

    public Tongxunlu() {
    }

    public Tongxunlu(String jiedao, String name, String phoneNumber) {
        this.jiedao = jiedao;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getJiedao() {
        return jiedao;
    }

    public void setJiedao(String jiedao) {
        this.jiedao = jiedao;
    }

    public String getJuweihui() {
        return juweihui;
    }

    public void setJuweihui(String juweihui) {
        this.juweihui = juweihui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberB() {
        return phoneNumberB;
    }

    public void setPhoneNumberB(String phoneNumberB) {
        this.phoneNumberB = phoneNumberB;
    }

    @Override
    public String toString() {
        return "Tongxunlu{" +
                "jiedao='" + jiedao + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
