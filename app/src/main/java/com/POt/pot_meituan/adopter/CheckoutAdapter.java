package com.POt.pot_meituan.adopter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.POt.pot_meituan.MainActivity;
import com.POt.pot_meituan.R;
import com.POt.pot_meituan.logic.OrderedDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>
{

    public List<String> nameList = new ArrayList<>();
    public List<Double> priceList = new ArrayList<>();

    @NonNull
    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.curt_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public CheckoutAdapter(){
        OrderedDataBaseHelper orderedDataBaseHelper = MainActivity.getOrderedDB();
        SQLiteDatabase db = orderedDataBaseHelper.getReadableDatabase();
        Cursor cursor=db.query("ordered",null,null,null,
                null,null,null);//读取所有信息
        String str = "";
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("name"));//根据key值读取信息
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                nameList.add(name);
                priceList.add(price);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder holder, int position) {

        holder.tv1.setText(nameList.get(position));
        holder.tv2.setText(String.valueOf(priceList.get(position)));
        //holder.imageView.setImageResource();
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1, tv2;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.goods_show_name);
            tv2 = itemView.findViewById(R.id.goods_show_price);
            imageView = itemView.findViewById(R.id.goods_show_pic);
        }
    }
}
