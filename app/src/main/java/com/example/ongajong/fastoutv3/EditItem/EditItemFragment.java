package com.example.ongajong.fastoutv3.EditItem;

/**
 * Created by ongajong on 12/7/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ongajong.fastoutv3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditItemFragment extends Fragment {
    static private List<Product> products = DataProvider.productList;
    public static final String PRODUCT_ID = "PRODUCT_ID";

    private static final int DETAIL_REQUEST = 1111;
    public static final String RETURN_MESSAGE = "RETURN MESSAGE";

    public EditItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (products == null) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef  = database.getReference("ProductList");
//        Log.i("Laura", "Firebase Reference gotten");
//        myRef.addChildEventListener(new ChildEventListener() {
//           @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                    Product product = postSnapshot.getValue(Product.class);
//                    String name = postSnapshot.getKey();
//                    products.add(product);
//                    DataProvider.productList.add(product);
//                    DataProvider.productMap.put(name,product);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                    Product product = postSnapshot.getValue(Product.class);
//                    String name = postSnapshot.getKey();
//                    products.add(product);
//                    DataProvider.productList.add(product);
//                    DataProvider.productMap.put(name,product);
//                }
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {}
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " );
//
//            }
//        });
//        }
        View rootView = inflater.inflate(R.layout.fragment_edititem, container, false);
        try{ProductListAdapter adapter = new ProductListAdapter(getActivity(),R.layout.list_item, products);
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Item Created","Look at Detail Activity");
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                //create an instance of class and display there
                Product product = products.get(position);//have the complex product. Could break this down and pass value to all of product
                //go with simpler approach: pass product Id, primary key and leave it to decide what to di.

                intent.putExtra(PRODUCT_ID, product.getProductId());//String is the name of extra, and the primitives and other simple types of java

                Log.i("Item Created","Finish Put extra");
                startActivityForResult(intent,DETAIL_REQUEST);
            }
        });}catch(Exception e){e.printStackTrace();
        Log.i("Laura", "Error at" + e.toString());
        }
        return rootView;
    }



}