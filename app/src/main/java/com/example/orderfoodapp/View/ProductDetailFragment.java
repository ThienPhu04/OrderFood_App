package com.example.orderfoodapp.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodapp.MainActivity;
import com.example.orderfoodapp.R;
import com.example.orderfoodapp.model.Cart;
import com.example.orderfoodapp.model.CartDataHolder;
import com.example.orderfoodapp.model.Product;

import java.util.ArrayList;

public class ProductDetailFragment extends Fragment {

    private int quantity = 1;
    private TextView tvQuantity;
    private ImageButton btnIncrease;
    private ImageButton btnDecrease;
    private ImageButton btnAddToCart;
    private ImageView imgItem;
    private TextView product_name;
    private TextView tvSaleOff;
    private TextView tvPrice;
    private TextView product_description;
    private TextView section_ingredients;
    private TextView tvSumPrice;
    private ImageButton btnBack;
    private LinearLayout layoutSale;
    private TextView tvSalePrice;
    Product product  = new Product();

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve parameters if any
            product = (Product) getArguments().getSerializable("productDetail");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        addControl(view);

        setDataProduct();

        addEvent();

        return view;
    }

    void setDataProduct(){
        int idImg = getContext().getResources().getIdentifier(product.getImageProduct(),"drawable", getContext().getPackageName());
        imgItem.setImageResource(idImg);
        product_name.setText(product.getProduct());
        product_description.setText(product.getDescription());
        tvPrice.setText(String.valueOf(product.getPrice()) + " đ");
        if(product.getSale()!=0){
            layoutSale.setVisibility(View.VISIBLE);
            tvSaleOff.setText(String.valueOf((int)(product.getSale()*100)) + " %");
            tvSalePrice.setText(String.valueOf(Math.round(product.getPrice()*(1.0-product.getSale())) + " đ"));
            tvPrice.setPaintFlags(tvPrice.getPaintFlags() | 16);
            tvPrice.setTextSize(22);
            tvSalePrice.setVisibility(View.VISIBLE);
        }else {
            layoutSale.setVisibility(View.GONE);
            tvSalePrice.setVisibility(View.GONE);
            tvPrice.setPaintFlags(tvPrice.getPaintFlags() & ~16);
        }
        section_ingredients.setText(product.getIngredient());
        tvQuantity.setText(String.valueOf(quantity));
        tvSumPrice.setText(String.valueOf(Math.round(product.getPrice()*(1.0-product.getSale())) + " đ"));
    }

    void addControl(View view){
        tvQuantity = view.findViewById(R.id.tvQuantity);
        btnIncrease = view.findViewById(R.id.btnIncrease);
        btnDecrease = view.findViewById(R.id.btnDecrease);
        btnAddToCart = view.findViewById(R.id.btnAddToCart);
        imgItem = view.findViewById(R.id.imgItem);
        product_name = view.findViewById(R.id.product_name);
        tvSaleOff = view.findViewById(R.id.tvSaleOff);
        tvPrice = view.findViewById(R.id.tvPrice);
        product_description = view.findViewById(R.id.product_description);
        section_ingredients = view.findViewById(R.id.section_ingredients);
        tvSumPrice = view.findViewById(R.id.tvSumPrice);
        btnBack = view.findViewById(R.id.btnBack);
        layoutSale = view.findViewById(R.id.layout_sale);
        tvSalePrice = view.findViewById(R.id.tvSalePrice);
    }

    void addEvent(){
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                if(quantity<10)
                {
                    tvQuantity.setText(String.valueOf(quantity));
                    tvSumPrice.setText(String.valueOf(Math.round(product.getPrice()*(1.0-product.getSale())*quantity)) + " đ");
                } else {
                    Toast.makeText(getContext(),"Qua gioi han", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    tvQuantity.setText(String.valueOf(quantity));
                    tvSumPrice.setText(String.valueOf(Math.round(product.getPrice()*(1.0-product.getSale())*quantity)) + " đ");
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart itemAddCart = new Cart(product,quantity);
                CartDataHolder.getInstance().addProduct(itemAddCart);
                Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
