package com.example.denis.assign42018;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/** {@link CollectionFlavorAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link CollectionFlavor} objects.<br>
 * <b>For Assignment 4 there is based on the code from DCU OpenICT SDA Assignment 3C</b>
 */
public class CollectionFlavorAdapter extends ArrayAdapter<CollectionFlavor> {

    //static final variable for log cat purposes
    private static final String LOG_TAG = CollectionFlavorAdapter.class.getSimpleName();

    /**
     * This is based on DCU OpenICT's own custom constructor used in Assignment 3 SDA (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.<br>
     * In assignment 4 this is a re-tasking of ProductFlavourAdapter for the purposes of the Collections tab.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param collectionFlavors A List of AndroidFlavor objects to display in a list
     */
    public CollectionFlavorAdapter(Activity context, ArrayList<CollectionFlavor> collectionFlavors) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for three TextViews the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, collectionFlavors);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.collect_layout, parent, false);
        }

        // Get the {@link CollectionFlavor} object located at this position in the list
        CollectionFlavor currentCollectionFlavor = getItem(position);

        // Find the TextView in the collect_layout.xml layout with the ID collect_location
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.collect_location);
        /* Get the collection location from the current CollectionFlavor object and
           set this text on the collect_location TextView*/
        locationTextView.setText(currentCollectionFlavor.getCollectionLocation());

        // Find the TextView in the collect_layout.xml layout with the ID collect_address
        TextView addressTextView = (TextView) listItemView.findViewById(R.id.collect_address);
        /* Get the collection address from the current CollectionFlavor object and
            set this text on the collect_text TextView*/
        addressTextView.setText(currentCollectionFlavor.getCollectionAddress());

        // Find the TextView in the collect_layout.xml layout with the ID collect_phone
        TextView phoneTextView = (TextView) listItemView.findViewById(R.id.collect_phone);
        /* Get the collection phone number from the current CollectFlavor object and
            set this text on the collect_phone TextView*/
        phoneTextView.setText(currentCollectionFlavor.getCollectionPhone());

        /* Return the whole list item layout (containing 3 TextViews)
            so that it can be shown in the ListView*/
        return listItemView;
    }
}
