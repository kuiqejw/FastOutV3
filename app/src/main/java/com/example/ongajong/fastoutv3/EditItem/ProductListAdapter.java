package com.example.ongajong.fastoutv3.EditItem;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ongajong.fastoutv3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by ongajong on 12/7/2017.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {
        private List<Product> products;
        public ProductListAdapter(Context context, int resource, List<Product> objects) {
            super(context, resource, objects);
            products = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).
                        inflate(R.layout.list_item, parent, false);
            }
            Product product = products.get(position);
            TextView nameText = convertView.findViewById(R.id.nameText);
            nameText.setText(product.getName());

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String price = formatter.format(product.getPrice());
            TextView priceText = (TextView) convertView.findViewById(R.id.priceText);
            priceText.setText(price);

            ImageView iv = convertView.findViewById(R.id.imageView);
            try {
                Bitmap bitmap = getBitmapFromAsset(product.getProductId());
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return convertView;
        }
        private Bitmap getBitmapFromAsset(String productId) throws IOException {
            AssetManager assetManager = getContext().getAssets();
            InputStream stream = null;
            try{
                stream = assetManager.open(productId + ".png");
                return BitmapFactory.decodeStream(stream);
            }catch (IOException e){return null;}
        }

}