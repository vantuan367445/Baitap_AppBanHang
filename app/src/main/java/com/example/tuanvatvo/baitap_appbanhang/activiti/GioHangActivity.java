package com.example.tuanvatvo.baitap_appbanhang.activiti;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.adapter.GioHangAdapter;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolBarGioHang;
    public  static  TextView txtThongBaoGioHang;
    public  static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    ListView lvGioHang;
    public static  GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);


        addControls();
        ActionToolBar();
        checkData();
        addEvents();
        EventUltil();   // lấy tổng tiền để cho textView hiển thị
        CatchOnItemListView();


    }

    private void CatchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa sản phẩm !");
                builder.setMessage("Bạn có muốn xóa không ?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.arrGioHang.size() <= 0){
                            txtThongBaoGioHang.setVisibility(View.VISIBLE);
                            lvGioHang.setVisibility(View.INVISIBLE);
                            txtTongTien.setVisibility(View.INVISIBLE);

                        }
                        else{
                            txtThongBaoGioHang.setVisibility(View.INVISIBLE);
                            lvGioHang.setVisibility(View.VISIBLE);
//                            Toast.makeText(GioHangActivity.this, "Đã xóa" + MainActivity.arrGioHang.get(position).getTenSP(),
////                                    Toast.LENGTH_SHORT).show();
                            MainActivity.arrGioHang.remove(position);
//
                            gioHangAdapter.notifyDataSetChanged();
                            // cập nhật lại tổng tiền
                            EventUltil();
                            // nếu bạn xóa hết
                            if(MainActivity.arrGioHang.size() <= 0){
                                txtThongBaoGioHang.setVisibility(View.VISIBLE);
                                lvGioHang.setVisibility(View.INVISIBLE);
                                txtTongTien.setVisibility(View.INVISIBLE);
                            }
                            else {
                                txtThongBaoGioHang.setVisibility(View.INVISIBLE);
                                lvGioHang.setVisibility(View.VISIBLE);
                                EventUltil();
                                gioHangAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien = 0;
        for (int  i =0 ; i < MainActivity.arrGioHang.size(); i ++){
            tongtien += MainActivity.arrGioHang.get(i).getGiaSP();
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtTongTien.setText(decimalFormat.format(tongtien)+ "Đ");
        }

    }

    private void checkData() {
        if(MainActivity.arrGioHang.size() <= 0 ){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBaoGioHang.setVisibility(View.VISIBLE); // nếu không có dữ liệu thì cho nó hiện lên là không có dữ liệu
            lvGioHang.setVisibility(View.INVISIBLE);// cho lv ẩn đi
        }
        else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBaoGioHang.setVisibility(View.INVISIBLE); // nếu  có dữ liệu thì cho nó ẩn
            lvGioHang.setVisibility(View.VISIBLE);// cho lv hiện ra
        }
    }

    private void addEvents() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arrGioHang.size() <= 0){
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(GioHangActivity.this,ThongTinKhachHangActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

    private void addControls() {
        txtTongTien = findViewById(R.id.txtTongTien);
        txtThongBaoGioHang = findViewById(R.id.txtThongBaoGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMua);
        toolBarGioHang = findViewById(R.id.toolBarGioHang);
        lvGioHang   = findViewById(R.id.lvGioHang);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this,R.layout.item_dong_gio_hang,MainActivity.arrGioHang);
        lvGioHang.setAdapter(gioHangAdapter);
    }

    @SuppressLint("RestrictedApi")
    private void ActionToolBar() {
        setSupportActionBar(toolBarGioHang);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        // mũi tên trên toolBar đẻ quay trở lại trang chính
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
