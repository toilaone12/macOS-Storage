package com.example.fricashop.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.face.CateInterface;
import com.example.fricashop.face.VolleyCallBack;
import com.example.fricashop.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CatePresenter {
    CateInterface cateInterface;
    List<Category> list;
    Category category;

    public CatePresenter(CateInterface cateInterface) {
        this.cateInterface = cateInterface;
    }

    public void getAllCategory(final VolleyCallBack volleyCallBack){
        RequestQueue queueCate = Volley.newRequestQueue(cateInterface.getContextCate());
        String urlCate = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectCate.php";
        JsonArrayRequest requestCate = new JsonArrayRequest(Request.Method.GET, urlCate, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    list = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try{
                                JSONObject objectCate = response.getJSONObject(i);
                                category = new Category(objectCate.getInt("id_cate"), objectCate.getString("name"), objectCate.getString("image"));
                                list.add(category);
    //                            Log.d("ddd",list+"");
                                volleyCallBack.takeAll(list);
                            }catch (JSONException e) {
                                    e.printStackTrace();
                            }
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cateInterface.getContextCate(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queueCate.add(requestCate);
    }
}
