package com.example.tuanvatvo.baitap_appbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DienThoaiAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    int resource;
    @NonNull ArrayList<SanPham> arr;
    public DienThoaiAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<SanPham> arr) {
        super(context, resource, arr);
        this.context = context;
        this.resource = resource;
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        ImageView imgHinhDT = view.findViewById(R.id.imgHinhDT);
        TextView txtTenDT = view.findViewById(R.id.txtTenDT);
        TextView txtGiaDT =view.findViewById(R.id.txtGiaDT);
        TextView txtMotaDT = view.findViewById(R.id.txtMoTaDT);


        SanPham sanPham = arr.get(position);

        txtTenDT.setText(sanPham.getTensanpham().trim());
        // set text cho mo ta
        txtMotaDT.setMaxLines(2);   // chỉ cho hiện 2 dòng
        txtMotaDT.setEllipsize(TextUtils.TruncateAt.END);
        txtMotaDT.setText(sanPham.getMotasanpham().trim());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaDT.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham())+ " Đ");
        //holder.txtGiasanpham.setText(sanPham.getGiasanpham()+"");
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .into(imgHinhDT);





        return view;
    }
}
