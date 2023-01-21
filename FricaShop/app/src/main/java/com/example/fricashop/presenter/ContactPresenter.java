package com.example.fricashop.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.face.ContactInterface;
import com.example.fricashop.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ContactPresenter {
    ContactInterface contactInterface;
    List<Contact> cList;

    public ContactPresenter(ContactInterface contactInterface) {
        this.contactInterface = contactInterface;
    }

    public void getContact(final ContactInterface contactInterface){
        RequestQueue queueContact = Volley.newRequestQueue(contactInterface.getContactContext());
        String urlContact = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectContact.php";
        JsonArrayRequest arrayRequestContact = new JsonArrayRequest(Request.Method.GET, urlContact, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objectContact = response.getJSONObject(i);
                        Contact c = new Contact(objectContact.getInt("id"), objectContact.getString("name"),objectContact.getString("email"), objectContact.getString("phone"));
                        cList.add(c);
                        contactInterface.onSuccessContact(cList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contactInterface.getContactContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("err",error+"");
            }
        });
        queueContact.add(arrayRequestContact);
    }
}
