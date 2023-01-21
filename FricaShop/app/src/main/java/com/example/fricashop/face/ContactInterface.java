package com.example.fricashop.face;

import android.content.Context;

import com.example.fricashop.model.Contact;

import java.util.List;

public interface ContactInterface {
    Context getContactContext();
    void onSuccessContact(List<Contact> contactList);
}
