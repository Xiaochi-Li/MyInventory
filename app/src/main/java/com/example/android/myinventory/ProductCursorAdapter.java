package com.example.android.myinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.android.myinventory.Data.ProductContract.ProductEntry;

import static android.R.attr.name;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry.COLUMN_PRODUCT_NAME;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry.COLUMN_PRODUCT_PIRCE;
import static com.example.android.myinventory.Data.ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY;

/**
 * Created by lixiaochi on 15/3/17.
 */

public class ProductCursorAdapter extends CursorAdapter {
    public  ProductCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView productName = (TextView) view.findViewById(R.id.list_name);
        TextView productQuantity = (TextView) view.findViewById(R.id.list_quantity);
        TextView productPrice = (TextView) view.findViewById(R.id.list_price);
        Button addProduct =(Button) view.findViewById(R.id.add_product);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
        final Integer price[] = {cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PIRCE))};
        Integer quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY));



        productName.setText(name);
        productQuantity.setText(quantity.toString());

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price[0] = price[0] -1;
                ContentValues values = new ContentValues();
                values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, price[0]);

            }
        });

        productPrice.setText(price[0].toString());



    }
}
