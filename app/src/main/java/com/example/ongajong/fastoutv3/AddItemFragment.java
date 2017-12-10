package com.example.ongajong.fastoutv3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ongajong.fastoutv3.EditItem.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.glxn.qrgen.android.QRCode;

import java.util.List;


public class AddItemFragment extends Fragment {


    public AddItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_additem, container, false);
        TextView tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        final EditText ed_addname = (EditText) rootView.findViewById(R.id.ed_addname);
        TextView tv_price = (TextView) rootView.findViewById(R.id.tv_price);
        final EditText ed_addprice = (EditText) rootView.findViewById(R.id.ed_addprice);
        TextView tv_quantity = (TextView) rootView.findViewById(R.id.tv_quantity);
        final EditText ed_addquantity = (EditText) rootView.findViewById(R.id.ed_addquantity);
        Button btn_submit = (Button) rootView.findViewById(R.id.btn_add);
        final ImageView qrcode = (ImageView) rootView.findViewById(R.id.imageView);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String curr = ed_addname.getText().toString();
                    double NewPrice = Double.parseDouble(ed_addprice.getText().toString());
                    int Quantity = Integer.parseInt(ed_addquantity.getText().toString());
                    Product item = new Product(curr, curr, NewPrice, Quantity);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("ProductList");
                    myRef.child(curr).child("price").setValue(NewPrice);
                    myRef.child(curr).child("itemId").setValue(curr);
                    myRef.child(curr).child("quantity").setValue(Quantity);
                    myRef.child(curr).child("name").setValue(curr);

                    Bitmap myBitmap = QRCode.from(curr).withSize(1000, 1000).bitmap();
                    qrcode.setImageBitmap(myBitmap);
                }catch(Exception e){
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG);
                }
                sendEmail();
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
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(),
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
