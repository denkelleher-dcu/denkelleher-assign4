package com.example.denis.assign42018;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class OrdersFragment extends Fragment {
    // sharedPreferences variables
    public static final String SAVED_COLLECTION_KEY = "SAVED_COLLECTION_KEY";
    private String mCollectionText;
    public static final String SAVED_PRODUCT_KEY = "SAVED_PRODUCT_KEY";
    private String mProductText;




    // Uri to track pictures taken with camera
     Uri mPhotoURI;

    // File to name pictures taken with camera
    File mphotoFile;

    //Spinner for 'Delivery/Collection in' field
     Spinner mSpinner;

     //Customer name variable
     EditText mCustomerName;

     //Optional text variable that can be included in email
     EditText meditOptional;

     //Static variable for request image code for intent results retrieval
     static final int REQUEST_IMAGE_CAPTURE = 1;

     //Static variable for request take photo code for intent results retrieval
    static final int REQUEST_TAKE_PHOTO = 2;

    //string variable set to 'Assign4' for logcat use
     private static final String TAG = "Assign4";

     //Bitmap variable for capturing images from camera
    private Bitmap mImageBitmap;

    //string variable for file path for photo taken by camera
    String mCurrentPhotoPath;


    /**
     * On creation of this fragment save the instance state,
     * setup the fragment resource <i>fragment_orders</i> and populate the screen.
     * @return the fragment_orders layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_orders,container,false);

        // setup shared preferences called pref
        final SharedPreferences prefs = this.getActivity().getPreferences(MODE_PRIVATE);

        meditOptional = (EditText) root.findViewById(R.id.editOptional);
        meditOptional.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditOptional.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mCustomerName = (EditText) root.findViewById(R.id.editCustomer);

        //initialise spinner using the integer array
        mSpinner = (Spinner) root.findViewById(R.id.spinner);



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ui_time_entries, R.layout.spinner_days);
        mSpinner.setAdapter(adapter);


        // When the camera icon is pressed, take a picture.
        // capture camera click icon
        ImageView cameraIcon = (ImageView) root.findViewById(R.id.imageView);
        // set a listener
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Launches the Camera to take a picture for the T-shirt<br>
             * Creates a unique file name for the picture to be taken based on the date/time
             * and assigns the file name to the mPhotoURI and sets the return code to 'REQUEST_TAKE_PHOTO' static variable
             */
            public void onClick(View v) {
                //declare the intent.
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //check for camera
                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                {
                    // camera present so
                    //call the method for photo file
                    mphotoFile = createTempFile();
                    // check all ok with photo file creation
                    if (mphotoFile!= null){
                        // file in place so go for Uri.
                        mPhotoURI = FileProvider.getUriForFile(getActivity(),getString(R.string.file_provider_authority),mphotoFile);
                        // save the file location for later use in populating back the file to screen
                        mCurrentPhotoPath = mphotoFile.getAbsolutePath();
                        //check the file location in logcat
                        Log.i(TAG, mCurrentPhotoPath.toString());
                        // take the photo
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO );
                        //incase of caching if it comes from the activity stack, just a precaution
                        // intent.removeExtra(MediaStore.EXTRA_OUTPUT);
                    }else{
                        //toaster alert to let the user know the image is missing
                        Toast toast = Toast.makeText(getActivity(), "There was a problem saving please retry", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else{
                    //no camera present, inform the user.
                    Toast toast = Toast.makeText(getActivity(), "you have no camera", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        // clear the Collection location
        Button clearCollectButton = (Button)root.findViewById(R.id.clear_collect);
        // set a listener
        clearCollectButton.setOnClickListener(new Button.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //clear the collection location
                  // saveData
                  SharedPreferences.Editor editor = prefs.edit();
                  editor.putString(SAVED_COLLECTION_KEY, "");
                  editor.apply();
                  Toast toast = Toast.makeText(getActivity(), "Collection Location Cleared From Memory", Toast.LENGTH_LONG);
                  toast.show();

              }
          });

        //Clear the Product selected
        Button clearProductButton = (Button)root.findViewById(R.id.clear_product);
        // set a listener
        clearProductButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear the collection location
                // saveData
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(SAVED_PRODUCT_KEY, "");
                editor.apply();
                Toast toast = Toast.makeText(getActivity(), "Product Cleared From Memory", Toast.LENGTH_LONG);
                toast.show();

            }
        });








        // When the Send Email button is pressed, take prepopulate the Email.
        // link too the button
        Button button = (Button)root.findViewById(R.id.button);
        // set a listener
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            /**
             * Launches the Email application and pre-populate the order details<br>
             *
             */
            public void onClick(View v) {
                //recall the product saved
                mProductText = prefs.getString(SAVED_PRODUCT_KEY, "");
                // recall the Collection location
                mCollectionText = prefs.getString(SAVED_COLLECTION_KEY, "");


                //check that Name is not empty,
                String customerName = mCustomerName.getText().toString();
                if (customerName.matches("")) {
                    //set a dialog box to show user error
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.notification_title).setMessage(R.string.no_customer).setPositiveButton("OK", null).show();

                } else {
                    //check for a product selected
                    if (mProductText.matches("")) {
                        //set a dialog box to show user error
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.notification_title).setMessage(R.string.no_product_set).setPositiveButton("OK", null).show();
                    } else {
                        //check if collection set and if not prompt for delivery address
                        String deliveryAddress = meditOptional.getText().toString();
                        if (mCollectionText.matches("") & deliveryAddress.matches("")) {
                            //set a dialog box to show user error
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.notification_title).setMessage(R.string.no_address_set).setPositiveButton("OK", null).show();
                        } else {
                            // check for both delivery and collection selected
                            if (!mCollectionText.matches("") & !deliveryAddress.matches("")) {
                                //set a dialog box to show user error
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle(R.string.notification_title).setMessage(R.string.both_address_set).setPositiveButton("OK", null).show();
                            } else {


                                // all ok so populate email

                                // test toast message
                                //Toast toast = Toast.makeText(getActivity(), "Product: " + mProductText + "\nCollection: " + mCollectionText + "\n" + createOrderSummary(), Toast.LENGTH_LONG);
                                //toast.show();
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("*/*");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.to_email)});
                                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
                                intent.putExtra(Intent.EXTRA_STREAM, mPhotoURI);
                                intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
                                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                    startActivity(intent);
                                }

                            }
                        }
                    }
                }
            }
        });

        return root;
     }


     /**
      * Retrieve the picture taken for the T-shirt, display the picture on screen and let the user know success via toast
      *@param requestCode
      *@param resultCode
      *@param data
      * @return toast and image
      */
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
         //also can give user a message that everything went ok
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
            //let user know that image saved
            CharSequence text = getString(R.string.image_confirm);


            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getActivity(), text, duration);
            toast.show();

            //link to the imageView
            ImageView imageView = getActivity().findViewById(R.id.imageView) ;
            //link to imageView Text
            TextView imageText = getActivity().findViewById(R.id.imageText);

            //get the image from the path and set it as the imageView
            Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            imageView.setImageBitmap(myBitmap);
            // set the image text to 'photo_taken_text' from strings
            imageText.setText(R.string.photo_taken_text);
            imageText.setTextAppearance(R.style.SmallestTextStyle);
       }
    }



    /**
     * Creates a temporary file
     * @return Temp file and path for image storage once taken by camera intent
     */
    private File createTempFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new
                Date());
        String imageFileName = "My_Image_" + timeStamp + "_";
        //we should get a general reference to externalstorage for images.
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //declare an image file
        File myImage = null;
        //try catch, ensure it doesn't crash if the file fails to be taken
        try
        {
            //make an empty file
            myImage = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e)
        {
            String error = String.valueOf(e);
            Log.e(TAG, error);
            //toaster alert to let the user know there's an issue
            Toast toast = Toast.makeText(getActivity(), "Please try retaking your photo!",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        return myImage;
    }

    /**
     * Returns the Email Body Message.<br>
     * Email body message is created used prescription related data inputed from user
     * @return Email Body Message
     */
    private String createOrderSummary()
    {
        // order introduction
        String orderMessage = getString(R.string.order_intro);
        //line breaks followed by
        //std order text
        orderMessage += "\n" + "\n" + getString(R.string.order_message_1)
            + "\n" + getString(R.string.order_message_2)+" " + mProductText + "\n";


        if (mCollectionText.matches("")) {
            //delivery required..
            String optionalInstructions = getString(R.string.order_message_deliver);
            orderMessage += "\n" + optionalInstructions + " " + meditOptional.getText().toString()  ;
        } else
        {
            // collection required
            String optionalInstructions = getString(R.string.order_message_collect);
            orderMessage += "\n" + optionalInstructions + " " + ((CharSequence) mSpinner.getSelectedItem()).toString() + " " + getString(R.string.collect_units)
                    + "\n" + getString(R.string.collection_location) + " " + mCollectionText;
        }
        orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString();
        return orderMessage;

    }














}