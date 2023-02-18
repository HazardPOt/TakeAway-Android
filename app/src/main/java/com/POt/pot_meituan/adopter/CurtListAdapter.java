package com.POt.pot_meituan.adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.POt.pot_meituan.R;

import java.util.ArrayList;
import java.util.List;

public class CurtListAdapter extends BaseAdapter {

    private List<String> nameList = new ArrayList<>();
    private List<Double> priceList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;


    public CurtListAdapter(List<String> nameList, List<Double> priceList, LayoutInflater inflater){
        this.nameList = nameList;
        this.priceList = priceList;
        this.mLayoutInflater = inflater;
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameList.get(position);
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView textView_1, textView_2, textView_3, textView_4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShopListAdopter.ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.curt_item, null);
            viewHolder = new ShopListAdopter.ViewHolder();
            //viewHolder.imageView = convertView.findViewById(R.id.goods_show_name);
            viewHolder.textView_1 = convertView.findViewById(R.id.goods_show_name);
            viewHolder.textView_2 = convertView.findViewById(R.id.goods_show_price);
            //viewHolder.textView_3 = convertView.findViewById(R.id.lv_tv_3);
            //viewHolder.textView_4 = convertView.findViewById(R.id.lv_tv_4);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ShopListAdopter.ViewHolder) convertView.getTag();
        }
        viewHolder.textView_1.setText(nameList.get(position));
        viewHolder.textView_2.setText("¥" + String.valueOf(priceList.get(position)));
        //viewHolder.textView_3.setText(describe[position]);
        //viewHolder.textView_4.setText(time[position]);
        //Glide.with(mFragment).load(pic[position]).into(viewHolder.imageView);
        return convertView;
    }
}
