package com.POt.pot_meituan.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.POt.pot_meituan.R;
import com.POt.pot_meituan.fragment.IndexFragment;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShopListAdopter extends BaseAdapter {

    private IndexFragment mFragment;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public ShopListAdopter(Context context, LayoutInflater inflater, IndexFragment indexFragment, List<String> ShopName, List<String> ShopDescribe, List<String> ShopImg, List<String> Shoptime, List<String> ShopSales){
        this.mLayoutInflater = inflater;
        this.mFragment = indexFragment;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return IndexFragment.ShopName.size();
    }

    @Override
    public Object getItem(int position) {
        return IndexFragment.ShopName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView textView_1, textView_2, textView_3, textView_4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.activity_shop_list_adopter, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.lv_iv_1);
            viewHolder.textView_1 = convertView.findViewById(R.id.lv_tv_1);
            viewHolder.textView_2 = convertView.findViewById(R.id.lv_tv_2);
            viewHolder.textView_3 = convertView.findViewById(R.id.lv_tv_3);
            viewHolder.textView_4 = convertView.findViewById(R.id.lv_tv_4);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView_1.setText(IndexFragment.ShopName.get(position));
        viewHolder.textView_2.setText(IndexFragment.ShopSales.get(position));
        viewHolder.textView_3.setText(IndexFragment.ShopDescribe.get(position));
        viewHolder.textView_4.setText(IndexFragment.Shoptime.get(position));
        Glide.with(mContext).load(IndexFragment.ShopImg.get(position).toString()).into((ImageView) viewHolder.imageView);
        return convertView;
    }
}