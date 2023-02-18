package com.POt.pot_meituan.adopter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.POt.pot_meituan.MainActivity;
import com.POt.pot_meituan.OrderedActivity;
import com.POt.pot_meituan.R;
import com.POt.pot_meituan.logic.OrderedDataBaseHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrderedMenuAdopter extends BaseAdapter {
    private List<String> stringList = new ArrayList<>();
    private OrderedDataBaseHelper orderedDataBaseHelper;
    private SQLiteDatabase db;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public OrderedMenuAdopter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        orderedDataBaseHelper = MainActivity.getOrderedDB();
        db = orderedDataBaseHelper.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return OrderedActivity.Itemname.size();
    }

    @Override
    public Object getItem(int position) {
        return OrderedActivity.Itemname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder{
        public ImageView imageView;
        public TextView textView_1, textView_2, textView_3;
        public android.widget.Button Button;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.meau_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.goods_show_pic);
            viewHolder.textView_1 = convertView.findViewById(R.id.goods_show_name);
            viewHolder.textView_2 = convertView.findViewById(R.id.goods_show_price);
            viewHolder.textView_3 = convertView.findViewById(R.id.goods_show_desc);
            viewHolder.Button = convertView.findViewById(R.id.goods_add);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView_1.setText(OrderedActivity.Itemname.get(position));
        viewHolder.textView_2.setText(String.valueOf(OrderedActivity.Itemprice.get(position)));
        viewHolder.textView_3.setText(OrderedActivity.Itemdescribe.get(position));
        Glide.with(mContext).load(OrderedActivity.Itemimg.get(position)).into(viewHolder.imageView);
        viewHolder.Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", OrderedActivity.Itemname.get(position));
                contentValues.put("price", OrderedActivity.Itemprice.get(position));
                contentValues.put("pic", OrderedActivity.Itemimg.get(position));
                //contentValues.put("name", name[position]);
                db.insert("ordered", null, contentValues);
                contentValues.clear();
                Toast.makeText(mContext, "添加成功", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
