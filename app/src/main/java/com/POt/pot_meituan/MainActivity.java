package com.POt.pot_meituan;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.POt.pot_meituan.fragment.CartFragment;
import com.POt.pot_meituan.fragment.IndexFragment;
import com.POt.pot_meituan.fragment.MeFragment;
import com.POt.pot_meituan.logic.OrderedDataBaseHelper;
import com.POt.pot_meituan.ui.Display;
import com.POt.pot_meituan.utils.MainTab;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
//
//    @Nullable
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewpagerTab;

    private static OrderedDataBaseHelper orderedDB;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SQLiteStudioService.instance().start(this);
        orderedDB = new OrderedDataBaseHelper(this, "ordered.db", null, 1);
        SQLiteDatabase orderedDataBase = orderedDB.getWritableDatabase();
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        handleIntent(getIntent());
        initializeViews(savedInstanceState);


//        initView();
    }

    public static OrderedDataBaseHelper getOrderedDB(){
        return orderedDB;
    }

//    private void initView(){
//        indexFragment = new IndexFragment();
//        meFragment = new MeFragment();
//        cartFragment = new CartFragment();
//        //BottomNavigationView
//        BottomNavigationView navigationView = findViewById(R.id.main_bottomNavigationView);
//        //????????????id  menu ??? navigation??????id????????????
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_index, R.id.navigation_cart, R.id.navigation_me)
//                .build();
//        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        //??????ActionBar???????????????????????????fragment?????????
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        //???navcontroller?????????
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//        //??????BottomNavigationView????????????
//        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                //????????????fragment
//                navController.navigate(menuItem.getItemId());
//                //??????false???????????????????????????????????????true?????????????????????
//                return false;
//            }
//        });
//    }


    protected void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(Display.PARAM_OBJ)) {
            MainTab tab = (MainTab) intent.getSerializableExtra(Display.PARAM_OBJ);
            switch (tab) {
                default:
                case SHOP:
                    mViewPager.setCurrentItem(0);
                    break;
                case ORDERS:
                    mViewPager.setCurrentItem(1);
                    break;
                case PERSON:
                    mViewPager.setCurrentItem(2);
                    break;
            }
        }
    }


    private void initializeViews(Bundle savedInstanceState){
        final LayoutInflater inflater = LayoutInflater.from(this);
        final int[] tabIcons = {R.drawable.icon_mainpage, R.drawable.icon_cartfull, R.drawable.icon_me};
        final int[] tabTitles = {R.string.tab_home, R.string.tab_cart, R.string.tab_me};
        // add ?????????????????????????????????replace????????????
        FragmentPagerItems pages = FragmentPagerItems.with(this)
                .add(R.string.tab_home, IndexFragment.class)
                .add(R.string.tab_cart, CartFragment.class)
                .add(R.string.tab_me, MeFragment.class)
                .create();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                pages);
        mViewPager.setOffscreenPageLimit(pages.size());
        mViewPager.setAdapter(adapter);
        mViewpagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = inflater.inflate(R.layout.bottom_nav_menu, container, false);
                ImageView iconView = (ImageView) view.findViewById(R.id.img_icon);
                iconView.setBackgroundResource(tabIcons[position % tabIcons.length]);
                TextView titleView = (TextView) view.findViewById(R.id.txt_title);
                titleView.setText(tabTitles[position % tabTitles.length]);
                return view;
            }
        });
        mViewpagerTab.setViewPager(mViewPager);



    }

}
