package com.example.dao;

import static com.example.databases.R3cyDB.CART_CUSTOMER_ID;
import static com.example.databases.R3cyDB.CART_PRODUCT_ID;
import static com.example.databases.R3cyDB.CART_QUANTITY;
import static com.example.databases.R3cyDB.CUSTOMER_ID;
import static com.example.databases.R3cyDB.INVENTORY;
import static com.example.databases.R3cyDB.MEMBERSHIP_SCORE;
import static com.example.databases.R3cyDB.ORDER_LINE_ORDER_ID;
import static com.example.databases.R3cyDB.ORDER_LINE_PRODUCT_ID;
import static com.example.databases.R3cyDB.ORDER_SALE_PRICE;
import static com.example.databases.R3cyDB.PRODUCT_ID;
import static com.example.databases.R3cyDB.QUANTITY;
import static com.example.databases.R3cyDB.SOLD_QUANTITY;
import static com.example.databases.R3cyDB.TBL_CUSTOMER;
import static com.example.databases.R3cyDB.TBl_CART;
import static com.example.databases.R3cyDB.TBl_ORDER_LINE;
import static com.example.databases.R3cyDB.TBl_PRODUCT;

import android.annotation.SuppressLint;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                    "p." + PRODUCT_ID + ", " +
                    "p." + R3cyDB.PRODUCT_NAME + ", " +
                    "p." + R3cyDB.CATEGORY + ", " +
                    "p." + R3cyDB.SALE_PRICE + ", " +
                    "c." + R3cyDB.CART_QUANTITY + ", " +
                    "p." + R3cyDB.PRODUCT_THUMB +
                    " FROM " + TBl_PRODUCT + " p" +
                    " INNER JOIN " + TBl_CART + " c" +
                    " ON p." + PRODUCT_ID + " = c." + R3cyDB.CART_PRODUCT_ID +
                    " WHERE c." + R3cyDB.CART_CUSTOMER_ID + " = " + customerId;

            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexLineId = cursor.getColumnIndex(R3cyDB.CART_LINE_ID);
                    int columnIndexProductId = cursor.getColumnIndex(PRODUCT_ID);
                    int columnIndexProductName = cursor.getColumnIndex(R3cyDB.PRODUCT_NAME);
                    int columnIndexCategory = cursor.getColumnIndex(R3cyDB.CATEGORY);
                    int columnIndexProductPrice = cursor.getColumnIndex(R3cyDB.SALE_PRICE);
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

    public boolean insertOrUpdateCartItem(int customerId, int productId, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra xem hàng đã tồn tại trong giỏ hàng hay chưa
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBl_CART + " WHERE " + CART_CUSTOMER_ID + " = ? AND " + CART_PRODUCT_ID + " = ?", new String[]{String.valueOf(customerId), String.valueOf(productId)});
        if (cursor != null && cursor.getCount() > 0) {
            // Hàng đã tồn tại, cập nhật số lượng
            cursor.moveToFirst();
            int existingQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(CART_QUANTITY));
            int newQuantity = existingQuantity + quantity;

            ContentValues updateValues = new ContentValues();
            updateValues.put(CART_QUANTITY, newQuantity);

            try {
                int rowsAffected = db.update(TBl_CART, updateValues, CART_CUSTOMER_ID + " = ? AND " + CART_PRODUCT_ID + " = ?", new String[]{String.valueOf(customerId), String.valueOf(productId)});
                if (rowsAffected > 0) {
                    Log.d("ProductDao", "Item quantity updated successfully");
                    return true;
                } else {
                    Log.e("ProductDao", "Failed to update item quantity");
                    return false;
                }
            } catch (SQLException e) {
                Log.e("ProductDao", "Error updating item quantity: " + e.getMessage());
                return false;
            } finally {
                cursor.close();
                db.close();
            }
        } else {
            // Hàng chưa tồn tại, thêm hàng mới vào giỏ hàng
            ContentValues values = new ContentValues();
            values.put(CART_CUSTOMER_ID, customerId);
            values.put(CART_PRODUCT_ID, productId);
            values.put(CART_QUANTITY, quantity);

            try {
                long newRowId = db.insert(TBl_CART, null, values);
                if (newRowId != -1) {
                    Log.d("ProductDao", "Item added to cart successfully with ID: " + newRowId);
                    return true;
                } else {
                    Log.e("ProductDao", "Failed to add item to cart");
                    return false;
                }
            } catch (SQLException e) {
                Log.e("ProductDao", "Error adding item to cart: " + e.getMessage());
                return false;
            } finally {
                db.close();
            }
        }
    }







    // Phương thức để cập nhật số lượng sản phẩm trong giỏ hàng
    public boolean updateQuantity(int lineId, int newQuantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(R3cyDB.CART_QUANTITY, newQuantity);

        String whereClause = R3cyDB.CART_LINE_ID + " = ?";
        String[] whereArgs = { String.valueOf(lineId) };

        try {
            int rowsUpdated = db.update(TBl_CART, values, whereClause, whereArgs);
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

    // Phương thức để xóa một lineid trong Cart
    public boolean deleteCartItem(int lineId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int rowsDeleted = db.delete(TBl_CART, R3cyDB.CART_LINE_ID + "=?", new String[]{String.valueOf(lineId)});
            if (rowsDeleted > 0) {
                Log.d("ProductDao", "Item deleted successfully for lineId " + lineId);
                db.setTransactionSuccessful();
                return true;
            } else {
                Log.e("ProductDao", "Failed to delete item for lineId " + lineId);
                return false;
            }
        } catch (SQLException e) {
            Log.e("ProductDao", "Error deleting item: " + e.getMessage());
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    // Phương thức trả về ngày hiện tại dưới dạng một chuỗi
    public String getCurrentDate() {
        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Lấy ngày giờ hiện tại
        Date currentDate = new Date();
        // Chuyển đổi thành chuỗi theo định dạng đã xác định
        return dateFormat.format(currentDate);
    }
    public long createOrder(int customerId, double totalOrderValue, double shippingFee, double  couponOrder, double couponShipping, double totalAmount, int couponId, String notes, ArrayList<CartItem> cartItems, String selectedPaymentMethod, String orderStatus, int addressId ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues orderValues = new ContentValues();
        orderValues.put(R3cyDB.ORDER_CUSTOMER_ID, customerId);
        orderValues.put(R3cyDB.TOTAL_ORDER_VALUE, totalOrderValue);
        orderValues.put(R3cyDB.SHIPPING_FEE, shippingFee);
        orderValues.put(R3cyDB.COUPON_ORDER,  couponOrder);
        orderValues.put(R3cyDB.COUPON_SHIPPING, couponShipping);
        orderValues.put(R3cyDB.TOTAL_AMOUNT, totalAmount);
        orderValues.put(R3cyDB.COUPON_ID,  couponId);
        orderValues.put(R3cyDB.ORDER_NOTE, notes);
        orderValues.put(R3cyDB.ORDER_DATE, getCurrentDate());
        orderValues.put(R3cyDB.PAYMENT_METHOD, selectedPaymentMethod);
        orderValues.put(R3cyDB.ORDER_STATUS, orderStatus);
        orderValues.put(R3cyDB.ADDRESS_ID, addressId);


        long orderId = db.insert(R3cyDB.TBl_ORDER, null, orderValues);
        if (orderId == -1) {
            return -1;
        }

        for (CartItem item : cartItems) {
            ContentValues orderLineValues = new ContentValues();
            orderLineValues.put(ORDER_LINE_ORDER_ID, orderId);
            orderLineValues.put(ORDER_LINE_PRODUCT_ID, item.getProductId());
            orderLineValues.put(QUANTITY, item.getProductQuantity());
            orderLineValues.put(ORDER_SALE_PRICE, item.getProductPrice());

            long orderLineId = db.insert(TBl_ORDER_LINE, null, orderLineValues);
            if (orderLineId == -1) {
                return -1;
            }
        }

        db.close();
        return orderId;
    }

    @SuppressLint("Range")
    public boolean updateProductQuantity(int productId, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Lấy thông tin sản phẩm từ cơ sở dữ liệu
        Cursor cursor = db.query(TBl_PRODUCT, null, R3cyDB.PRODUCT_ID + "=?",
                new String[]{String.valueOf(productId)}, null, null, null);

        int currentInventory = 0;
        int currentSoldQuantity = 0;

        if (cursor != null && cursor.moveToFirst()) {
            currentInventory = cursor.getInt(cursor.getColumnIndex(INVENTORY));
            currentSoldQuantity = cursor.getInt(cursor.getColumnIndex(SOLD_QUANTITY));
            cursor.close();
        }

        // Tính toán số lượng mới của tồn kho và số lượng đã bán
        int newInventory = currentInventory - quantity;
        int newSoldQuantity = currentSoldQuantity + quantity;

        ContentValues values = new ContentValues();
        values.put(INVENTORY, newInventory);
        values.put(SOLD_QUANTITY, newSoldQuantity);

        // Cập nhật thông tin sản phẩm vào cơ sở dữ liệu
        int rowsAffected = db.update(TBl_PRODUCT, values, R3cyDB.PRODUCT_ID + "=?", new String[]{String.valueOf(productId)});
        db.close();

        // Trả về true nếu cập nhật thành công, ngược lại trả về false
        return rowsAffected > 0;
    }


    public void accumulateMembershipScore(int customerId, double orderValue) {
        int scoreToAdd = (int) (orderValue / 3000);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int currentScore = getMembershipScore(customerId);
        int newScore = currentScore + scoreToAdd;

        ContentValues values = new ContentValues();
        values.put(R3cyDB.MEMBERSHIP_SCORE, newScore);

        db.update(R3cyDB.TBL_CUSTOMER, values, R3cyDB.CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});
        String newCustomerType = calculateCustomerType(newScore);
        values.put(R3cyDB.CUSTOMER_TYPE, newCustomerType);

        db.update(R3cyDB.TBL_CUSTOMER, values, R3cyDB.CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});
        db.close();
    }
    public String calculateCustomerType(int membershipScore) {
        if (membershipScore >= 0 && membershipScore < 1000) {
            return "Thường";
        } else if (membershipScore >= 1000 && membershipScore < 5000) {
            return "Đồng";
        } else if (membershipScore >= 5000 && membershipScore < 10000) {
            return "Bạc";
        } else if (membershipScore >= 10000 && membershipScore < 20000) {
            return "Vàng";
        } else {
            return "Kim Cương";
        }
    }

    @SuppressLint("Range")
    public int getMembershipScore(int customerId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int membershipScore = 0;

        Cursor cursor = db.query(TBL_CUSTOMER, new String[]{MEMBERSHIP_SCORE}, CUSTOMER_ID + " = ?",
                new String[]{String.valueOf(customerId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            membershipScore = cursor.getInt(cursor.getColumnIndex(MEMBERSHIP_SCORE));
            cursor.close();
        }

        return membershipScore;
    }


//    public boolean createOrderLine(int orderId, int productId, double salePrice, int quantity) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ORDER_LINE_ORDER_ID, orderId);
//        values.put(ORDER_LINE_PRODUCT_ID, productId);
//        values.put(ORDER_SALE_PRICE, salePrice);
//        values.put(QUANTITY, quantity);
//
//        try {
//            long newRowId = db.insert(TBl_ORDER_LINE, null, values);
//            if (newRowId != -1) {
//                Log.d("ProductDao", "Order line created successfully with ID: " + newRowId);
//                return true;
//            } else {
//                Log.e("ProductDao", "Failed to create order line");
//                return false;
//            }
//        } catch (SQLException e) {
//            Log.e("ProductDao", "Error creating order line: " + e.getMessage());
//            return false;
//        } finally {
//            db.endTransaction();
//            db.close();
//        }
//    }
////    public boolean createOrder(int customerId, String paymentMethod, double totalOrderValue, int addressId, List<CartItem> cartItems) {
////        SQLiteDatabase db = dbHelper.getWritableDatabase();
////        ContentValues values = new ContentValues();
////        values.put(R3cyDB.ORDER_CUSTOMER_ID, customerId);
////        values.put(R3cyDB.ORDER_DATE, getCurrentDate());
////        values.put(R3cyDB.PAYMENT_METHOD, paymentMethod);
////        values.put(R3cyDB.TOTAL_ORDER_VALUE, totalOrderValue);
////        values.put(R3cyDB.ADDRESS_ID, addressId);
////        values.put(R3cyDB.ORDER_STATUS, "Chờ xử lí"); // Đặt giá trị mặc định cho ORDER_STATUS
////
////        try {
////            db.beginTransaction();
////
////            long newRowId = db.insert(R3cyDB.TBl_ORDER, null, values);
////            if (newRowId != -1) {
////                Log.d("ProductDao", "Order created successfully with ID: " + newRowId);
////
////                // Lưu thông tin chi tiết đơn hàng xuống bảng OrderLine
////                for (CartItem item : cartItems) {
////                    createOrderLine((int) newRowId, item.getProductId(), item.getProductPrice(), item.getProductQuantity());
////                }
////
////                db.setTransactionSuccessful();
////                return true;
////            } else {
////                Log.e("ProductDao", "Failed to create order");
////                return false;
////            }
////        } catch (SQLException e) {
////            Log.e("ProductDao", "Error creating order: " + e.getMessage());
////            return false;
////        } finally {
////            db.endTransaction();
////            db.close();
////        }
////    }
//public boolean createOrder(int customerId, String paymentMethod, double totalOrderValue, int addressId, List<CartItem> cartItems) {
//    SQLiteDatabase db = dbHelper.getWritableDatabase();
//    ContentValues orderValues = new ContentValues();
//    orderValues.put(R3cyDB.ORDER_CUSTOMER_ID, customerId);
//    orderValues.put(R3cyDB.ORDER_DATE, getCurrentDate());
//    orderValues.put(R3cyDB.PAYMENT_METHOD, paymentMethod);
//    orderValues.put(R3cyDB.TOTAL_ORDER_VALUE, totalOrderValue);
//    orderValues.put(R3cyDB.ADDRESS_ID, addressId);
//    orderValues.put(R3cyDB.ORDER_STATUS, "Chờ xử lí");
//
//    try {
//        db.beginTransaction();
//
//        long newRowId = db.insert(R3cyDB.TBl_ORDER, null, orderValues);
//        if (newRowId != -1) {
//            for (CartItem item : cartItems) {
//                createOrderLine( (int) newRowId, item.getProductId(), item.getProductPrice(), item.getProductQuantity());
//            }
//            db.setTransactionSuccessful();
//
//            if (updateInventoryAndDeleteCart(cartItems)) {
//                db.setTransactionSuccessful();
//                for (CartItem item : cartItems) {
//                    accumulateMembershipScore(customerId, item.getProductPrice() * item.getProductQuantity());
//                }
//            }
//
//            db.setTransactionSuccessful();
//            return true;
//        } else {
//            Log.e("ProductDao", "Failed to create order");
//            return false;
//        }
//    } catch (SQLException e) {
//        Log.e("ProductDao", "Error creating order: " + e.getMessage());
//        return false;
//    } finally {
//        db.endTransaction();
//        db.close();
//    }
//}
//
//
//    // Thêm phương thức cập nhật tồn kho sản phẩm và xóa lineid khỏi bảng cart
//    public boolean updateInventoryAndDeleteCart(List<CartItem> cartItems) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        try {
//            db.beginTransaction();
//            for (CartItem item : cartItems) {
//                // Cập nhật tồn kho sản phẩm
//                updateProductInventory(db, item.getProductId(), item.getProductQuantity());
//                // Xóa mục từ giỏ hàng
//                deleteCartItem(item.getLineId());
//            }
//            db.setTransactionSuccessful();
//            return true;
//        } catch (SQLException e) {
//            Log.e("ProductDao", "Error updating inventory and deleting cart items: " + e.getMessage());
//            return false;
//        } finally {
//            db.endTransaction();
//            db.close();
//        }
//    }
//
//    // Phương thức cập nhật tồn kho sản phẩm
//    public void updateProductInventory(SQLiteDatabase db, int productId, int quantity) {
//        // Xây dựng câu lệnh SQL để cập nhật tồn kho và số lượng đã bán của sản phẩm
//        String sql = "UPDATE " + TBl_PRODUCT +
//                " SET " + INVENTORY + " = " + INVENTORY + " - ?, " +
//                SOLD_QUANTITY + " = " + SOLD_QUANTITY + " + ? " +
//                " WHERE " + PRODUCT_ID + " = ?";
//
//        SQLiteStatement statement = db.compileStatement(sql);
//        statement.bindLong(1, quantity);  // Cập nhật INVENTORY - quantity
//        statement.bindLong(2, quantity);  // Cập nhật SOLD_QUANTITY + quantity
//        statement.bindLong(3, productId); // Điều kiện WHERE cho sản phẩm có ID là productId
//
//        try {
//            int rowsAffected = statement.executeUpdateDelete();
//            if (rowsAffected > 0) {
//                Log.d("ProductDao", "Inventory updated successfully for product ID " + productId);
//            } else {
//                Log.e("ProductDao", "Failed to update inventory for product ID " + productId);
//            }
//        } catch (SQLException e) {
//            Log.e("ProductDao", "Error updating inventory: " + e.getMessage());
//        } finally {
//
//            db.endTransaction();
//            statement.close();
//        }
//    }
//    public void accumulateMembershipScore(int customerId, double orderValue) {
//        // Quy đổi 3,000 đồng thành 1 điểm
//        int scoreToAdd = (int) (orderValue / 3000);
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Lấy số điểm tích lũy hiện tại của khách hàng
//        int currentScore = getMembershipScore(customerId);
//
//        // Tính toán số điểm mới
//        int newScore = currentScore + scoreToAdd;
//
//        ContentValues values = new ContentValues();
//        values.put(MEMBERSHIP_SCORE, newScore);
//
//        // Cập nhật số điểm mới vào cơ sở dữ liệu
//        int rowsAffected = db.update(TBL_CUSTOMER, values, CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});
//        if (rowsAffected > 0) {
//            Log.d("CustomerDao", "Membership score updated successfully for customer ID " + customerId);
//        } else {
//            Log.e("CustomerDao", "Failed to update membership score for customer ID " + customerId);
//        }
//
//        // Đóng kết nối đến cơ sở dữ liệu
//        db.endTransaction();
//        db.close();
//    }
//
//    @SuppressLint("Range")
//    public int getMembershipScore(int customerId) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        int membershipScore = 0;
//
//        Cursor cursor = db.query(TBL_CUSTOMER, new String[]{MEMBERSHIP_SCORE}, CUSTOMER_ID + " = ?",
//                new String[]{String.valueOf(customerId)}, null, null, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            membershipScore = cursor.getInt(cursor.getColumnIndex(MEMBERSHIP_SCORE));
//            cursor.close();
//        }
//
//        return membershipScore;
//    }
//
//    public void closeDatabase() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        if (db != null && db.isOpen()) {
//            db.close();
//        }
//    }


    // Thêm phương thức quy đổi điểm cho khách hàng








}
