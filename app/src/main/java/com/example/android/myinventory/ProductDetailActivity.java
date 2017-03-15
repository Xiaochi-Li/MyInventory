package com.example.android.myinventory;

import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lixiaochi on 15/3/17.
 */

public class ProductDetailActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    /**
     * Content URI for the existing pet (null if it's a new pet)
     */
    private Uri mCurrentPetUri;

    private static final int EXISTING_PRODUCT_LOADER = 0;


    private ImageView productPicture;
    private TextView productName;
    private TextView productPrice;
    private TextView productSupplier;
    private Button productOrderMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_details);
        setTitle("Product Details");

        getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);

        productPicture = (ImageView) findViewById(R.id.detail_image);
        productName = (TextView) findViewById(R.id.detail_name);
        productPrice = (TextView) findViewById(R.id.detail_price);
        productSupplier = (TextView) findViewById(R.id.detail_supplier);
        productOrderMore = (Button) findViewById(R.id.order_from_suppliers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
