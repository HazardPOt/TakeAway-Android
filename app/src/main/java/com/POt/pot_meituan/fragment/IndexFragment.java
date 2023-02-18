package com.POt.pot_meituan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.POt.pot_meituan.OrderedActivity;
import com.POt.pot_meituan.R;
import com.POt.pot_meituan.adopter.PageAdopter;
import com.POt.pot_meituan.adopter.ShopListAdopter;
import com.POt.pot_meituan.utils.HttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment {


    private ViewPager viewPager;
    private ListView listView;
    private Handler mHandler = new Handler();
    public static List<View> mViewList = new ArrayList<>();
    public static int[] Img_ad = new int[]{
            R.drawable.ad1pic,
            R.drawable.ad2pic,
            R.drawable.ad3pic
    };
    public static List<String> ShopName = new ArrayList<>();
    public static List<String> ShopDescribe = new ArrayList<>();
    public static List<String> ShopImg = new ArrayList<>();
    public static List<String> Shoptime = new ArrayList<>();
    public static List<String> ShopSales = new ArrayList<>();
    private String date;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HttpUtils.sendOkHttpRequest("http://10.4.157.41:9090/shop", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                jsonJXDate(responseData);
            }
        });
//        okhttpDate();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        viewPager = view.findViewById(R.id.main_VP);
        PageAdopter adapter = new PageAdopter();
        viewPager.setAdapter(adapter);
        initData();
        autoScroll();
        listView = view.findViewById(R.id.index_shoplist);
        ShopListAdopter shopListAdopter = new ShopListAdopter(this.getActivity(), inflater, IndexFragment.this, ShopName, ShopDescribe, ShopImg, Shoptime, ShopSales);
        listView.setAdapter(shopListAdopter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int shopid = (Integer)shopListAdopter.getItem(position);
                Intent intent;
                intent = new Intent(getActivity(), OrderedActivity.class);
                intent.putExtra("shopid", String.valueOf(shopid));
                startActivity(intent);
            }
        });
        return view;
    }

    private void autoScroll() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1);
                mHandler.postDelayed(this, 2000);
            }
        }, 2000);
    }

    private void initData() {
        for (int i = 0; i < Img_ad.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.viewpager_ad, null);
            ImageView imageView_ad = inflate.findViewById(R.id.Img_view);
            imageView_ad.setImageResource(Img_ad[i]);
            mViewList.add(inflate);
        }
    }
    //通过Http协议下载服务器上的图片
    private void okhttpDate() {
        Log.i("TAG","--ok-");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url("http://10.4.17.175:9090/shop").build();
                try {
                    Response sponse = client.newCall(request).execute();
                    date = sponse.body().string();
                    //解析
                    jsonJXDate(date);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void jsonJXDate(String date) {
        if(date!=null) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(date);
                //转对象
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                //对象转数组用来遍历
                JSONArray jsonArray = jsonObject1.getJSONArray("records");
                //遍历数组
                for (int i = 0; i < jsonArray.size(); i++) {
                    //将字段的值遍历并转型
                    String shopname = jsonArray.getJSONObject(i).getString("shopname");
                    String shopdescribe = jsonArray.getJSONObject(i).getString("shopdescribe");
                    String shopimg = jsonArray.getJSONObject(i).getString("shopimg");
                    if(shopimg.startsWith("http://localhost")){
                        shopimg = shopimg.replace("http://localhost", "http://10.4.17.175");
                    }
                    String shoptime = jsonArray.getJSONObject(i).getString("shoptime");
                    String shopsales = jsonArray.getJSONObject(i).getString("shopsales");
                    ShopName.add(shopname);
                    ShopDescribe.add(shopdescribe);
                    ShopImg.add(shopimg);
                    Shoptime.add(shoptime);
                    ShopSales.add(shopsales);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}