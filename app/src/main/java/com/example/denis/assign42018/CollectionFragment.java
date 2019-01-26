package com.example.denis.assign42018;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CollectionFragment extends Fragment {

    public static final String SAVED_COLLECTION_KEY = "SAVED_COLLECTION_KEY";
    private String mCollectionText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_collection,container,false);
        //setup shared preferences
        final SharedPreferences prefs = this.getActivity().getPreferences(MODE_PRIVATE);

        // Create an ArrayList of CollectionFlavour objects to represent the Collection Shop
        // for Assignment 4 the ProductFlavour adapter has been adapted for the Collections context Products context
        final ArrayList<CollectionFlavor> collectionFlavors = new ArrayList<CollectionFlavor>();
        collectionFlavors.add(new CollectionFlavor("Mahon Point", "Link Rd, Mahon, Cork." , "021-1112223"));
        collectionFlavors.add(new CollectionFlavor("Queens Old Castle", "Grand Parade, Cork City." , "021-2223334"));
        collectionFlavors.add(new CollectionFlavor("Blackpool", "Millfield Cottages, Blackpool, Cork." , "021-3334445"));
        collectionFlavors.add(new CollectionFlavor("Millstreet", "18 Main St, Millstreet, Co. Cork." , "029-4445556"));
        collectionFlavors.add(new CollectionFlavor("Little Island", "Unit 7, Ballytrasna, Little Island, Cork." , "021-5556667"));
        collectionFlavors.add(new CollectionFlavor("Midleton", "23-25, Main Street, Midleton, Co Cork." , "021-6667778"));

        /* Create an CollectionFlavorAdapter, whose data source is a list of
        CollectionFlavor's. The adapter knows how to create list item views for each item
        in the list.
        */
        final CollectionFlavorAdapter flavorAdapter = new CollectionFlavorAdapter(getActivity(), collectionFlavors);

        // Get a reference to the ListView, and attach the adapter to the listView.
        final ListView listView = (ListView) root.findViewById(R.id.listview_collections);
        listView.setAdapter(flavorAdapter);

        // Set the toast & save the data
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // here I have the position of the element in the arraylist 'position'
                // declare a AndroidFlavour instance called product value and set it to the appropriate AndroidFlavour at the position in the arraylist.
                CollectionFlavor collectionValue = collectionFlavors.get(position);
                mCollectionText=collectionValue.getCollectionLocation();

                // saveData
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(SAVED_COLLECTION_KEY, mCollectionText );
                editor.apply();

                //check memorised value and send a message to user
                Toast.makeText(getActivity(),prefs.getString(SAVED_COLLECTION_KEY,"")+" selected for collection", Toast.LENGTH_LONG)
                        .show();

            }
        });




        return root;
    }
}