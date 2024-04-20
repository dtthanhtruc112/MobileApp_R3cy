package com.example.dao;

import static com.example.databases.R3cyDB.CATEGORY;
import static com.example.databases.R3cyDB.PRODUCT_ID;
import static com.example.databases.R3cyDB.TBl_PRODUCT;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.databases.R3cyDB;
import com.example.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao2 {
    R3cyDB dbHelper;

    public ProductDao2(R3cyDB dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Product> getProductsByCategory(String category ) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {

            String query = "SELECT * FROM " + R3cyDB.TBl_PRODUCT + " WHERE " + CATEGORY + "=?";
            cursor = db.rawQuery(query, new String[]{CATEGORY});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexProductId = cursor.getColumnIndex(R3cyDB.PRODUCT_ID);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexProductPrice = cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE);
                    int columnIndexProductDescription = cursor.getColumnIndex(R3cyDB.PRODUCT_DESCRIPTION);
                    int columnIndexProductThumb = cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB);
                    int columnIndexHot = cursor.getColumnIndex(R3cyDB.HOT);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexInventory = cursor.getColumnIndex(R3cyDB.INVENTORY);
                    int columnIndexProductRate = cursor.getColumnIndex(R3cyDB.PRODUCT_RATE);
                    int columnIndexSalePrice = cursor.getColumnIndex(R3cyDB.SALE_PRICE);
                    int columnIndexSoldQuantity = cursor.getColumnIndex(R3cyDB.SOLD_QUANTITY);
                    int columnIndexCreatedDate = cursor.getColumnIndex(R3cyDB.CREATED_DATE);
                    int columnIndexStatus = cursor.getColumnIndex(R3cyDB.STATUS);
                    int columnIndexImg1 = cursor.getColumnIndex(R3cyDB.PRODUCT_IMG1);
                    int columnIndexImg2 = cursor.getColumnIndex(R3cyDB.PRODUCT_IMG2);
                    int columnIndexImg3 = cursor.getColumnIndex(R3cyDB.PRODUCT_IMG3);

                    int productID = cursor.getInt(columnIndexProductId);
                    String productName = cursor.getString(columnIndexProductName);
                    double productPrice = cursor.getDouble(columnIndexProductPrice);
                    String productDescription = cursor.getString(columnIndexProductDescription);
                    byte[] productThumb = cursor.getBlob(columnIndexProductThumb);
                    int hot = cursor.getInt(columnIndexHot);
                    String productCategory = cursor.getString(columnIndexCategory);
                    int inventory = cursor.getInt(columnIndexInventory);
                    double productRate = cursor.getDouble(columnIndexProductRate);
                    double salePrice = cursor.getDouble(columnIndexSalePrice);
                    int soldQuantity = cursor.getInt(columnIndexSoldQuantity);
                    String createdDate = cursor.getString(columnIndexCreatedDate);
                    byte[] img1 = cursor.getBlob(columnIndexImg1);
                    byte[] img2 = cursor.getBlob(columnIndexImg2);
                    byte[] img3 = cursor.getBlob(columnIndexImg3);
                    int status = cursor.getInt(columnIndexStatus);

                    Product product = new Product(productID, productName, productPrice, productDescription, productThumb, hot, productCategory, inventory, productRate, salePrice, soldQuantity, createdDate, status);
                    products.add(product);
                } while (cursor.moveToNext());
            } else {
                Log.d("R3cyDB", "No products found for category: " + category);
            }
        } catch (SQLiteException e) {
            Log.e("R3cyDB", "Error getting products by category: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return products;
    }
}