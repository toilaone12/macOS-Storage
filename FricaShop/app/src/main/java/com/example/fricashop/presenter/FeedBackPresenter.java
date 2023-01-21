package com.example.fricashop.presenter;

import android.graphics.Bitmap;
import android.util.Base64;
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
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedBackPresenter {
    FeedBackInterface fbInterface;
    List<User> usList;
    List<Status> sList;
    List<Comment> cList;

    public FeedBackPresenter(FeedBackInterface fbInterface) {
        this.fbInterface = fbInterface;
    }

    public void getAllFeedBack(final FeedBackInterface feedBackInterface){
        //Volley là thư viện dùng để gửi và nhận từ server thông qua HTTP
        RequestQueue queueFB = Volley.newRequestQueue(fbInterface.getContextFeedBack()); // hàng đợi giữ các yêu cầu, là nơi tất cả các y/c đc đợi để thực hiện
        String urlFB = "http://"+ UserPresenter.IP_NETWORK+"/fricashop/webservice/selectFB.php";
        JsonArrayRequest arrayRequestFB = new JsonArrayRequest(Request.Method.GET, urlFB, null, new Response.Listener<JSONArray>() { // truy xuất nội dung dưới dạng mảng JSON từ máy chủ (server)
            @Override
            public void onResponse(JSONArray response) {
                sList = new ArrayList<>();
                usList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject objectFB = response.getJSONObject(i);
                        Status s = new Status(objectFB.getInt("id"), objectFB.getString("ten_khach_hang"), objectFB.getString("bai_viet"), objectFB.getString("anh_bai_viet"), objectFB.getInt("so_luot_thich"),objectFB.getInt("so_luot_binh_luan"));
                        User u = new User(objectFB.getInt("id_kh"),objectFB.getString("ten_khach_hang"), objectFB.getString("anh_khach_hang"));
                        sList.add(s);
                        usList.add(u);
                        feedBackInterface.onSuccessFeedBack(sList,usList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(),"Error "+error, Toast.LENGTH_SHORT).show();
                Log.d("Error",error+"");
            }
        });
        queueFB.add(arrayRequestFB);
    }

    public String getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // là lớp con của OutputStream, dùng để ghi các bytes vào 1 mảng byte
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); // nén dữ liệu ảnh
        byte[] anh = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(anh,Base64.DEFAULT); // mã hóa ảnh đã cho và trả về 1 chuỗi
        return encodeImage;
    }

    public void postStatus(Bitmap bm, int id, String title){
        String urlPost = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/postStatus.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlPost, new Response.Listener<String>() { // truy xuất nội dung dưới dạng mảng chuỗi từ máy chủ (server)
            @Override
            public void onResponse(String response) {
//                Log.d("AAA",response);
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("OK")){
                        fbInterface.returnFeedBack();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>(); // lớp tạo ra lưu trữ dữ liệu tập hợp dưới dạng key-value
                params.put("image",getFileDataFromDrawable(bm));
                params.put("ten_nguoi_dang",id+"");
                params.put("bai_viet",title);
//                System.out.println("Map: "+params);
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(fbInterface.getContextFeedBack()).add(request);
    }
    public void likeStatus(int countLike,String titlePost){
        String urlLikeStatus = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/likeStatus.php";
        StringRequest requestLikeStatus = new StringRequest(Request.Method.POST, urlLikeStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("title",titlePost);
                params.put("countLike",countLike+"");
                return params;
            }
        };
        Volley.newRequestQueue(fbInterface.getContextFeedBack()).add(requestLikeStatus);
    }

    public void getDetailBlog(final FeedBackInterface fbInterface,int id){
        String urlBlog = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/selectComment.php";
        RequestQueue queue = Volley.newRequestQueue(fbInterface.getContextFeedBack());
        StringRequest request = new StringRequest(Request.Method.POST, urlBlog, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cList = new ArrayList<>();
                Log.d("AAA",response+"");
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject objectFB = array.getJSONObject(i);
                            Comment c = new Comment(objectFB.getInt("id"), objectFB.getInt("blog_id"), objectFB.getString("img_blog"),objectFB.getString("name_blog"), objectFB.getString("detail_blog"), objectFB.getString("date_blog"));
                            cList.add(c);
                            fbInterface.onSuccessBlog(cList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                usList = new ArrayList<>();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
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
    public void addComment(int id, String image_blog, String name_blog, String desc_blog){
        RequestQueue queue = Volley.newRequestQueue(fbInterface.getContextFeedBack());
        String urlAddComment = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/insertComment.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlAddComment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AAAAA",response);
                if(response.equals("2")){
                    Toast.makeText(fbInterface.getContextFeedBack(), "Comment của bạn có vấn đề, yêu cầu kiểm tra lại!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                map.put("image_blog",image_blog);
                map.put("name_blog",name_blog);
                map.put("desc_blog",desc_blog);
                return map;
            }
        };
        queue.add(request);
    }

    public void deleteComment(int id, int blog_id) {
        RequestQueue queue = Volley.newRequestQueue(fbInterface.getContextFeedBack());
        String urlDelete = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/deleteComment.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("2")){
                    Toast.makeText(fbInterface.getContextFeedBack(), "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id+"");
                map.put("blog_id",blog_id+"");
                return map;
            }
        };
        queue.add(request);
    }

    public void updateComment(int id, String descUpdate) {
        RequestQueue queue = Volley.newRequestQueue(fbInterface.getContextFeedBack());
        String urlUpdate = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/updateComment.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("AAAAA",response+"");
                if(response.equals("1")){
                    fbInterface.returnFeedBack();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>(); // giao dien Map xac dinh 1 doi tuong anh xa den cac gia tri, HashMap la mang luu tru duoi dang key-value
                map.put("id",id+"");
                map.put("desc_comment",descUpdate);
                return map;
            }
        };
        queue.add(request);
    }

    public void deletePost(int id) {
        RequestQueue queue = Volley.newRequestQueue(fbInterface.getContextFeedBack());
        String urlUpdate = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/deletePost.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("AAAAA",response+"");
                if(response.equals("1")){
                    fbInterface.returnFeedBack();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>(); // giao dien Map xac dinh 1 doi tuong anh xa den cac gia tri, HashMap la mang luu tru duoi dang key-value
                map.put("id",id+"");
                return map;
            }
        };
        queue.add(request);
    }

    public void updatePost(int id, Bitmap bm, String title_blog) {
        RequestQueue queue = Volley.newRequestQueue(fbInterface.getContextFeedBack());
        String urlUpdate = "http://"+UserPresenter.IP_NETWORK+"/fricashop/webservice/updatePost.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
//                    Log.d("AAAAA",response+"");
                    if(object.getString("status").equals("OK")){
                        fbInterface.returnFeedBack();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fbInterface.getContextFeedBack(), "Error"+error, Toast.LENGTH_SHORT).show();
                Log.d("error",error+"");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>(); // giao dien Map xac dinh 1 doi tuong anh xa den cac gia tri, HashMap la mang luu tru duoi dang key-value
                map.put("id_blog",id+"");
                map.put("image_blog",getFileDataFromDrawable(bm)+"");
                map.put("title_blog",title_blog+"");
                return map;
            }
        };
        queue.add(request);
    }
}
