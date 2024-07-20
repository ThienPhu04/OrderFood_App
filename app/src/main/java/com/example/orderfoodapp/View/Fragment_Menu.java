package com.example.orderfoodapp.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodapp.MainActivity;
import com.example.orderfoodapp.R;
import com.example.orderfoodapp.controller.AdapterFood;
import com.example.orderfoodapp.model.Cart;
import com.example.orderfoodapp.model.Product;

import java.util.ArrayList;
import java.util.Comparator;

public class Fragment_Menu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Product> listProduct = new ArrayList<>();
    String[] typeSort = new String[]{"A-Z","Z-A","Price ASC","Price DESC"};
    ArrayAdapter<String> adapterSort;
    AdapterFood adapterFood;
    RecyclerView recyclerListFood;
    Spinner spinnerSort;
    TextView title;
    String titleItem;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Menu() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_Menu newInstance(String param1, String param2) {
        Fragment_Menu fragment = new Fragment_Menu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            listProduct = (ArrayList<Product>) getArguments().getSerializable("ListProduct");
            titleItem = getArguments().getString("Title");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__menu, container, false);
        //set title item choose
        title = view.findViewById(R.id.titleItemMenuChoose);
        title.setText(titleItem);
        setAdapter(view);
        sortProduct();
        return view;
    }


    void setAdapter(View view){
        //set adapter spinner
        spinnerSort = view.findViewById(R.id.spinnerSort);
        adapterSort = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,typeSort);
        spinnerSort.setAdapter(adapterSort);

        //set adapter recycler
        recyclerListFood = view.findViewById(R.id.recyclerListFood);
        adapterFood = new AdapterFood(getContext(), listProduct, new OnItemClickListener(){
            @Override
            public void onItemClick(Product p) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("productDetail",p);
                Product a = (Product) bundle.getSerializable("productDetail");
                ProductDetailFragment fragment = new ProductDetailFragment();
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).loadFragment(fragment, true);
            }
        });
        recyclerListFood.setAdapter(adapterFood);
    }

    public void searchProducts(String query) {
        if(query.equals("")){
            adapterFood = new AdapterFood(getContext(), listProduct, new OnItemClickListener(){
                @Override
                public void onItemClick(Product p) {

                    Bundle bundle = new Bundle();
                    ArrayList<Product> products = new ArrayList<>();
                    products.add(p);
                    bundle.putSerializable("productDetail",products);
                    ProductDetailFragment fragment = new ProductDetailFragment();
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).loadFragment(fragment, true);
                }
            });
        }
        else {
            ArrayList<Product> filteredList = new ArrayList<>();
            for (Product product : listProduct) {
                if (product.getProduct().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            adapterFood = new AdapterFood(getContext(), filteredList, new OnItemClickListener(){
                @Override
                public void onItemClick(Product p) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("productDetail",p);
                    ProductDetailFragment fragment = new ProductDetailFragment();
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).loadFragment(fragment, true);
                }
            });
        }
        recyclerListFood.setAdapter(adapterFood);
    }

    void sortProduct(){
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        listProduct.sort(Comparator.comparing(Product::getProduct));
                        adapterFood.notifyDataSetChanged();
                        break;
                    case 1:
                        listProduct.sort(Comparator.comparing(Product::getProduct).reversed());
                        adapterFood.notifyDataSetChanged();
                        break;
                    case 2:
                        listProduct.sort(Comparator.comparing(Product::getPrice));
                        adapterFood.notifyDataSetChanged();
                        break;
                    case 3:
                        listProduct.sort(Comparator.comparing(Product::getPrice).reversed());
                        adapterFood.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}