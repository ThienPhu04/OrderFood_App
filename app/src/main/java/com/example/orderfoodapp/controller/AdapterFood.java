package com.example.orderfoodapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodapp.R;
import com.example.orderfoodapp.View.OnItemClickListener;
import com.example.orderfoodapp.model.Cart;
import com.example.orderfoodapp.model.CartDataHolder;
import com.example.orderfoodapp.model.Product;

import java.util.ArrayList;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.MyHolder>{
    private OnItemClickListener listener;
    private ArrayList<Product> listProduct = new ArrayList<>();
    private Context context;
    private int layout = R.layout.item_food_menu;

    public AdapterFood(Context context, ArrayList<Product> listProduct, OnItemClickListener listener) {
        this.context = context;
        this.listProduct = listProduct;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Product p = listProduct.get(position);
        int idImage = context.getResources().getIdentifier(p.getImageProduct(), "drawable", context.getPackageName());
        holder.imgItem.setImageResource(idImage);
        holder.tvFoodName.setText(p.getProduct());
        if (p.getSale() == 0){
            holder.saleTag.setVisibility(View.GONE);
        }
        holder.saleTag.setText((int)(p.getSale()*100) + "% OFF");
        holder.tvPrice.setText(String.valueOf(p.getPrice()) + " đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(p);
            }
        });
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                Cart cartItem = new Cart(p,quantity);
                CartDataHolder.getInstance().addProduct(cartItem);
                Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                quantity++;
                holder.tvQuantity.setText(String.valueOf(quantity));
            }
        });
        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                if(quantity > 1){
                    quantity--;
                    holder.tvQuantity.setText(String.valueOf(quantity));
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return listProduct.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imgItem ;
        TextView tvFoodName,tvPrice;
        TextView saleTag;
        TextView tvQuantity;
        ImageButton btnAddToCart, btnDecrease,btnIncrease;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvFoodName = itemView.findViewById(R.id.tvFood);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            saleTag = itemView.findViewById(R.id.saleTag);
        }
    }
}
