package com.example.denis.assign42018;


/**
 * {@link ProductFlavor} represents a single Online Shop Product.<br>
 * Each object has 3 properties: Name, Price, and Image Resource ID.<br>
 * This class is based on DCU OpenICT SDA Assignment 3 content
 */
public class ProductFlavor {
    //Represents the Product Name (eg Shirt, Tie, etc)
    private String mProductName;

    //Represents the Product Price
    private String mProductPrice;

    //Represents Drawable resource ID for the icon to be displayed
    private int mImageResourceId;

    /**
     * Create a new ProductFlavor object.
     *
     * @param vName is the name of the Product (e.g. Shirt)
     * @param vPrice is the corresponding Product Price (e.g. €10.99)
     * @param imageResourceId is drawable reference ID that corresponds to the Product<br>
     *
     * Define the instance when creating an ProductFlavour with vName, vPrice and imageResourceId
     * */
    public ProductFlavor(String vName, String vPrice, int imageResourceId){
        mProductName = vName;
        mProductPrice = vPrice;
        mImageResourceId = imageResourceId;
    }

    /**
     * Get method for the name of the Product name
     * @return String representing the name of Product name (shirt, shorts etc)
     */
    public String getProductName() {
        return mProductName;
    }

    /**
     * Get method for the price of the Product
     * @return String (for display purposes only in this case) representing the name of Product price (€10.99 etc)
     */
    public String getProductPrice() {
        return mProductPrice;
    }

    /**
     * Get the image resource ID
     * @return reference ID for the icon that corresponds to the Product
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }


}
