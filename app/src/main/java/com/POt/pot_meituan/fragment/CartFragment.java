package com.POt.pot_meituan.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.POt.pot_meituan.MainActivity;
import com.POt.pot_meituan.R;
import com.POt.pot_meituan.activity.CheckOutActivity;
import com.POt.pot_meituan.adopter.CurtListAdapter;
import com.POt.pot_meituan.logic.OrderedDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private OrderedDataBaseHelper orderedDataBaseHelper;
    public static List<String> nameList = new ArrayList<>();
    public static List<Double> priceList = new ArrayList<>();
    private ListView listView;
    private double SumPrice;
    private TextView mTextView;
    private Button clearButton, checkoutButton;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listView = view.findViewById(R.id.shoppingId);
        orderedDataBaseHelper = MainActivity.getOrderedDB();
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
        clearButton = view.findViewById(R.id.fragment_cart_clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("delete from ordered");
                nameList.clear();
                priceList.clear();
            }
        });
        CurtListAdapter adopter = new CurtListAdapter(nameList, priceList, inflater);
        listView.removeAllViewsInLayout();
        listView.setAdapter(adopter);
        SumPrice = 0;
        for(int i = 0; i < nameList.size(); i++){
            SumPrice += priceList.get(i);
        }
        mTextView = view.findViewById(R.id.shop_all_price);
        mTextView.setText(String.valueOf(SumPrice));

        checkoutButton = view.findViewById(R.id.shop_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inflater.getContext(), CheckOutActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}