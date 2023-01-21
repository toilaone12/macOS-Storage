package com.example.fricashop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fricashop.R;
import com.example.fricashop.face.ContactInterface;
import com.example.fricashop.model.Contact;
import com.example.fricashop.presenter.ContactPresenter;

import java.util.ArrayList;
import java.util.List;

public class ContactInformation extends AppCompatActivity implements ContactInterface {
    TextView txtNameContact,txtPhoneContact,txtEmailContact;
    Toolbar tbContact;
    List<Contact> cList;
    ContactPresenter contactPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_infomation);
        mapping();
        getContact();
        addActionBar();
    }

    private void addActionBar() {
        setSupportActionBar(tbContact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tbContact.setNavigationIcon(R.drawable.arrow_back);
        tbContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Liên hệ");
    }

    private void getContact() {
        contactPresenter = new ContactPresenter(this);
        contactPresenter.getContact(new ContactInterface() {
            @Override
            public Context getContactContext() {
                return ContactInformation.this;
            }

            @Override
            public void onSuccessContact(List<Contact> contactList) {
                cList = new ArrayList<>();
                for (Contact c : contactList) {
                    cList.add(c);
                }
                for (int i = 0; i < cList.size(); i++) {
                    txtNameContact.setText("Họ và tên: "+cList.get(i).getNameContact());
                    txtEmailContact.setText("Email: "+cList.get(i).getEmailContact());
                    txtPhoneContact.setText("Số điện thoại: "+cList.get(i).getPhoneContact());
                }
            }
        });

    }

    private void mapping() {
        txtNameContact = findViewById(R.id.txtNameContact);
        txtEmailContact = findViewById(R.id.txtEmailContact);
        txtPhoneContact = findViewById(R.id.txtPhoneContact);
        tbContact = findViewById(R.id.tbContact);
    }

    @Override
    public Context getContactContext() {
        return this;
    }

    @Override
    public void onSuccessContact(List<Contact> contactList) {

    }
}