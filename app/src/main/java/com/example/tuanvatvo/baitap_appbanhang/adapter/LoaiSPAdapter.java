package com.example.tuanvatvo.baitap_appbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LoaiSPAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    @NonNull ArrayList<Loaisp> arr;
    public LoaiSPAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Loaisp> arr) {
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

        ImageView imgHinhLoaiSP = view.findViewById(R.id.imgHinhLoaiSP);
        TextView txtTenloaiSP = view.findViewById(R.id.txtTenloaiSP);

        Loaisp loaisp = arr.get(position);

        txtTenloaiSP.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhloaisp())
                .placeholder(R.drawable.index)
                .error(R.drawable.delete)
                .into(imgHinhLoaiSP);

        return view;
    }
}
