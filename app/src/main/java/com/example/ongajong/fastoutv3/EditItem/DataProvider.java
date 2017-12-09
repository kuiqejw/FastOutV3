package com.example.ongajong.fastoutv3.EditItem;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ongajong on 12/7/2017.
 */

public class DataProvider {
    public static List<Product> productList = new ArrayList<>();
    public static Map<String, Product> productMap = new HashMap<>();
    public static List<String> catalogue = new ArrayList<>();
    static{
//
//        addProduct("shirt101",
//                "Cross-back training tank",
//                35,23);
//
//        addProduct("jacket101",
//                "Bamboo thermal ski coat",
//                128,24);
//
//        addProduct("pants101",
//                "Stretchy dance pants",
//                85,25);
//
//        addProduct("shirt102",
//                "Ultra-soft tank top",
//                23,26);
//
//        addProduct("shirt103",
//                "V-neck t-shirt",
//                26,27);
//
//        addProduct("sweater101",
//                "V-neck sweater",
//                65,28);
//
//        addProduct("shirt104",
//                "Polo shirt",
//                38,29);
//
//        addProduct("shirt105",
//                "Skater graphic T-shirt\n",
//                45,30);
//
//        addProduct("jacket102",
//                "Thermal fleece jacket",
//                85,31);
//
//        addProduct("shirt106",
//                "V-neck pullover",
//                35,32);
//
//        addProduct("shirt107",
//                "V-neck T-shirt",
//                28,33);
//
//        addProduct("pants102",
//                "Grunge skater jeans",
//                75,34);
//
//        addProduct("vest101",
//                "Thermal vest",
//                95,35);
    }

    protected static void addProduct(String itemId, String name, double price,Integer quantity) {
        Product item = new Product(itemId, name, price,quantity);
        productList.add(item);
        productMap.put(itemId, item);
        if(!catalogue.contains(itemId))
            catalogue.add(itemId); //proviso in case got multiple instances of the same item
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef  = database.getReference("ProductList");
        myRef.child(name).child("price").setValue(price);
        myRef.child(name).child("itemId").setValue(itemId);
        myRef.child(name).child("quantity").setValue(quantity);
        myRef.child(name).child("name").setValue(name);

    }
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private static void deleteProduct(String itemId, String name, double price) {
//        Product item = new Product(itemId, name, price);
//        productList.remove(item);
//        productMap.remove(itemId, item);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef  = database.getReference("ProductList");
//        myRef.child(item.getName()).removeValue();
//
//    }

//    public static List<String> getProductNames() {
//        List<String> list = new ArrayList<>();
//        for (Product product : productList) {
//            list.add(product.getName());
//        }
//        return list;
//    }
//
//    public static List<Product> getFilteredList(String searchString) {
//
//        List<Product> filteredList = new ArrayList<>();
//        for (Product product : productList) {
//            if (product.getProductId().contains(searchString)) {
//                filteredList.add(product);
//            }
//        }
//
//        return filteredList;
//
//    }


}
