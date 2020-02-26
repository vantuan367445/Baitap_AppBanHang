package com.example.tuanvatvo.baitap_appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tuanvatvo.baitap_appbanhang.R;
import com.example.tuanvatvo.baitap_appbanhang.activiti.ChiTietSPActivity;
import com.example.tuanvatvo.baitap_appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham>arraySanPham;
    //Constructor.
    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }
//    Mỗi Adapter cần 3 phương thức chính:
//    onCreateViewHolder - Định nghĩa các Item layout và khởi tạo Holder.
//    onBindViewHolder - Thiết lập các thuộc tính của View và dữ liệu.
//    getItemCount - Đếm số Item trong List Data.

    @NonNull
    @Override
    //onCreateViewHolder - Định nghĩa các Item layout và khởi tạo Holder.
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    // onBindViewHolder - Thiết lập các thuộc tính của View và dữ liệu.
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = arraySanPham.get(position);
        holder.txtTensanpham.setText(sanPham.getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiasanpham.setText("Giá : " + decimalFormat.format(sanPham.getGiasanpham())+ " Đ");
        //holder.txtGiasanpham.setText(sanPham.getGiasanpham()+"");
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .into(holder.imgHinhsanpham);

    }

    @Override
    //getItemCount - Đếm số Item trong List Data.
    public int getItemCount() {
        return arraySanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhsanpham;
        public TextView txtTensanpham,txtGiasanpham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            imgHinhsanpham = itemView.findViewById(R.id.imgSanphamnew);
            txtGiasanpham = itemView.findViewById(R.id.txtGiasanphammoi);
            txtTensanpham = itemView.findViewById(R.id.txtTensanphammoi);

            // khi người dùng click vào sản phẩm ở  mục  " Sản Phẩm mới nhất "
            // thì cũng chuyển sang màn hình ChiTietSP
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSPActivity.class);
                    intent.putExtra("thongtinsanpham",arraySanPham.get(getPosition()));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
