package com.example.orderfoodapp.View;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodapp.R;
import com.example.orderfoodapp.controller.PaymentFragment_Listview_Adapter;
import com.example.orderfoodapp.model.Cart;
import com.example.orderfoodapp.model.CartDataHolder;
import com.example.orderfoodapp.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PaymentFragment extends Fragment {

    private ListView orderListView;
    private PaymentFragment_Listview_Adapter adapter;
    private ArrayList<Cart> cartList;
    private TextView totalQuantityTextView;
    private TextView totalAmountTextView;
    private Button confirmPayment;
    private ImageView qrCodeImageView;
    private RadioGroup paymentMethodGroup;
    private TextView invoiceCodeTextView;
    private int totalQuantity = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // Khởi tạo danh sách các sản phẩm từ CartDataHolder
        cartList = CartDataHolder.getInstance().getProductList();

        // Thiết lập ListView và Adapter
        orderListView = view.findViewById(R.id.order_list);
        totalQuantityTextView = view.findViewById(R.id.total_quantity);
        totalAmountTextView = view.findViewById(R.id.total_amount);
        adapter = new PaymentFragment_Listview_Adapter(getContext(), R.layout.item_order, cartList, totalQuantityTextView, totalAmountTextView);
        orderListView.setAdapter(adapter);

        confirmPayment = view.findViewById(R.id.confirm_payment_button);
        qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
        paymentMethodGroup = view.findViewById(R.id.payment_method_group);
        invoiceCodeTextView = view.findViewById(R.id.invoice_code_text_view);
        updateTotal();
        handleConfirmPaymentBtnClick();

        qrCodeImageView.setVisibility(View.GONE);
        invoiceCodeTextView.setVisibility(View.GONE);
        return view;
    }

    private void handleConfirmPaymentBtnClick() {
        confirmPayment.setOnClickListener(v -> {
            if (totalQuantity == 0) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Giỏ hàng trống")
                        .setMessage("Giỏ hàng của bạn đang trống. Vui lòng chọn sản phẩm trước khi thanh toán.")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận thanh toán")
                    .setMessage("Bạn có chắc chắn muốn thanh toán không?")
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        int selectedId = paymentMethodGroup.getCheckedRadioButtonId();
                        String invoiceCode = createCashPaymentId();
                        if (selectedId == R.id.payment_cash) {
                            qrCodeImageView.setVisibility(View.GONE);
                            invoiceCodeTextView.setText("Mã hóa đơn: " + invoiceCode + "\nVui lòng đến quầy để thanh toán.");
                            invoiceCodeTextView.setVisibility(View.VISIBLE);
                        } else if (selectedId == R.id.payment_qr) {
                            showQRCode(invoiceCode);
                            invoiceCodeTextView.setVisibility(View.GONE);
                        }
                        clearCart(); // Xóa giỏ hàng sau khi thanh toán

                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    private void showQRCode(String invoiceCode) {
        // URL của hình ảnh QR code
        String qrCodeUrl = "https://img.vietqr.io/image/mbbank-5411122004-2PbyHC1.png?amount="+ getTotalAmount() +"&addInfo="+ invoiceCode +"&accountName=Mach%20Lam%20Quoc%20Hoai";
        Log.d("Show qr code", "showQRCode: " + qrCodeUrl);
        qrCodeImageView.setVisibility(View.VISIBLE);
        loadImageWithRetry(qrCodeUrl, 3); // Thử tải lại hình ảnh tối đa 3 lần
    }

    private void loadImageWithRetry(String url, int retryCount) {
        Picasso.get().load(url).into(qrCodeImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("Picasso", "Image loaded successfully");
            }

            @Override
            public void onError(Exception e) {
                Log.e("Picasso", "Error loading image", e);
                if (retryCount > 0) {
                    Log.d("Picasso", "Retrying... (" + retryCount + " attempts left)");
                    loadImageWithRetry(url, retryCount - 1);
                }
            }
        });
    }

    private String paymentId;
    private String createCashPaymentId() {
        if (paymentId == null){
            qrCodeImageView.setVisibility(View.GONE);
            // Lấy ngày tháng năm hiện tại
            String date = new SimpleDateFormat("ddMM", Locale.getDefault()).format(new Date());
            // Tạo 3 số ngẫu nhiên
            int randomNum = new Random().nextInt(900) + 100; // Số ngẫu nhiên từ 100 đến 999
            paymentId = "HD" + date + randomNum;
        }
        return paymentId;
    }

    private void updateTotal() {
        int totalAmount = 0;
        totalQuantity = 0;
        for (Cart item : cartList) {
            float sale = item.getProduct().getSale();
            if (sale != 0) {
                Log.d("sale", "updateTotal: sale = " + sale);
                totalAmount += item.getProduct().getPrice() * (1 - sale) * item.getQuantity();
            } else {
                totalAmount += item.getProduct().getPrice() * item.getQuantity();
            }
            totalQuantity += item.getQuantity();
        }
        totalQuantityTextView.setText(String.format("Số lượng: %d", totalQuantity));
        totalAmountTextView.setText(String.format("Tổng cộng: %,dđ", totalAmount));
    }

    private int getTotalAmount() {
        int totalAmount = 0;
        for (Cart item : cartList) {
            float sale = item.getProduct().getSale();
            if (sale != 0) {
                totalAmount += item.getProduct().getPrice() * (1 - sale) * item.getQuantity();
            } else {
                totalAmount += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        return totalAmount;
    }

    private void clearCart() {
        cartList.clear();
        updateTotal(); // Cập nhật lại tổng số lượng và tổng số tiền
        CartDataHolder.getInstance().setProductList(new ArrayList<>()); // Xóa giỏ hàng sau khi thanh toán
        adapter.notifyDataSetChanged();
    }
}
