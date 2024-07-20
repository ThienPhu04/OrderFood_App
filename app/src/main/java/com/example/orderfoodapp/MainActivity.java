package com.example.orderfoodapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfoodapp.View.Fragment_Menu;
import com.example.orderfoodapp.View.PaymentFragment;
import com.example.orderfoodapp.model.Product;
import com.example.orderfoodapp.model.ProductHandler;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<Product> allProduct = new ArrayList<>();
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDataProduct();
        addControl();
        setToolbar();
        addEvent();

        //hien thi trang dau
        Bundle bundle = new Bundle();
        bundle.putString("Title", "Hot Trend");
        bundle.putSerializable("ListProduct", filterProductClasify("",1));
        Fragment_Menu fragmentMenu = new Fragment_Menu();
        fragmentMenu.setArguments(bundle);
        loadFragment(fragmentMenu, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                if (fragment instanceof Fragment_Menu) {
                    // Đặt thời gian trì hoãn 0.02 giây trước khi gọi filterArrayList
                    searchView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((Fragment_Menu) fragment).searchProducts(newText);
                        }
                    }, 200); // 20 milliseconds delay
                }
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Xử lý các item trong menu nếu cần
        int id = item.getItemId();

        if(R.id.trend == id){
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Hot Trend");
            bundle.putSerializable("ListProduct", filterProductClasify("",1));
            Fragment_Menu fragmentMenu = new Fragment_Menu();
            fragmentMenu.setArguments(bundle);
            loadFragment(fragmentMenu, false);
        }
        else if(R.id.sale == id){
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Sale");
            bundle.putSerializable("ListProduct", filterProductClasify("",2));
            Fragment_Menu fragmentMenu = new Fragment_Menu();
            fragmentMenu.setArguments(bundle);
            loadFragment(fragmentMenu, false);
        }
        else if (R.id.maindish == id) {
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Main Dish");
            bundle.putSerializable("ListProduct", filterProductClasify("MonChinh",0));
            Fragment_Menu fragmentMenu = new Fragment_Menu();
            fragmentMenu.setArguments(bundle);
            loadFragment(fragmentMenu, false);
        }
        else if (R.id.sidedish == id) {
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Side Dish");
            bundle.putSerializable("ListProduct", filterProductClasify("TrangMieng",0));
            Fragment_Menu fragmentMenu = new Fragment_Menu();
            fragmentMenu.setArguments(bundle);
            loadFragment(fragmentMenu, false);
        }
        else if(R.id.water == id){
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Water");
            bundle.putSerializable("ListProduct", filterProductClasify("MonUong",0));
            Fragment_Menu fragmentMenu = new Fragment_Menu();
            fragmentMenu.setArguments(bundle);
            loadFragment(fragmentMenu, false);
        }
        else if(R.id.desserts == id){
            Bundle bundle = new Bundle();
            bundle.putString("Title", "Desserts");
            bundle.putSerializable("ListProduct", filterProductClasify("MonPhu",0));
            Fragment_Menu fragmentMenu = new Fragment_Menu();
            fragmentMenu.setArguments(bundle);
            loadFragment(fragmentMenu, false);
        }
        return super.onOptionsItemSelected(item);
    }

    void addControl(){
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_Drawer);
        drawerLayout = findViewById(R.id.main);

    }

    void setToolbar(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout, toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    void addEvent(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(R.id.menu == id){
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", "Hot Trend");
                    bundle.putSerializable("ListProduct", filterProductClasify("",1));
                    Fragment_Menu fragmentMenu = new Fragment_Menu();
                    fragmentMenu.setArguments(bundle);
                    loadFragment(fragmentMenu, false);
                    return true;
                }
                if(R.id.order == id){
                    PaymentFragment paymentFragment = new PaymentFragment();
                    loadFragment(paymentFragment,false);
                    return true;
                }
                return false;
            }
        });

    }

    public void loadFragment(Fragment fragment, boolean addToBackStack){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout,fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    void loadDataProduct(){
        ProductHandler productHandler = new ProductHandler(MainActivity.this);
        productHandler.onUpgrade(db, 1, 2);
        productHandler.initData();

        allProduct = productHandler.getAllProduct();
    }

    ArrayList<Product> filterProductClasify(String str, int mode){
        ArrayList<Product> list = new ArrayList<>();
        if (mode == 0){
            for (Product p: allProduct) {
                if(p.getClassify().equals(str)){
                    list.add(p);
                }
            }
        } else if (mode == 1) {
            for (Product p: allProduct) {
                if(p.getTrending() == 1){
                    list.add(p);
                }
            }
        } else if (mode == 2) {
            for (Product p: allProduct) {
                if(p.getSale() != 0){
                    list.add(p);
                }
            }
        }
        return list;
    }
}