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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionFragment extends Fragment {
    public static final String SAVED_COLLECTION_KEY = "SAVED_PRODUCT_KEY";
    private String mCollectionText;
    /**
     * Spinner for 'Collection From' field
     */
    Spinner mcollectSpinner;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_collection,container,false);
        //setup shared preferences
        final SharedPreferences prefs = this.getActivity().getPreferences(MODE_PRIVATE);

        //initialise spinner using the integer array
        mcollectSpinner = (Spinner) root.findViewById(R.id.collectSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ui_collection_entries, R.layout.spinner_days);
        mcollectSpinner.setAdapter(adapter);


        //Problem Here in that the toast is created on creation of the mcollectSpinner



        //get a reference to the spinner
        mCollectionText= mcollectSpinner.getSelectedItem().toString();
        // saveData

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SAVED_COLLECTION_KEY, mCollectionText );
        editor.apply();

        //check memorised value and send a message to user
        Toast.makeText(getActivity(),prefs.getString(SAVED_COLLECTION_KEY,"")+" is saved", Toast.LENGTH_LONG)
                .show();



        return root;




    }
}