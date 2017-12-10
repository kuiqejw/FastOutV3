package com.example.ongajong.fastoutv3.EditItem;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ongajong.fastoutv3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

public class DetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Opened ","New Activity from fragment");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final String productId = getIntent().getStringExtra(EditItemFragment.PRODUCT_ID);
        final Product product = DataProvider.productMap.get(productId);
        TextView tv = (TextView) findViewById(R.id.nameText);
        tv.setText(product.getName());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        final String price = formatter.format(product.getPrice());
        final TextView priceText = (TextView) findViewById(R.id.priceText);
        priceText.setText(price);

        final Integer quantity = product.getQuantity();
        final TextView quantityText = (TextView) findViewById(R.id.quantityText);
        String quantText = quantity.toString();
        quantityText.setText(quantText);

        TextView newpriceText = (TextView) findViewById(R.id.tv_NewPrice);
        final EditText ed_price = (EditText) findViewById(R.id.ed_price);
        final EditText ed_quantity = (EditText) findViewById(R.id.ed_quantity);
        Button btn_delete = (Button) findViewById(R.id.delete_btn);
        btn_delete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef  = database.getReference("ProductList");
                myRef.child(product.getName()).removeValue();
                DataProvider.productList.remove(product);
                DataProvider.productMap.remove(product);
                finish();
                //want to write finish, but realize that once I exit the activity, recycler view is still showing the old item.
            }
        });
        final Button btn_update = (Button) findViewById(R.id.edit_btn);
        btn_update.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef  = database.getReference("ProductList");
                Double newPrice = Double.parseDouble(ed_price.getText().toString());

                Integer newQuantity = Integer.parseInt(ed_quantity.getText().toString());
                myRef.child(product.getName()).child("price").setValue(newPrice);
                myRef.child(product.getName()).child("quantity").setValue(newQuantity);

                DataProvider.productList.remove(product);
                DataProvider.productMap.remove(product);

                Product item = new Product(product.getItemId(), product.getName(), newPrice,newQuantity);
                DataProvider.productList.add(item);
                DataProvider.productMap.put(product.getItemId(), item);
                priceText.setText(newPrice.toString());
                finish();
            }
        });



    }
    private Bitmap getBitmapFromAsset(String productId) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream stream = null;
        try{
            stream = assetManager.open(productId + ".png");
            return BitmapFactory.decodeStream(stream);
        }catch (IOException e){return null;}
    }
}