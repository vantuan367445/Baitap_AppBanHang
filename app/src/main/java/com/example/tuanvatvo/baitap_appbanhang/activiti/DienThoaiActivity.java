package com.example.tuanvatvo.baitap_appbanhang.activiti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.adapter.DienThoaiAdapter;
import com.example.tuanvatvo.baitap_appbanhang.model.SanPham;
import com.example.tuanvatvo.baitap_appbanhang.ultil.CheckConnection;
import com.example.tuanvatvo.baitap_appbanhang.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDienThoai;
    ListView lvDienThoai;
    DienThoaiAdapter adapter;
    ArrayList<SanPham> arrDienThoai;

    int idDt = 0; // nhận về từ beenActivityMain gửi đến
    int page = 1;

    View footerView;
    boolean isLoading = false; // chưa load dữ liệu

    // muốn dùng 1 class t phải khai báo trước
    mHandler mHandler;

    boolean limitData =false; // kiểm tra xme đã load hết dữ liệu hay chưa nếu load hết rồi k hiện  progressBar nữa



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        addControls();
        GetIdLoaiSp();
        ActionToolBar();
        GetData(page);
        LoadmoreData();
    }

    private void addControls() {
        toolbarDienThoai = findViewById(R.id.toolBarDienThoai);
        lvDienThoai = findViewById(R.id.lvDienThoai);
        arrDienThoai = new ArrayList<>();
        adapter = new DienThoaiAdapter(DienThoaiActivity.this,R.layout.item_dien_thoai,arrDienThoai);
        lvDienThoai.setAdapter(adapter);

        // tạo progressBar để load dữ liệu
        @SuppressLint("ServiceCast")
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);

        mHandler = new mHandler();
    }

    private void GetIdLoaiSp() {
        idDt = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idDt + "");

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDienThoai);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        // mũi tên trên toolBar đẻ quay trở lại trang chính
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetData(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Sever.urlSapPham + String.valueOf(Page);
        // String.valueOf(Page); ép kiểu page về kiểu chuỗi để nối chuỗi

        // gửi page = 1  lên cho sever đẻ lấy id DT về
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // khi có dữ liệu rồi thì k cho hiện progressBar nữa
                        lvDienThoai.removeFooterView(footerView);
                        //dữ liệu trả về  ở  response
                        if(response != null && response.length() != 2){
                            // vì nó nằm trong JsonArray  nên  khai báo 1 JsonArray
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject object = null;
                                for(int  i =0 ; i < jsonArray.length() ; i ++){
                                    object = jsonArray.getJSONObject(i);
                                    SanPham sanPham = new SanPham(
                                            object.getInt("id"),
                                            object.getString("tensp"),
                                            object.getInt("giasp"),
                                            object.getString("hinhanhsp"),
                                            object.getString("motasp"),
                                            object.getInt("idsanpham"));

                                    arrDienThoai.add(sanPham);
                                }

                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            // nếu load hết dữ liệu rồi
                            limitData = true;
                            // khi không có dữ liệu nữa thì k cho hiện progressBar nữa
                            lvDienThoai.removeFooterView(footerView);
                            // Toast
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            // gửi dữ liệu lên cho sever dạng HaspMap
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idsanpham",String.valueOf(idDt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadmoreData() {
        //chi tiết sản phẩm
        lvDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham",arrDienThoai.get(i)); // chuển  1 object
                startActivity(intent);
            }
        });


        lvDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                // TotalItem tổng số Item có trong ListView
                // FirstItem dòng đầu tiên trong listVire
                // VisibleItem số lượng  Item có thể nhìn nhất

                //TotalItem != 0 để  ần dầu tiên chạy nó không chạy vào
                // FirstItem + VisibleItem == TotalItem  đang đứng ở vị trí cuối cùng
                //limitData == false  tức là chưa hết dữ liệu thì mới đi vào Load

                if(FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true; // đang load dữ liệu
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });
    }



    public  class mHandler extends Handler{


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  0:
                    lvDienThoai.addFooterView(footerView);
                    break;
                case  1:
                    page++;
                    GetData(page);
                    // đọc xong dữ liệu thì ta cho isLoading  về trạng thái chưa load dữ liệu
                    isLoading = false;
                    break;
            }
                super.handleMessage(msg);
        }
    }

    public  class ThreadData extends Thread{

        @Override
        public void run() {

            mHandler.sendEmptyMessage(0); // gửi tin nhắn 0 cho class mHandler rồi class mHandler add footerView
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //sau 3s gửi tiếp 1 tin nhắn
            Message message = mHandler.obtainMessage(1); // gửi 1 lên cho class mHandler để class mHandler lấy dữ liệu
            mHandler.sendMessage(message);
            super.run();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuGioHang :
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

}
