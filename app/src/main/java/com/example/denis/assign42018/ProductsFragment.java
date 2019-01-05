package com.example.denis.assign42018;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products,container,false);

        // Create an ArrayList of ProductFlavour objects to represent the Shop products
        // for Assignment 3 the vName and vNumbers have been adapted for the Shop context rather than the Android Flavours
        final ArrayList<ProductFlavor> productFlavors = new ArrayList<ProductFlavor>();
        productFlavors.add(new ProductFlavor("T-Shirt", "0.10" , R.drawable.tshirt));
        productFlavors.add(new ProductFlavor("Shorts",  ("€1.50"), R.drawable.shorts));
        productFlavors.add(new ProductFlavor("Vest",   ("€3.00"), R.drawable.vest));
        productFlavors.add(new ProductFlavor("Jeans", ("€4:50"), R.drawable.trousers));
        productFlavors.add(new ProductFlavor("Caps",  ("€5.00"), R.drawable.cap));
        productFlavors.add(new ProductFlavor("Hoodie",  ("€6.50"), R.drawable.hoodie));
        productFlavors.add(new ProductFlavor("Tie",  ("€7.50"), R.drawable.tie));
        productFlavors.add(new ProductFlavor("Shoes",  ("€9.00"), R.drawable.boots));
        productFlavors.add(new ProductFlavor("Shirt",  ("€10.99"), R.drawable.shirt));
        productFlavors.add(new ProductFlavor("Belt",  ("€10.99"), R.drawable.belt));
        productFlavors.add(new ProductFlavor("Runners",  ("€10.99"), R.drawable.sneakers));


        /* Create an ProductFlavorAdapter, whose data source is a list of
        ProductFlavor's. The adapter knows how to create list item views for each item
        in the list.
        */
        final ProductFlavorAdapter flavorAdapter = new ProductFlavorAdapter(getActivity(), productFlavors);

        // Get a reference to the ListView, and attach the adapter to the listView.
        final ListView listView = (ListView) root.findViewById(R.id.listview_products);
        listView.setAdapter(flavorAdapter);

        // Set the toast
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // here I have the position of the element in the arraylist 'position'
                // declare a AndroidFlavour instance called product value and set it to the appropriate AndroidFlavour at the position in the arraylist.
                ProductFlavor productValue = productFlavors.get(position);
                Toast.makeText(getActivity(),
                        productValue.getProductName(), Toast.LENGTH_LONG)
                        .show();
            }
        });



    return root;
    }
}
