package com.example.android.myinventory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myinventory.Data.ProductContract.ProductEntry;

/**
 * Created by lixiaochi on 1/3/17.
 */

public class EditorActivity extends AppCompatActivity{

    /**
     * Identifier for the product data loader
     */
    private static final int EXISTING_PRODUCT_LOADER = 1;

    private TextView mNameEditor;
    private TextView mQuantityEditor;
    private TextView mPriceEditor;
    private TextView mSupplierEditor;
    private TextView mEmailEditor;

    /**
     * Boolean flag that keeps track of whether the product has been edited (true) or not (false)
     */
    private boolean mProductHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditor = (EditText) findViewById(R.id.edit_product_name);
        mQuantityEditor = (EditText) findViewById(R.id.edit_product_quantity);
        mPriceEditor = (EditText) findViewById(R.id.edit_product_price);
        mSupplierEditor= (EditText) findViewById(R.id.edit_supplier_name);
        mEmailEditor = (EditText) findViewById(R.id.edit_supplier_email);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mNameEditor.setOnTouchListener(mTouchListener);
        mQuantityEditor.setOnTouchListener(mTouchListener);
        mPriceEditor.setOnTouchListener(mTouchListener);
        mSupplierEditor.setOnTouchListener(mTouchListener);
        mEmailEditor.setOnTouchListener(mTouchListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //respond to a click on the "Save" menu option
            case R.id.action_save:
                //Save pet to database
                saveProduct();
                fileList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Get user input from editor and save product into database.
     */
    private void saveProduct(){
        //Read from input fields
        //Use trim to eliminate leading or trailing white space
        String nameString = mNameEditor.getText().toString().trim();
        String priceString = mPriceEditor.getText().toString().trim();
        String quantityString = mQuantityEditor.getText().toString().trim();
        String supplierString = mSupplierEditor.getText().toString().trim();
        String supplierEmailString = mEmailEditor.getText().toString().trim();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues newProductValues = new ContentValues();
        newProductValues.put(ProductEntry.COLUMN_PRODUCT_NAME,nameString);
        // If the price is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int price = 0;
        if (!TextUtils.isEmpty(priceString)) {
            price = Integer.parseInt(priceString);
        }
        newProductValues.put(ProductEntry.COLUMN_PRODUCT_PIRCE, price);
        // If the quantity is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(priceString);
        }
        newProductValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        newProductValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER,supplierString);
        newProductValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,supplierEmailString);

        // returning the content URI for the new pet.
        Uri newProductUri = getContentResolver().insert(ProductEntry.CONTENT_URI, newProductValues);
        if (newProductUri == null){
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "Insert fail",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "New product inserted",
                    Toast.LENGTH_SHORT).show();
        }

    }


}
