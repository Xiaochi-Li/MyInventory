package com.example.android.myinventory;

import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myinventory.Data.ProductContract;

import static android.R.attr.data;

/**
 * Created by lixiaochi on 15/3/17.
 */

public class ProductDetailActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    /**
     * Content URI for the existing pet (null if it's a new pet)
     */
    private Uri mCurrentProductUri;

    private static final int EXISTING_PRODUCT_LOADER = 0;


    private ImageView productPicture;
    private TextView productName;
    private TextView productPrice;
    private TextView productSupplier;
    private Button productOrderMore;
    private EditText productQuantity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_details);
        setTitle("Product Details");
        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);

        productPicture = (ImageView) findViewById(R.id.detail_image);
        productName = (TextView) findViewById(R.id.detail_name);
        productPrice = (TextView) findViewById(R.id.detail_price);
        productSupplier = (TextView) findViewById(R.id.detail_supplier);
        productOrderMore = (Button) findViewById(R.id.order_from_suppliers);
        productQuantity = (EditText) findViewById(R.id.detail_quantity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case R.id.action_receive:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(ProductDetailActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRODUCT_IMAGE,
                ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER,
                ProductContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,
                ProductContract.ProductEntry.COLUMN_PRODUCT_PIRCE,
        };
        return new CursorLoader(this,
                mCurrentProductUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()){
            int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex =cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PIRCE);
            int imageColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_IMAGE);
            int supplierColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER);
            int supplierEmailColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
            int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);

            String productNameString = cursor.getString(nameColumnIndex);
            int productPriceInt = cursor.getInt(priceColumnIndex);
            byte[] productImageByte = cursor.getBlob(imageColumnIndex);
            String productSupplierString = cursor.getString(supplierColumnIndex);
            String productSupplierEmailString = cursor.getString(supplierEmailColumnIndex);
            int productQuantityInt = cursor.getInt(quantityColumnIndex);

            if(productImageByte !=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(productImageByte, 0, productImageByte.length);
           productPicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, productPicture.getWidth(),
                    productPicture.getHeight(), false));}
            productName.setText(productNameString);
            productPrice.setText(Integer.toString(productPriceInt));
            productSupplier.setText(productSupplierString);
            productQuantity.setText(Integer.toString(productQuantityInt));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Want to delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteProduct();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentProductUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentProductUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "delete fail",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, "delete success",
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
