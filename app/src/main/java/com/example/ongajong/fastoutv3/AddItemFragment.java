package com.example.ongajong.fastoutv3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ongajong.fastoutv3.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ongajong.fastoutv3.EditItem.DataProvider;
import com.example.ongajong.fastoutv3.EditItem.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.glxn.qrgen.android.QRCode;


public class AddItemFragment extends Fragment {

    public AddItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_additem, container, false);
        final EditText ed_addname = (EditText) rootView.findViewById(R.id.ed_addname);
        final EditText ed_addprice = (EditText) rootView.findViewById(R.id.ed_addprice);
        final EditText ed_addquantity = (EditText) rootView.findViewById(R.id.ed_addquantity);
        Toast.makeText(getContext(),"onCreateView", Toast.LENGTH_LONG);
        Button btn_submit = (Button) rootView.findViewById(R.id.btn_add);

        Log.i("Objects Created","On Click before");
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = ed_addname.getText().toString();
                String curr1  = curr.replaceAll("[^A-Za-z]+", "").toLowerCase();
                Bitmap myBitmap = QRCode.from(curr1).bitmap();
                ImageView myImage = (ImageView) rootView.findViewById(R.id.qrdisplay);
                myImage.setImageBitmap(myBitmap);
                double NewPrice = Double.parseDouble(ed_addprice.getText().toString());
                Integer Quantity = Integer.parseInt(ed_addquantity.getText().toString());
                Product item = new Product(curr1, curr, NewPrice,Quantity);
                Log.i("Laura", "New Conversion to Product done");
                DataProvider.productList.add(item);
                DataProvider.productMap.put(curr, item);
                Log.i("Laura", "Added to internal List");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef  = database.getReference("ProductList");
                myRef.child(curr).child("price").setValue(NewPrice);
                myRef.child(curr).child("itemId").setValue(curr1);
                myRef.child(curr).child("quantity").setValue(Quantity);
                myRef.child(curr).child("name").setValue(curr);
                //sendEmail();

            }
        });
        return rootView;
    }
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"lauraong1@yahoo.com.sg"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
