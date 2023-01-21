package com.example.fricashop.model;

import java.io.Serializable;

public class Contact implements Serializable {
    int idContact;
    String nameContact;
    String emailContact;
    String phoneContact;

    public Contact(int idContact, String nameContact, String emailContact, String phoneContact) {
        this.idContact = idContact;
        this.nameContact = nameContact;
        this.emailContact = emailContact;
        this.phoneContact = phoneContact;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "idContact=" + idContact +
                ", nameContact='" + nameContact + '\'' +
                ", emailContact='" + emailContact + '\'' +
                ", phoneContact='" + phoneContact + '\'' +
                '}';
    }
}
