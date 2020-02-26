package com.example.tuanvatvo.baitap_appbanhang.activiti;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    EditText edtTenNguoiDung,edtEmailNguoiDung,edtSDTNguoiDung;
    Button btnXacNhan,btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        showDialog();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(ThongTinKhachHangActivity.this);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.customdialog);

          edtTenNguoiDung = dialog.findViewById(R.id.edtTenNguoiDung);
          edtEmailNguoiDung = dialog.findViewById(R.id.edtEmailNguoiDung);
          edtSDTNguoiDung = dialog.findViewById(R.id.edtSDTNguoiDung);
         btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
         btnTroVe = dialog.findViewById(R.id.btnTroVe);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edtTenNguoiDung.getText().toString().trim();
                final String email = edtEmailNguoiDung.getText().toString().trim();
                final String phone = edtSDTNguoiDung.getText().toString().trim();
                if(ten.isEmpty() || email.isEmpty()||phone.isEmpty()){
                    Toast.makeText(ThongTinKhachHangActivity.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    // trả về về web
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.urlDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            // nếu  tồn tại madonhang tức là tồn tại người mua
                            // nên ta sẽ đưa người mua về dạng JSON và gửi lên bảng
                            if(Integer.parseInt(madonhang) > 0 ){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Sever.urlChiTietDonHangMua,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                if(response.equals("success")){
                                                    MainActivity.arrGioHang.clear();
                                                    Toast.makeText(ThongTinKhachHangActivity.this, "Bạn thêm dữ liệu giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(intent);
                                                    Toast.makeText(ThongTinKhachHangActivity.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(ThongTinKhachHangActivity.this, "Dữ liệu giỏ hàng bị lỗi", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        // chuyển dữ liệu về 1 đờng link có dạng JSON Array
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i < MainActivity.arrGioHang.size() ; i ++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang); //  cái name phải trùng với  $VALUE ở trong file PHP
                                                jsonObject.put("masanpham",MainActivity.arrGioHang.get(i).getIdSP());
                                                jsonObject.put("tensanpham",MainActivity.arrGioHang.get(i).getTenSP());
                                                jsonObject.put("giasanpham",MainActivity.arrGioHang.get(i).getGiaSP());
                                                jsonObject.put("soluongsanpham",MainActivity.arrGioHang.get(i).getSoluongSP());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }

                                        HashMap<String,String> hashMap = new HashMap<>();
                                        // gửi jsonArray dạng chuỗi lên biến $json trong  file chitietdonhang.php
                                        hashMap.put("json",jsonArray.toString());

                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }



                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",phone);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }

            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                finish();
            }
        });
        dialog.show();
    }
}
