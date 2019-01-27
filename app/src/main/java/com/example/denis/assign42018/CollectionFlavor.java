package com.example.denis.assign42018;


/**
 * {@link CollectionFlavor} represents a single Online Shop Collection Point.<br>
 * Each object has 3 properties: Location, Address, and Phone Number.<br>
 * This class is based on DCU OpenICT SDA Assignment 3 content
 */
public class CollectionFlavor {
    //Represents the Collection Location (eg 'Mahon', 'Bishopstown' etc)
    private String mCollectionLocation;

    //Represents the Collection Address (eg 'No 4, Main Street')
    private String mCollectionAddress;

    //Represents the Collection Phone Number (eg '087-11122233')
    private String mCollectionPhone;

    /**
     * Create a new CollectionFlavor object.
     *
     * @param vLocation is the name of the Collection Location ((eg 'Mahon', 'Bishopstown' etc))
     * @param vAddress is the corresponding Collection Address (eg 'No 4, Main Street')
     * @param vPhoneNum is the corresponding Collection Phone Number (eg '087-11122233')<br>
     *
     * Define the instance when creating an CollectionFlavour with vLocation, vAddress and vPhoneNum
     * */
    public CollectionFlavor(String vLocation, String vAddress, String vPhoneNum){
        mCollectionLocation = vLocation;
        mCollectionAddress = vAddress;
        mCollectionPhone = vPhoneNum;
    }

    /**
     * Get method for the name of the Product name
     * @return String representing the name of Product name (shirt, shorts etc)
     */
    public String getCollectionLocation() {
        return mCollectionLocation;
    }

    /**
     * Get method for the price of the Product
     * @return String (for display purposes only in this case) representing the name of Product price (€10.99 etc)
     */
    public String getCollectionAddress() {
        return mCollectionAddress;
    }

    /**
     * Get the image resource ID
     * @return reference ID for the icon that corresponds to the Product
     */
    public String getCollectionPhone() {
        return mCollectionPhone;
    }


}
