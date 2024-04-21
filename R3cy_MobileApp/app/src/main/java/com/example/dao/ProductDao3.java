package com.example.dao;

import static com.example.databases.R3cyDB.CATEGORY;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.databases.R3cyDB;
import com.example.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao3 {
    R3cyDB dbHelper;

    public ProductDao3(R3cyDB dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM " + R3cyDB.TBl_PRODUCT;
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexProductId = cursor.getColumnIndex(R3cyDB.PRODUCT_ID);
                    int columnIndexProductThumb = cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexSalePrice = cursor.getColumnIndex(R3cyDB.SALE_PRICE);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexProductDescription = cursor.getColumnIndex(R3cyDB.PRODUCT_DESCRIPTION);
                    int columnIndexProductRate = cursor.getColumnIndex(R3cyDB.PRODUCT_RATE);


                    int productID = cursor.getInt(columnIndexProductId);
                    byte[] productThumb = cursor.getBlob(columnIndexProductThumb);
                    String productName = cursor.getString(columnIndexProductName);
                    double salePrice = cursor.getDouble(columnIndexSalePrice);
                    String productCategory = cursor.getString(columnIndexCategory);
                    String productDescription = cursor.getString(columnIndexProductDescription);
                    double productRate = cursor.getDouble(columnIndexProductRate);


                    Product product = new Product(productID, productThumb, productName, salePrice, productCategory, productDescription, productRate);
                    products.add(product);

                } while (cursor.moveToNext());
            } else {
                Log.d("R3cyDB", "No products found in database");
            }
        } catch (SQLiteException e) {
            Log.e("R3cyDB", "Error getting all products: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return products;
    }}
