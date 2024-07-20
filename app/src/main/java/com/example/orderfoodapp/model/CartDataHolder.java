package com.example.orderfoodapp.model;

import java.util.ArrayList;

//khai bao lop sigleton de su dung du lieu cho toan bai
public class CartDataHolder {
    private static final CartDataHolder instance = new CartDataHolder();
    private ArrayList<Cart> productList;

    private CartDataHolder() {
        productList = new ArrayList<>();
    }

    public static CartDataHolder getInstance() {
        return instance;
    }

    public ArrayList<Cart> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Cart> productList) {
        this.productList = productList;
    }

    public void addProduct(Cart cartItem) {
        productList.add(cartItem);
    }
}
