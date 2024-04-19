package com.example.dao;

import static com.example.databases.R3cyDB.PRODUCT_ID;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao2 {
    R3cyDB dbHelper;

    public ProductDao2(R3cyDB dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String query = "SELECT " +
                    "p." + R3cyDB.PRODUCT_ID + ", " +
                    "p." + R3cyDB.PRODUCT_NAME + ", " +
                    "p." + R3cyDB.PRODUCT_PRICE + ", " +
                    "p." + R3cyDB.PRODUCT_DESCRIPTION + ", " +
                    "p." + R3cyDB.PRODUCT_THUMB + ", " +
                    "p." + R3cyDB.HOT + ", " +
                    "p." + R3cyDB.CATEGORY + ", " +
                    "p." + R3cyDB.INVENTORY + ", " +
                    "p." + R3cyDB.PRODUCT_RATE + ", " +
                    "p." + R3cyDB.SALE_PRICE + ", " +
                    "p." + R3cyDB.SOLD_QUANTITY + ", " +
                    "p." + R3cyDB.CREATED_DATE + ", " +
                    "p." + R3cyDB.STATUS + ", " +
                    "p." + R3cyDB.PRODUCT_IMG1 + ", " +
                    "p." + R3cyDB.PRODUCT_IMG2 + ", " +
                    "p." + R3cyDB.PRODUCT_IMG3 +
                    " FROM " + R3cyDB.TBl_PRODUCT + " p";

            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndexProductId = cursor.getColumnIndex(PRODUCT_ID);
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

                if (columnIndexProductId != -1 && columnIndexProductName != -1 && columnIndexCategory != -1
                        && columnIndexSalePrice != -1 && columnIndexProductThumb != -1 && columnIndexProductDescription != -1) {

                    while (cursor.moveToNext()) {


                        int ProductID = cursor.getInt(columnIndexProductId);
                        String ProductName = cursor.getString(columnIndexProductName);
                        double ProductPrice = cursor.getDouble(columnIndexProductPrice);
                        String ProductDescription = cursor.getString(columnIndexProductDescription);
                        byte[] ProductThumb = cursor.getBlob(columnIndexProductThumb);
                        int Hot = cursor.getInt(columnIndexHot);
                        String Category = cursor.getString(columnIndexCategory);
                        int Inventory = cursor.getInt(columnIndexInventory);
                        double ProductRate = cursor.getDouble(columnIndexProductRate);
                        double SalePrice = cursor.getDouble(columnIndexSalePrice);
                        int SoldQuantity = cursor.getInt(columnIndexSoldQuantity);
                        String CreatedDate = cursor.getString(columnIndexCreatedDate);
                        byte[] Img1 = cursor.getBlob(columnIndexImg1);
                        byte[] Img2 = cursor.getBlob(columnIndexImg2);
                        byte[] Img3 = cursor.getBlob(columnIndexImg3);
                        int Status = cursor.getInt(columnIndexStatus);


                        // Tạo đối tượng Product từ dữ liệu lấy được từ Cursor và thêm vào danh sách products
                        Product product = new Product(ProductID, ProductName, ProductPrice, ProductDescription, ProductThumb, Hot, Category, Inventory, ProductRate, SalePrice, SoldQuantity, CreatedDate, Status, Img1, Img2, Img3);
                        products.add(product);
                    }
                }else {
                    Log.e("R3cyDB", "One or more column indexes are invalid.");
                    Log.e("R3cyDB", "Invalid columns: " +
                            "columnIndexProductId: " + columnIndexProductId + ", " +
                            "columnIndexProductName: " + columnIndexProductName + ", " +
                            "columnIndexCategory: " + columnIndexCategory + ", " +
                            "columnIndexSalePrice: " + columnIndexSalePrice + ", " +
                            "columnIndexProductThumb: " + columnIndexProductThumb + ", " +
                            "columnIndexProductDescription: " + columnIndexProductDescription);
                }
            } else {
                Log.d("R3cyDB", "No products found.");
            }
        } catch (SQLiteException e) {
            Log.e("R3cyDB", "Error getting products: " + e.getMessage());
        } finally {
            // Đóng Cursor và SQLiteDatabase nếu chúng không null
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return products;
    }
}