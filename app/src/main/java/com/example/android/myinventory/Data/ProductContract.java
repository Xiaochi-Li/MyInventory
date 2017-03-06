package com.example.android.myinventory.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import static android.R.attr.type;

/**
 * Created by lixiaochi on 1/3/17.
 */

public class ProductContract {
    /*URLs Contract*/
    //Db scheme
    public static final String URI_SCHEME = "content://";
    //ContentAuthority
    public static final String CONTENT_AUTHORITY = "com.example.android.products";
    //PATH_TableName
    public static final String PATH_PRODUCTS = "products";
    //Uri Object of Db Scheme and Content Authority
    public static final Uri BASE_CONTENT_URI = Uri.parse(URI_SCHEME + CONTENT_AUTHORITY);

    /*Inner class that defines the table contents of the pet table */
    public static final class ProductEntry implements BaseColumns {
        //Uri Object Content Uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        // Table name
        public static final String TABLE_NAME = "products";
        // column id
        public static final String _ID = BaseColumns._ID;
        // column name
        public static final String COLUMN_PRODUCT_NAME = "name";
        // column price
        public static final String COLUMN_PRODUCT_PIRCE = "price";
        // column quantity
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        // column supplier
        public static final String COLUMN_PRODUCT_SUPPLIER = "supplier";
        // column supplier's email
        public static final String COLUMN_PRODUCT_SUPPLIER_EMAIL = "email";
        // column product image
        public static final String COLUMN_PRODUCT_IMAGE = "image";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
    }
}
