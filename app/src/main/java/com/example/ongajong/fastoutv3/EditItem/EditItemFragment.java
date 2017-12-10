package com.example.ongajong.fastoutv3.EditItem;

/**
 * Created by ongajong on 12/7/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ongajong.fastoutv3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditItemFragment extends Fragment {
    RecyclerView myRecycleview;
    MyAdapter adapter;
    List<Product> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;
    public static final String PRODUCT_ID = "PRODUCT_ID";

    public EditItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edititem, container, false);
        myRecycleview = (RecyclerView) rootView.findViewById(R.id.rv);
        myRecycleview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), myRecycleview, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),DetailActivity.class);
                Product product = listData.get(position);
                intent.putExtra(PRODUCT_ID, product.getItemId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }
        ));
        myRecycleview.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        myRecycleview.setLayoutManager(LM);
        myRecycleview.setItemAnimator(new DefaultItemAnimator());
        myRecycleview.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        Log.i("onCreateView", "RecycleView Added");

        listData = new ArrayList<>();
        adapter= new MyAdapter(listData);
        FDB = FirebaseDatabase.getInstance();
        GetDataFirebase();
        return rootView;
    }
    void GetDataFirebase(){

        DBR = FDB.getReference("ProductList");

        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Product data = dataSnapshot.getValue(Product.class);
                //Now add to ArrayList
                listData.add(data);
                //Now Add List into Adapter/Recyclerview
                myRecycleview.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listData.clear();
                Product data = dataSnapshot.getValue(Product.class);
                //Now add to ArrayList
                listData.add(data);
                //Now Add List into Adapter/Recyclerview
                myRecycleview.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        List<Product> listArray;
        public MyAdapter(List List){
            this.listArray = List;
        }

        //Ctrl + O


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

            return new MyViewHolder(view);
        }



        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Product data = listArray.get(position);
            holder.Name.setText(data.getName());
            double pricehing = data.getPrice();
            String pricetag = String.valueOf(pricehing);
            holder.Price.setText("$ " + pricetag);
            holder.Quantity.setText(data.getQuantity().toString());

            DataProvider.productList.add(data);
            DataProvider.productMap.put(data.getItemId(),data);

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            //Ctrl + O
            TextView Name;
            TextView Price;
            TextView Quantity;


            public MyViewHolder(View itemView) {
                super(itemView);
                Name = (TextView) itemView.findViewById(R.id.textView);
                Price = (TextView) itemView.findViewById(R.id.tv_Price);
                Quantity = (TextView) itemView.findViewById(R.id.tv_Quantity);


            }
        }

        @Override
        public int getItemCount() {
            return listArray.size();
        }
    }

}