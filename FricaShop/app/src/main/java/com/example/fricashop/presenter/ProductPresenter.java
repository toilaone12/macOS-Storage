package com.example.fricashop.presenter;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fricashop.HomePage;
import com.example.fricashop.face.ComputerCallBack;
import com.example.fricashop.face.DetailCallBack;
import com.example.fricashop.face.LoginInterface;
import com.example.fricashop.face.PhoneCallBack;
import com.example.fricashop.face.ProductInterface;
import com.example.fricashop.face.VolleyCallBack;
import com.example.fricashop.model.Product;
import com.example.fricashop.model.Slider;
import com.example.fricashop.view.DetailProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPresenter {
    List<Product> productList;
    Product p;
//    VolleyCallBack volleyCallBack;
    LoginInterface loginInterface;
    ProductInterface productInterface;

    public ProductPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public ProductPresenter(ProductInterface productInterface) {
        this.productInterface = productInterface;
    }

    public void getDetailProduct(final DetailCallBack detailCallBack, int id){
        RequestQueue queueDetailProduct = Volley.newRequestQueue(loginInterface.getContext());
        String urlDetailProduct = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectDetailProduct.php";
        StringRequest requestDetailProduct = new StringRequest(Request.Method.POST, urlDetailProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Product> detailList = new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        p = new Product(object.getInt("id_pr"),object.getInt("id_cate"),object.getString("name"), object.getInt("quantity"), object.getInt("price"), object.getString("image"),object.getString("link_ytb"),object.getString("desc"));
                        detailList.add(p);
                        detailCallBack.takeDetailProduct(detailList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                Log.d("er",""+error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                return map;
            }
        };
        queueDetailProduct.add(requestDetailProduct);
    }
    public void getAllProduct(final VolleyCallBack volleyCallBack){
        RequestQueue queue = Volley.newRequestQueue(loginInterface.getContext());
        String urlGetPerson = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectProduct.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlGetPerson, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Product> productList1 = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        p = new Product(object.getInt("id_pr"),object.getInt("id_cate"),object.getString("name"), object.getInt("quantity"), object.getInt("price"), object.getString("image"), object.getString("link_ytb"),object.getString("desc"));
                        productList1.add(p);
                        volleyCallBack.takeSuccessProduct(productList1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queue.add(request);
    }

    public void getPhoneForHomePage(final PhoneCallBack phoneCallBack){
        RequestQueue queue = Volley.newRequestQueue(loginInterface.getContext());
        String urlGetPerson = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectAverageCourses.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlGetPerson, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                productList = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        p = new Product(object.getInt("id_pr"),object.getInt("id_cate"),object.getString("name"), object.getInt("quantity"), object.getInt("price"), object.getString("image"),object.getString("link_ytb"), object.getString("desc"));
                        productList.add(p);
                        phoneCallBack.takeSuccessPhone(productList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queue.add(request);
    }

    public void getLowCourses(final PhoneCallBack phoneCallBack){
        RequestQueue queue = Volley.newRequestQueue(loginInterface.getContext());
        String urlGetPerson = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectLowCourses.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlGetPerson, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                productList = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        p = new Product(object.getInt("id_pr"),object.getInt("id_cate"),object.getString("name"), object.getInt("quantity"), object.getInt("price"), object.getString("image"),object.getString("link_ytb"), object.getString("desc"));
                        productList.add(p);
                        phoneCallBack.takeSuccessPhone(productList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queue.add(request);
    }

    public void getComputerProduct(final ComputerCallBack computerCallBack, int id){
        RequestQueue queue = Volley.newRequestQueue(productInterface.getContext());
        String urlComputer = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectProductById.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlComputer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Product> productList = new ArrayList<>();
                if(response != null){
                    try {
                        JSONArray arrayComputer = new JSONArray(response);
                        for (int i = 0; i < arrayComputer.length(); i++){
                            JSONObject objectComputer = arrayComputer.getJSONObject(i);
                            p = new Product(objectComputer.getInt("id_pr"),objectComputer.getInt("id_cate"),objectComputer.getString("name"), objectComputer.getInt("quantity"), objectComputer.getInt("price"), objectComputer.getString("image"),objectComputer.getString("link_ytb"), objectComputer.getString("desc"));
                            productList.add(p);
                            computerCallBack.onSuccessComputerProduct(productList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                return map;
            }
        };
        queue.add(request);
    }

    public void getSlider(VolleyCallBack v){
        RequestQueue queue = Volley.newRequestQueue(loginInterface.getContext());
        String urlSlider = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/getSlider.php";
        StringRequest request = new StringRequest(Request.Method.GET, urlSlider, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Slider> list = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new Slider(object.getInt("id"), object.getString("image"), object.getString("name")));
                        v.getSlider(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queue.add(request);
    }
    public void getPriceByRequest(int priceMin,int priceMax,String request,final ProductInterface productInterface){
        RequestQueue queue = Volley.newRequestQueue(productInterface.getContext());
        String url = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/getPriceByRequest.php";
        StringRequest requestPrice = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("AAAAA",response);
                try {
                    JSONArray array = new JSONArray(response);
                    productList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        p = new Product(object.getInt("id_pr"),object.getInt("id_cate"),object.getString("name"), object.getInt("quantity"), object.getInt("price"), object.getString("image"),object.getString("link_ytb"), object.getString("desc"));
                        productList.add(p);
                        productInterface.takeOnSuccess(productList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("min",priceMin+"");
                params.put("max",priceMax+"");
                params.put("request",request+"");

                return params;
            }
        };
        queue.add(requestPrice);
    }
    public void getRequest(int priceMin,int priceMax,int quantityMin, int quantityMax,final ProductInterface productInterface){
        RequestQueue queue = Volley.newRequestQueue(productInterface.getContext());
        String url = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/getRequest.php";
        StringRequest requestPrice = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("AAAAA",response);
                try {
                    JSONArray array = new JSONArray(response);
                    productList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        p = new Product(object.getInt("id_pr"),object.getInt("id_cate"),object.getString("name"), object.getInt("quantity"), object.getInt("price"), object.getString("image"),object.getString("link_ytb"), object.getString("desc"));
                        productList.add(p);
                        productInterface.takeOnSuccess(productList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productInterface.getContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("min",priceMin+"");
                params.put("max",priceMax+"");
                params.put("sl_min",quantityMin+"");
                params.put("sl_max",quantityMax+"");
//                params.put("request",request+"");

                return params;
            }
        };
        queue.add(requestPrice);
    }
}
