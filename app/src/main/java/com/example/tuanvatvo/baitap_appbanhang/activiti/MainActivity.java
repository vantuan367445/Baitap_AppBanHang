package com.example.tuanvatvo.baitap_appbanhang.activiti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.adapter.LoaiSPAdapter;
import com.example.tuanvatvo.baitap_appbanhang.adapter.SanPhamAdapter;
import com.example.tuanvatvo.baitap_appbanhang.model.GioiHanh;
import com.example.tuanvatvo.baitap_appbanhang.model.Loaisp;
import com.example.tuanvatvo.baitap_appbanhang.model.SanPham;
import com.example.tuanvatvo.baitap_appbanhang.ultil.CheckConnection;
import com.example.tuanvatvo.baitap_appbanhang.ultil.Sever;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarManHInhChinh; //  để hiện menu người dùng
    ViewFlipper viewFlipperManHinhChinh; ////chạy quảng cáo
    RecyclerView recyclerViewManHinhChinh;  // hiện thông tin Sản phẩm mới
    NavigationView navigationViewManHinhChinh; // đẻ chứa listView Điện thọi , Laptop, Phu kien,..
    ListView lvManHinhChinh;      // nằm trong navigationViewManHinhChinh
    DrawerLayout drawerlayoutMHC;

    ArrayList<Loaisp> arayLoaiSP;
    LoaiSPAdapter adapterLoaiSP;

    ArrayList<SanPham> araySanPhamMoi;
    SanPhamAdapter adapterSPMoi;

    // mảng giỏ hàng : ta để ở hàm Main để khi chuyển các màn hình khác nó không bị mất dữ liệu
    public static ArrayList<GioiHanh> arrGioHang;


    ImageView img_Left,img_Right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        if(CheckConnection.isConnected(getApplicationContext())){
            addEvents();
            Getdulieuloaisanpham();
            GetdulieuSanphamMoinhat();
            BatsukienchoListViewManHinhChinh();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
            finish();
        }



    }

    private void BatsukienchoListViewManHinhChinh() {
        lvManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        // đóng listView
                        drawerlayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    case 1:{
                        Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                        intent.putExtra("idloaisanpham",1);
                        startActivity(intent);
                        drawerlayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 2:{
                        Intent intent   = new Intent(MainActivity.this,LapTopActivity.class);
                        intent.putExtra("idloaisanpham",2);
                        startActivity(intent);
                        drawerlayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 3:{
                        Intent intent   = new Intent(MainActivity.this,LienHeActivity.class);
                        startActivity(intent);
                        drawerlayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    }


                }
            }
        });
    }

    private void GetdulieuSanphamMoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Sever.urlSapPhamMoinhat, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject object =null;

                        for(int  i = 0 ; i < response.length() ; i ++){
                            try {
                                object = response.getJSONObject(i);
                                SanPham sp = new SanPham(
                                        object.getInt("id"),object.getString("tensp"),
                                        object.getInt("giasp"),object.getString("hinhanhsp"),
                                        object.getString("motasp"),object.getInt("idsanpham"));
                                araySanPhamMoi.add(sp);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterSPMoi.notifyDataSetChanged();
                    }
                    
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Loi", error.toString());
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisanpham() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Sever.urlLoaiSanPham, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject object = null;
                        for(int  i =0 ; i < response.length() ; i ++){
                            try {
                                object = response.getJSONObject(i);


                                arayLoaiSP.add(new Loaisp(object.getInt("id"),
                                                          object.getString("tenloaisp"),
                                                          object.getString("hinhanhloaisp")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //http://quynhanhmobile.com/uploads/source/menu/main_menu_icon_5.png
                        //http://upload2.webbnc.vn/uploadv2/web/28/2812/media/2016/04/13/02/38/1460515396_1195445181899094722molumen_phone_icon.svg.hi.png
                        arayLoaiSP.add(3,new Loaisp(0,"Phụ kiện","https://icon-icons.com/icons2/1129/PNG/512/headphonestoolforears_80000.png"));
                        arayLoaiSP.add(4,new Loaisp(0,"Khuyến mãi","http://icons.iconarchive.com/icons/icons8/ios7/256/Ecommerce-Sale-icon.png"));
                        arayLoaiSP.add(5,new Loaisp(0,"Hỏi đáp","https://cdn.thegioididong.com/dmxchat/iconinvitechat_1.v201705000045.png"));
                        arayLoaiSP.add(6,new Loaisp(0,"Liên hệ","https://uphinhnhanh.com/images/2018/10/23/main_menu_icon_5.png"));
                        arayLoaiSP.add(7,new Loaisp(0,"Cài đặt","https://cdn2.iconfinder.com/data/icons/outline-signs/350/gear-512.png"));

                        adapterLoaiSP.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    private void addControls() {
        toolbarManHInhChinh = findViewById(R.id.toolBar);
        viewFlipperManHinhChinh = findViewById(R.id.viewFlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerView);
        navigationViewManHinhChinh = findViewById(R.id.navigationView);
        lvManHinhChinh = findViewById(R.id.lvManHinhChinh);
        drawerlayoutMHC = findViewById(R.id.drawerlayoutMHC);

        arayLoaiSP = new ArrayList<>();
        // https://sieuthihue.vn/wp-content/uploads/2018/10/homes.png
        //https://goo.gl/HyTdn4
        arayLoaiSP.add(0,new Loaisp(0,"Trang chủ","https://goo.gl/X8k9Pi"));
        adapterLoaiSP = new LoaiSPAdapter(MainActivity.this,R.layout.item_loaisp,arayLoaiSP);
        lvManHinhChinh.setAdapter(adapterLoaiSP);

        araySanPhamMoi = new ArrayList<>();
        adapterSPMoi = new SanPhamAdapter(MainActivity.this,araySanPhamMoi);
        recyclerViewManHinhChinh.setHasFixedSize(true); // tăng tốc dộ xử lý
        //*LinearLayoutManager * hiển thị Item theo chiều ngang hay chiều dọc như Scroll list.
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        //*GridLayoutManager * hiển thị Item theo kiểu Grid
        //*StaggeredGridLayoutManager * hiển thị Item theo tùy kích thước của Item trong Grid.
        recyclerViewManHinhChinh.setAdapter(adapterSPMoi);

        // nếu mảng chưa có dữ liệu ta mới tạo mới nếu nó có
        // dữ liệu rồi àm ta tạo mới thì sẽ mất dữ liệu
        if(arrGioHang == null){
            arrGioHang = new ArrayList<>();
        }

        img_Left = findViewById(R.id.img_Left);
        img_Right = findViewById(R.id.img_Right);
    }
    private void addEvents() {
        //actionBar để hiện menu người dùng
        ActionBar();
        //chạy quảng cáo
        ActionViewFlipper();

        img_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipperManHinhChinh.showNext();
            }
        });
        img_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipperManHinhChinh.showPrevious();
            }
        });

    }



    @SuppressLint("RestrictedApi")
    //actionBar để hiện menu người dùng
    private void ActionBar() {
        setSupportActionBar(toolbarManHInhChinh);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarManHInhChinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // bắt sự kiện để mở drawerlayoutMHC
        toolbarManHInhChinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayoutMHC.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {

        // đóng listView
        if(drawerlayoutMHC.isDrawerOpen(GravityCompat.START)){
            drawerlayoutMHC.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    //chạy quảng cáo
    private void ActionViewFlipper() {
        ArrayList<String> arrayListQuangCao = new ArrayList<>();
        arrayListQuangCao.add("https://goo.gl/odnRK6");
        arrayListQuangCao.add("https://goo.gl/VzZ8YZ");
        arrayListQuangCao.add("https://goo.gl/N9h6Ep");
        arrayListQuangCao.add("https://goo.gl/Q1yPbV");

        for (int  i = 0 ; i < arrayListQuangCao.size(); i ++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayListQuangCao.get(i)).into(imageView);
            // căn vừa Imager với ViewFlipper
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperManHinhChinh.addView(imageView);
        }
        //cho ViewFlipper tự chạy
        viewFlipperManHinhChinh.setFlipInterval(10000); // chạy trong 5s
        //viewFlipperManHinhChinh.setAutoStart(true); // tự động chạy

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipperManHinhChinh.setInAnimation(animation_slide_in);
        viewFlipperManHinhChinh.setOutAnimation(animation_slide_out);
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

