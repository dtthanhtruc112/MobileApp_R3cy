package com.example.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.databases.R3cyDB;
import com.example.models.CartItem;
import com.example.r3cy_mobileapp.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
   R3cyDB   dbHelper;

    public ProductDao(R3cyDB dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<CartItem> getCartItemsForCustomer(int customerId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " +
                    "c." + R3cyDB.CART_LINE_ID + ", " +
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

            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexLineId = cursor.getColumnIndex(R3cyDB.CART_LINE_ID);
                    int columnIndexProductId = cursor.getColumnIndex(R3cyDB.PRODUCT_ID);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexProductPrice = cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE);
                    int columnIndexCartQuantity = cursor.getColumnIndex(R3cyDB.CART_QUANTITY);
                    int columnIndexProductThumb = cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB);

                    int lineId = cursor.getInt(columnIndexLineId);
                    int productId = cursor.getInt(columnIndexProductId);
                    String productName = cursor.getString(columnIndexProductName);
                    String category = cursor.getString(columnIndexCategory);
                    double productPrice = cursor.getDouble(columnIndexProductPrice);
                    int cartQuantity = cursor.getInt(columnIndexCartQuantity);
                    byte[] productThumb = cursor.getBlob(columnIndexProductThumb);

                    // Tạo đối tượng CartItem từ dữ liệu lấy được từ Cursor và thêm vào danh sách cartItems
                    CartItem cartItem = new CartItem(productId, productName, category, productPrice, cartQuantity, productThumb, false, lineId );
                    cartItems.add(cartItem);
                } while (cursor.moveToNext());
            } else {
                Log.d("DATABASE", "No data retrieved.");
            }
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error retrieving data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return cartItems;
    }





    // Các phương thức khác để thực hiện các hoạt động tương tác với cơ sở dữ liệu
    // Phương thức để cập nhật số lượng sản phẩm trong cơ sở dữ liệu cho giỏ hàng của khách hàng
    // Phương thức để cập nhật số lượng sản phẩm trong giỏ hàng
    public boolean updateQuantity(int lineId, int newQuantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R3cyDB.CART_QUANTITY, newQuantity);

        String whereClause = R3cyDB.CART_LINE_ID + " = ?";
        String[] whereArgs = { String.valueOf(lineId) };

        try {
            int rowsUpdated = db.update(R3cyDB.TBl_CART, values, whereClause, whereArgs);
            if (rowsUpdated > 0) {
                Log.d("ProductDao", "Quantity updated successfully for lineId " + lineId);
                return true;
            } else {
                Log.e("ProductDao", "Failed to update quantity for lineId " + lineId);
                return false;
            }
        } catch (SQLException e) {
            Log.e("ProductDao", "Error updating quantity: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    // Phương thức để xóa một mục từ cơ sở dữ liệu
    // Phương thức để xóa một mục từ cơ sở dữ liệu
    // Phương thức để xóa một mục từ cơ sở dữ liệu
    public boolean deleteCartItem(int lineId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            int rowsDeleted = db.delete(R3cyDB.TBl_CART, R3cyDB.CART_LINE_ID + "=?", new String[]{String.valueOf(lineId)});
            if (rowsDeleted > 0) {
                Log.d("ProductDao", "Item deleted successfully for lineId " + lineId);
                return true;
            } else {
                Log.e("ProductDao", "Failed to delete item for lineId " + lineId);
                return false;
            }
        } catch (SQLException e) {
            Log.e("ProductDao", "Error deleting item: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }






}
