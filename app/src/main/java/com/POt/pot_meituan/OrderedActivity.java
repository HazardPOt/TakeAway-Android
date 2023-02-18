package com.POt.pot_meituan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.POt.pot_meituan.adopter.OrderedMenuAdopter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderedActivity extends AppCompatActivity {

    private String date;
    private ListView listView;
    private List<String> stringList = new ArrayList<>();
    private OrderedMenuAdopter adopter;
    public static List<String> Itemname = new ArrayList<>();
    public static List<Double> Itemprice = new ArrayList<>();
    public static List<String> Itemimg = new ArrayList<>();
    public static List<String> Itemdescribe = new ArrayList<>();
    public static List<String> Itemtag = new ArrayList<>();
    public static int realid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        okhttpDate();
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_ordered);
        listView = (ListView) findViewById(R.id.menu_lists);
        adopter = new OrderedMenuAdopter(OrderedActivity.this);
        listView.setAdapter(adopter);
        Intent intent = getIntent();
        String shopid = intent.getStringExtra("shopid");
        realid = Integer.parseInt(shopid);
    }
    private void okhttpDate() {
        Log.i("TAG","--ok-");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url("http://10.4.17.175:9090/item").build();
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
        Itemname.clear();
        Itemprice.clear();
        Itemimg.clear();
        Itemdescribe.clear();
        Itemtag.clear();
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
                    String itemname = jsonArray.getJSONObject(i).getString("itemname");
                    String itemdescribe = jsonArray.getJSONObject(i).getString("itemdescribe");
                    String itemimg = jsonArray.getJSONObject(i).getString("itemimg");
                    if(itemimg.startsWith("http://localhost")){
                        itemimg = itemimg.replace("http://localhost", "http://10.4.17.175");
                    }
                    Double itemprice = jsonArray.getJSONObject(i).getDouble("itemprice");
                    String itemtag = jsonArray.getJSONObject(i).getString("itemtag");
                    Itemname.add(itemname);
                    Itemdescribe.add(itemdescribe);
                    Itemimg.add(itemimg);
                    Itemprice.add(itemprice);
                    Itemtag.add(itemtag);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}