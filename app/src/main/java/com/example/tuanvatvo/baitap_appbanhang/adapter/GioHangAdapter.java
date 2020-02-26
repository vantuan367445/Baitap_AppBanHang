package com.example.tuanvatvo.baitap_appbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.activiti.GioHangActivity;
import com.example.tuanvatvo.baitap_appbanhang.activiti.MainActivity;
import com.example.tuanvatvo.baitap_appbanhang.model.GioiHanh;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GioHangAdapter extends ArrayAdapter<GioiHanh> {
    Activity context;
    int resource;
    ArrayList<GioiHanh> arr;
    public GioHangAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<GioiHanh> arr) {
        super(context, resource, arr);
        this.arr = arr;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        final TextView txtTenGioHang,txtGiaGioHang;
        final Button btnMinus,btnValues,btnPlus;
        ImageView imgGioHang;

        txtTenGioHang = view.findViewById(R.id.txtTenGioHang);
        txtGiaGioHang = view.findViewById(R.id.txtGiaGioHang);
        btnMinus = view.findViewById(R.id.btnMinus);
        btnValues = view.findViewById(R.id.btnValues);
        btnPlus = view.findViewById(R.id.btnPlus);
        imgGioHang = view.findViewById(R.id.imgGioHang);

        GioiHanh gioiHanh = arr.get(position);

        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaGioHang.setText("Giá : " + decimalFormat.format(gioiHanh.getGiaSP())+ " Đ");
        txtTenGioHang.setText(gioiHanh.getTenSP());
        Picasso.with(context).load(gioiHanh.getHinhSP())
                .into(imgGioHang);
        btnValues.setText(gioiHanh.getSoluongSP()+"");


        // xử lý  button thêm  và bớt
        final int sl = Integer.parseInt(btnValues.getText().toString());
        if(sl <= 1){
            btnMinus.setVisibility(View.INVISIBLE);
            btnPlus.setVisibility(View.VISIBLE);
        }
        else if( sl >= 10){
            btnMinus.setVisibility(View.VISIBLE);
            btnPlus.setVisibility(View.INVISIBLE);
        }
        else if( sl >= 1){
            btnMinus.setVisibility(View.VISIBLE);
            btnPlus.setVisibility(View.VISIBLE);
        }
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(btnValues.getText().toString()) - 1;
                int slht = MainActivity.arrGioHang.get(position).getSoluongSP();
                long giaht = MainActivity.arrGioHang.get(position).getGiaSP();
                long giamoinhat = slmoinhat*giaht/slht;
                MainActivity.arrGioHang.get(position).setSoluongSP(slmoinhat);
                MainActivity.arrGioHang.get(position).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtGiaGioHang.setText("Giá : " + decimalFormat.format(giamoinhat ) + " Đ");
                btnValues.setText(slmoinhat+"");

                GioHangActivity.EventUltil();

                if(slmoinhat <= 1){
                    btnMinus.setVisibility(View.INVISIBLE);
                    btnPlus.setVisibility(View.VISIBLE);
                }
                else if( slmoinhat >= 10){
                    btnMinus.setVisibility(View.VISIBLE);
                    btnPlus.setVisibility(View.INVISIBLE);
                }
                else if( slmoinhat >= 1){
                    btnMinus.setVisibility(View.VISIBLE);
                    btnPlus.setVisibility(View.VISIBLE);
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(btnValues.getText().toString()) + 1;
                int slht = MainActivity.arrGioHang.get(position).getSoluongSP();
                long giaht = MainActivity.arrGioHang.get(position).getGiaSP();
                MainActivity.arrGioHang.get(position).setSoluongSP(slmoinhat);
                long giamoinhat = (slmoinhat*giaht)/slht;
                MainActivity.arrGioHang.get(position).setGiaSP(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtGiaGioHang.setText("Giá : " + decimalFormat.format(giamoinhat ) + " Đ");
                btnValues.setText(slmoinhat+"");

                GioHangActivity.EventUltil();

                if(slmoinhat <= 1){
                    btnMinus.setVisibility(View.INVISIBLE);
                    btnPlus.setVisibility(View.VISIBLE);
                }
                else if( slmoinhat >= 10){
                    btnMinus.setVisibility(View.VISIBLE);
                    btnPlus.setVisibility(View.INVISIBLE);
                }
                else if( slmoinhat >= 1){
                    btnMinus.setVisibility(View.VISIBLE);
                    btnPlus.setVisibility(View.VISIBLE);
                }
            }
        });


        return view;
    }
}
