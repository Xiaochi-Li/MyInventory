package com.example.android.myinventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.myinventory.Data.ProductContract;
import com.example.android.myinventory.Data.ProductContract.ProductEntry;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.numeric;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry.COLUMN_PRODUCT_NAME;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry.COLUMN_PRODUCT_PIRCE;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry._ID;

/**
 * Created by lixiaochi on 15/3/17.
 */

public class ProductCursorAdapter extends CursorAdapter {
    TextView productName ;
    TextView productQuantity ;
    TextView productPrice ;
    Button addProduct;

    public  ProductCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final Context ctext = context;
        final Cursor cur =cursor;

        productName = (TextView) view.findViewById(R.id.list_name);
        productQuantity = (TextView) view.findViewById(R.id.list_quantity);
        productPrice = (TextView) view.findViewById(R.id.list_price);
        addProduct =(Button) view.findViewById(R.id.add_product);

        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
        Integer price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PIRCE));
        final Integer quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY));



        productName.setText(name);
        productQuantity.setText(quantity.toString());

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(ctext,  id, quantity);
            }
        });

        productPrice.setText(price.toString());
    }


    //The following post will solve the problem.
    //https://discussions.udacity.com/t/list-view-item-click-listener-and-button-click-listener-not-associated-correctly/191401
    // https://discussions.udacity.com/t/sale-button-in-inventory-list-view/191393/2
    private void updateQuantity(Context context,int id, int quantity){
        Uri currentProductUri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, id);

        if(quantity>0){
            quantity--;
        }

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        int rowsAffected = context.getContentResolver().update(currentProductUri, values,
                null, null);
        if(rowsAffected !=0){
            productQuantity.setText(Integer.toString(quantity));
        }
    }
}
