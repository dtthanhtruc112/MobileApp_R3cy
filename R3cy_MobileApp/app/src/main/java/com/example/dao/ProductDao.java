package com.example.dao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.r3cy_mobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    R3cyDB   dbHelper;;

    public ProductDao(R3cyDB dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Phương thức để lấy dữ liệu sản phẩm từ cơ sở dữ liệu cho giỏ hàng của khách hàng đang đăng nhập
    public List<CartItem> getCartItemsForCustomer(int customerId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " +
                    "p." + R3cyDB.PRODUCT_ID + ", " +
                    "p." + R3cyDB.PRODUCT_NAME + ", " +
                    "p." + R3cyDB.CATEGORY + ", " +
                    "p." + R3cyDB.PRODUCT_PRICE + ", " +
                    "c." + R3cyDB.CART_QUANTITY + ", " +
                    "p." + R3cyDB.PRODUCT_THUMB +
                    " FROM " + R3cyDB.TBl_PRODUCT + " p" +
                    " INNER JOIN " + R3cyDB.TBl_CART + " c" +
                    " ON p." + R3cyDB.PRODUCT_ID + " = c." + R3cyDB.CART_PRODUCT_ID +
                    " WHERE c." + R3cyDB.CART_CUSTOMER_ID + " = " + customerId;





            Log.d("SQL_QUERY", query); // Log câu truy vấn SQL

            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexProductId = cursor.getColumnIndex(R3cyDB.PRODUCT_ID);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexProductPrice = cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE);
                    int columnIndexCartQuantity = cursor.getColumnIndex(R3cyDB.CART_QUANTITY);
                    int columnIndexProductThumb = cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB);

                    int productId = cursor.getInt(columnIndexProductId);
                    String productName = cursor.getString(columnIndexProductName);
                    String category = cursor.getString(columnIndexCategory);
                    double productPrice = cursor.getDouble(columnIndexProductPrice);
                    int cartQuantity = cursor.getInt(columnIndexCartQuantity);
                    byte[] productThumb = cursor.getBlob(columnIndexProductThumb);

                    // Tạo đối tượng CartItem từ dữ liệu lấy được từ Cursor và thêm vào danh sách cartItems
                    CartItem cartItem = new CartItem(productId, productName, category, productPrice, cartQuantity, productThumb);
                    cartItems.add(cartItem);
                } while (cursor.moveToNext());
            } else {
                Log.d("DATABASE", "No data retrieved."); // Log nếu không có dữ liệu được trả về
            }
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error retrieving data: " + e.getMessage()); // Log lỗi nếu có bất kỳ ngoại lệ nào xảy ra
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return cartItems;
    }




    // Các phương thức khác để thực hiện các hoạt động tương tác với cơ sở dữ liệu
}
