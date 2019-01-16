package com.example.denis.assign42018;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class OrdersFragment extends Fragment {
    /**
     * Uri to track pictures taken with camera
     */
    Uri mPhotoURI;
    /**
     * Spinner for 'Delivery/Collection in' field
     */
    Spinner mSpinner;
    /**
     * Customer name variable
     */
    EditText mCustomerName;
    /**
     * Optional text variable that can be included in email
     */
    EditText meditOptional;
    /**
     * Static variable for request image code for intent results retrieval
     */
    static final int REQUEST_IMAGE_CAPTURE = 1;
    /**
     * Static variable for request take photo code for intent results retrieval
     */
    static final int REQUEST_TAKE_PHOTO = 2;
    /**
     * string variable set to 'Assign4' for logcat use
     */
    private static final String TAG = "Assign4";
    /**
     * Bitmap variable for capturing images from camera
     */
    private Bitmap mImageBitmap;
    /**
     * string variable for file path for photo taken by camera
     */
    String mCurrentPhotoPath;


    /**
     * On creation of this fragment save the instance state,
     * setup the fragment resource <i>fragment_orders</i> and populate the screen.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_orders,container,false);

        meditOptional = (EditText) root.findViewById(R.id.editOptional);
        meditOptional.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditOptional.setRawInputType(InputType.TYPE_CLASS_TEXT);

        //initialise spinner using the integer array
        mSpinner = (Spinner) root.findViewById(R.id.spinner);
        mCustomerName = (EditText) root.findViewById(R.id.editCustomer);

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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "my_tshirt_image_" + timeStamp + ".jpg";
                Log.i(TAG, "imagefile");
                File file = new File(Environment.getExternalStorageDirectory(), imageFileName);
                // Save a file: path for use with later
                mCurrentPhotoPath = file.getAbsolutePath();
                // use FileProvider here rather than Uri.fromFile
                mPhotoURI = FileProvider.getUriForFile(getActivity(),getString(R.string.file_provider_authority),file);
                Log.i(TAG, mPhotoURI.toString());
                Log.i(TAG, getString(R.string.file_provider_authority));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO );
                //incase of caching if it comes from the activity stack, just a precaution
                // intent.removeExtra(MediaStore.EXTRA_OUTPUT);
            }
        });


        return root;
     }





    /**
     * Retrieve the picture taken for the T-shirt, display the picture on screen and let the user know success via toast
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

            // get the external file here but I cant get access to the thing!!
            //imageView.setImageResource(R.drawable.mad_penguins);
       }
    }









    /**
     * Returns the Email Body Message.<br>
     * Email body message is created used prescription related data inputed from user
     * @return Email Body Message
     */
    private String createOrderSummary()
    {
        String orderMessage = getString(R.string.customer_name) + " " + mCustomerName.getText().toString();
        orderMessage += "\n" + "\n" + getString(R.string.order_message_1);
        String optionalInstructions = meditOptional.getText().toString();

        orderMessage += "\n" + getString(R.string.order_message_collect) + ((CharSequence) mSpinner.getSelectedItem()).toString() + " days";
        orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString();

        // add simple use of optionalInstruction for Assignment 3
        orderMessage += "\n" + "\n" + "Optional Instrutions: " + optionalInstructions;

        return orderMessage;
        //update screen
    }












    /**
     * Open Email application an prepopulate with data from OrderActivity screen & picture retrieved
     */

    public void sendEmail(View v)
    {
        //check that Name is not empty, and ask do they want to continue
        String customerName = mCustomerName.getText().toString();
        if (customerName.matches(""))
        {
            Toast.makeText(getActivity(), getString(R.string.customer_name_blank), Toast.LENGTH_SHORT).show();
            // we can also use a dialog
            //AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //builder.setTitle("Notification!").setMessage("Customer Name not set.").setPositiveButton("OK", null).show();
            //
        } else
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.to_email)});
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
            intent.putExtra(Intent.EXTRA_STREAM, mPhotoURI);
            intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
            if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            {
                startActivity(intent);
            }
        }
    }

}