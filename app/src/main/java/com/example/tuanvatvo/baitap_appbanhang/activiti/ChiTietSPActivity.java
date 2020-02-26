package com.example.tuanvatvo.baitap_appbanhang.activiti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.model.GioiHanh;
import com.example.tuanvatvo.baitap_appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {
    ImageView imgHinhChiTietSP;
    TextView txtTenChiTietSP,txtGiaChiTietSP,txtMotaChiTietSP;
    Button btnThemGioHang;
    android.support.v7.widget.Toolbar toolBarChiTietSP;
    Spinner spinerChiTietSP;

    // Infor SP
    int id;
    String tenSP;
    int giaChiTietSP;
    String hinhSP;
    String motaSP;
    int idsanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        addControls();
        ActionToolBar();
        addEvents();
        // bắt sự kiện cho Spiner và tạo giới hạn cho nó từ 1 -> 10
        CatchEventSpiner();
        EventsButton();

    }

    private void EventsButton() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MainActivity.arrGioHang.size() > 0){
                    int soluong = Integer.parseInt(spinerChiTietSP.getSelectedItem().toString());
                    // nếu có dữ liệu rồi thì ta tìm xem san phẩm ta thêm vào có trùng với sản phẩm nào
                    // đã có trong giỏ hành không nếu có thì ta tăng số lượng lên 1

                    boolean exists = false; // kiểm tra xe tìm thấy hay không


                    for(int  i = 0 ; i < MainActivity.arrGioHang.size() ; i ++){
                        if (MainActivity.arrGioHang.get(i).getIdSP() == id){
                            int soluongcu = MainActivity.arrGioHang.get(i).getSoluongSP();
                            // ta cập nhật số lượng mới  = sluong cu + soluong  ( ở sniper )

                            MainActivity.arrGioHang.get(i).setSoluongSP(soluongcu+soluong);
                            int soluongmoi = MainActivity.arrGioHang.get(i).getSoluongSP();
                            // nếu lớn hơn 10 sản phẩm thì ta  chỉ cho ua 10 sản phẩm
                            if( soluongmoi > 10){
                                MainActivity.arrGioHang.get(i).setSoluongSP(10);
                            }
                            // cập nhật số lượng rồi bh ta cập nhật lại tổng tiền cần phải trả
                            MainActivity.arrGioHang.get(i).setGiaSP(giaChiTietSP*soluongmoi);
                            exists = true;
                        }
                    }

                    // nếu không tìm thấy
                    if(exists == false){
                        int soluongInSpiner = Integer.parseInt(spinerChiTietSP.getSelectedItem().toString());
                        long giamoi = soluongInSpiner*giaChiTietSP;
                        MainActivity.arrGioHang.add(new GioiHanh(id,tenSP,giamoi,hinhSP,soluongInSpiner));

                    }

                }
                    // nếu mảng rỗng thì ta add sản phẩm lại thôi
                    // còn nếu mảng có sản phẩm đấy rồi thì ta chỉ việc cập nhật lại giá và số lượng
                else {
                    int soluongInSpiner = Integer.parseInt(spinerChiTietSP.getSelectedItem().toString());
                    long giamoi = soluongInSpiner*giaChiTietSP;
                    MainActivity.arrGioHang.add(new GioiHanh(id,tenSP,giamoi,hinhSP,soluongInSpiner));

                }

                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }


    private void addControls() {
        imgHinhChiTietSP = findViewById(R.id.imgHinhChiTietSP);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);
        txtTenChiTietSP = findViewById(R.id.txtTenChiTietSP);
        txtGiaChiTietSP = findViewById(R.id.txtGiaChiTietSP);
        txtMotaChiTietSP = findViewById(R.id.txtMotaChiTietSP);
        toolBarChiTietSP = findViewById(R.id.toolBarChiTietSP);
        spinerChiTietSP = findViewById(R.id.spinerChiTietSP);

    }

    private void ActionToolBar() {
            setSupportActionBar(toolBarChiTietSP);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        // mũi tên trên toolBar đẻ quay trở lại trang chính
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolBarChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addEvents() {
        Intent intent = getIntent();
        SanPham sanPham = (SanPham) intent.getSerializableExtra("thongtinsanpham");

        tenSP = sanPham.getTensanpham();
        giaChiTietSP = sanPham.getGiasanpham();
        motaSP = sanPham.getMotasanpham();
        hinhSP = sanPham.getHinhanhsanpham();
        id = sanPham.getId();
        idsanpham = sanPham.getIdSanpham();

        //Toast.makeText(this, sanPham.getTensanpham()+"", Toast.LENGTH_SHORT).show();

        Picasso.with(getApplicationContext()).load(hinhSP).into(imgHinhChiTietSP);
        txtTenChiTietSP.setText(tenSP);
        txtMotaChiTietSP.setText(motaSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaChiTietSP.setText("Giá : " + decimalFormat.format(giaChiTietSP)+ " Đ");



    }

    private void CatchEventSpiner() {
        Integer [] arrsoluong = new Integer[] {1,2,3,4,5,6,7,8,9,10};

        // tạo bảng vẽ
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,arrsoluong);
        spinerChiTietSP.setAdapter(arrayAdapter);
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
