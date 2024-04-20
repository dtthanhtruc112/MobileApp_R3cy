package com.example.databases;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import static java.sql.DriverManager.getConnection;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.models.Address;
import com.example.models.CartItem;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.R;

import java.io.ByteArrayOutputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class R3cyDB extends SQLiteOpenHelper {
    Context context;
    // Tên cơ sở dữ liệu

    public static final String DATABASE_NAME = "r3cy_database.db";
    // Phiên bản cơ sở dữ liệu
    public static final int DATABASE_VERSION = 1;

    // Tên các bảng trong cơ sở dữ liệu
    public static final String TBL_CUSTOMER = "CUSTOMER";
    public static final String TBl_PRODUCT = "PRODUCT";
    public static final String TBl_FEEDBACK = "FEEDBACK";
    public static final String TBl_DISCUSS = "DISCUSS";
    public static final String TBl_ORDER = "ORDER_TABLE";
    public static final String TBl_ORDER_LINE = "ORDERLINE";
    public static final String TBl_ADDRESS = "ADDRESS";
    public static final String TBl_CART = "CART";
    public static final String TBl_COUPON = "COUPON";
    public static final String TBl_VOUCHER_SHIP = "VOUCHERSHIP";
    public static final String TBL_WISHLIST = "WISHLIST";
    public static final String TBL_BLOG = "BLOG";
    public static final String TBL_CUSTOMPRODUCT = "CUSTOMPRODUCT";

    // Các cột của bảng Customer
    public static final String CUSTOMER_ID = "CustomerID";
    public static final String FULLNAME = "Fullname";
    public static final String USERNAME = "Username";
    public static final String PHONE = "PhoneNumber";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String GENDER = "Gender";
    public static final String BIRTHDAY = "Birthday";
    public static final String CUSTOMER_THUMB = "CustomerThumb";
    public static final String CUSTOMER_TYPE = "CustomerType";
    public static final String MEMBERSHIP_SCORE = "MembershipScore";


    // Các cột của bảng Product
    public static final String PRODUCT_ID = "ProductID";
    public static final String PRODUCT_NAME = "ProductName";
    public static final String PRODUCT_PRICE = "ProductPrice";
    public static final String PRODUCT_DESCRIPTION = "ProductDescription";
    public static final String PRODUCT_THUMB = "ProductThumb";
    public static final String HOT = "Hot";
    public static final String CATEGORY = "Category";
    public static final String INVENTORY = "Inventory";
    public static final String PRODUCT_RATE = "ProductRate";
    public static final String SALE_PRICE = "SalePrice";
    public static final String SOLD_QUANTITY = "SoldQuantity";
    public static final String CREATED_DATE = "CreatedDate";
    public static final String UPDATED_DATE = "UpdatedDate";
    public static final String STATUS = "Status";
    public static final String PRODUCT_IMG1 = "img1";
    public static final String PRODUCT_IMG2 = "img2";
    public static final String PRODUCT_IMG3 = "img3";

    // Các cột của bảng Discuss
    public static final String DISCUSS_ID = "DiscussID";
    public static final String DISCUSS_PRODUCT_ID = "ProductID"; // Đổi tên cột [ProductID] thành ProductID
    public static final String DISCUSS_CUS_EMAIL = "CusEmail";
    public static final String DISCUSS_CONTENT = "DiscussContent";
    public static final String RESPOND_CONTENT = "RespondContent";
    public static final String DISCUSS_STATUS = "Status";


    // Các cột của bảng Feedback
    public static final String FEEDBACK_ID = "FeedbackID";
    public static final String FEEDBACK_PRODUCT_ID = "ProductID";
    public static final String FEEDBACK_ORDER_ID = "OrderID";
    public static final String FEEDBACK_CUSTOMER_ID = "CustomerID";
    public static final String FEEDBACK_CONTENT = "FeedbackContent";
    public static final String FEEDBACK_RATING = "Rating";
    public static final String FEEDBACK_DATE = "FeedbackDate";


    // Các cột của bảng OrderLine
    public static final String ORDER_LINE_ID = "LineID";
    public static final String ORDER_LINE_ORDER_ID = "OrderID";
    public static final String ORDER_LINE_PRODUCT_ID = "ProductID";
    public static final String ORDER_SALE_PRICE = "SalePrice";
    public static final String QUANTITY = "Quantity";

    // Các cột của bảng Order
    public static final String ORDER_ID = "OrderID";
    public static final String ORDER_CUSTOMER_ID = "CustomerID";
    public static final String ORDER_DATE = "OrderDate";
    public static final String PAYMENT_METHOD = "PaymentMethod";
    public static final String PAYMENT_ID = "PaymentID";
    public static final String COUPON_ID = "CouponId";
    public static final String TOTAL_ORDER_VALUE = "TotalOrderValue";
    public static final String ORDER_STATUS = "Orderstatus"; // Đổi tên cột Orderstatus thành Orderstatus để tránh trùng với từ khóa SQL
    public static final String ORDER_NOTE = "Note";
    public static final String DELIVERY_DATE = "DeliveryDate";
    public static final String DISCOUNT = "Discount";
    public static final String SHIPPING_FEE = "Shippingfee";
    public static final String TOTAL_AMOUNT = "TotalAmount";
    public static final String PAYMENT_STATUS = "Paymentstatus";
    public static final String ADDRESS_ID = "AddressID";
    // Các cột của bảng Address
    public static final String ADDRESS_CUSTOMER_ID = "CustomerID";
    public static final String RECEIVER_NAME = "ReceiverName";
    public static final String RECEIVER_PHONE = "ReceiverPhone";
    public static final String PROVINCE = "Province";
    public static final String DISTRICT = "District";
    public static final String WARD = "Ward";
    public static final String ADDRESS_DETAILS = "AddressDetails";
    public static final String IS_DEFAULT = "IsDefault";
    public static final String ADDRESS_TYPE = "AddressType";

    // Các cột của bảng Cart
    public static final String CART_LINE_ID = "LineID";
    public static final String CART_CUSTOMER_ID = "CustomerID";
    public static final String CART_PRODUCT_ID = "ProductID";
    public static final String CART_QUANTITY = "Quantity";

    // Các cột của bảng Coupon
    public static final String COUPON_CODE = "CouponCode";
    public static final String COUPON_TITLE = "CouponTitle";
    public static final String SCORE_MIN = "ScoreMin";
    public static final String COUPON_TYPE = "CouponType";
    public static final String COUPON_CATEGORY = "CouponCategory";
    public static final String VALID_DATE = "ValidDate";
    public static final String EXPIRE_DATE = "ExpireDate";
    public static final String MIN_ORDER_VALUE = "MinOrderValue";
    public static final String MAXIMUM_DISCOUNT = "MaximumDiscount";
    public static final String COUPON_VALUE = "CouponValue";
    public static final String MAXIMUM_USERS = "MaximumUsers";
    public static final String CUSTOMER_IDS  = "Customer_IDs";

    // Các cột của bảng VoucherShip
    public static final String VOUCHER_SHIP_ID = "VoucherShipID";
    public static final String VOUCHER_SHIP_CODE = "VoucherShipCode";
    public static final String VOUCHER_SHIP_TITLE = "VoucherShipTitle";
    public static final String DISCOUNT_TYPE = "DiscountType";
    public static final String VOUCHER_SHIP_VALUE = "VouchershipValue";

    // Các cột của bảng Blog
    public static final String BLOG_ID = "BlogID";
    public static final String BLOG_TITLE = "BlogTitle";
    public static final String BLOG_THUMB = "BlogThumb";
    public static final String BLOG_CREATE_DATE = "BlogCreateDate";
    public static final String BLOG_AUTHOR = "BlogAuthor";
    public static final String BLOG_CONTENT = "BlogContent";


    // Các cột của bảng CUSTOMPRODUCT
    public static final String CUSTOMPRODUCT_ID = "CustomID";
    public static final String CUSTOMPRODUCT_NAME = "CusName";
    public static final String CUSTOMPRODUCT_PHONE = "CusPhone";
    public static final String CUSTOMPRODUCT_EMAIL = "CusEmail";
    public static final String CUSTOMPRODUCT_TITLE = "CustomTitle";
    public static final String CUSTOMPRODUCT_DESFILE = "DesFile";

//Tạo hàm getProductByCategory
//    public List<Product> getProductsByCategory(String category) {
//        String sql = "SELECT * FROM PRODUCT WHERE category = ?";
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, new String[]{category});
//        List<Product> products = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            // ... (process cursor data and create Product objects)
//            products.add(product);
//        }
//        cursor.close();
//        db.close();
//        return products;
//    }


//    Tạo constructor


    public R3cyDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực thi các câu lệnh tạo bảng khi tạo cơ sở dữ liệu
        db.execSQL(CREATE_TBL_PRODUCT);
        db.execSQL(CREATE_TBL_FEEDBACK);
        db.execSQL(CREATE_TBL_DISCUSS);
        db.execSQL(CREATE_TBL_ORDER);
        db.execSQL(CREATE_TBL_ORDER_LINE);
        db.execSQL(CREATE_TBL_ADDRESS);
        db.execSQL(CREATE_TBL_CART);
        db.execSQL(CREATE_TBL_COUPON);
        db.execSQL(CREATE_TBL_WISHLIST);
        db.execSQL(CREATE_TBL_BLOG);
        db.execSQL(CREATE_TBL_CUSTOMPRODUCT);
        db.execSQL(CREATE_TBL_CUSTOMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TBl_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_FEEDBACK);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_DISCUSS);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_ORDER_LINE);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_COUPON);
        db.execSQL("DROP TABLE IF EXISTS " + TBl_VOUCHER_SHIP);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_BLOG);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CUSTOMPRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CUSTOMER);

        // Tạo lại bảng mới
        onCreate(db);
    }

    // Câu lệnh tạo bảng PRODUCT
    private static final String CREATE_TBL_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TBl_PRODUCT + "(" +
            PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PRODUCT_NAME + " TEXT NOT NULL," +
            PRODUCT_PRICE + " REAL NOT NULL," +
            PRODUCT_DESCRIPTION + " TEXT NOT NULL," +
            PRODUCT_THUMB + " BLOB," +
            HOT + " INTEGER DEFAULT 0," +
            CATEGORY + " TEXT NOT NULL," +
            INVENTORY + " INTEGER DEFAULT 60," +
            PRODUCT_RATE + " REAL," +
            SALE_PRICE + " REAL NOT NULL," +
            SOLD_QUANTITY + " INTEGER," +
            CREATED_DATE + " DATE DEFAULT ('2024-04-03')," +
            STATUS + " INTEGER DEFAULT 1," +
            "PRIMARY KEY (" + PRODUCT_ID + ")" +
            ")";


    // Câu lệnh tạo bảng FEEDBACK
    private static final String CREATE_TBL_FEEDBACK = "CREATE TABLE IF NOT EXISTS " + TBl_FEEDBACK + "(" +
            FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FEEDBACK_PRODUCT_ID + " INTEGER REFERENCES " + TBl_PRODUCT + "(" + PRODUCT_ID + ")," +
            FEEDBACK_ORDER_ID + " INTEGER REFERENCES " + TBl_ORDER + "(" + ORDER_ID + ")," +
            FEEDBACK_CUSTOMER_ID + " INTEGER REFERENCES " + TBL_CUSTOMER + "(" + CUSTOMER_ID + ")," +
            FEEDBACK_CONTENT + " TEXT," +
            FEEDBACK_RATING + " REAL DEFAULT 5.0," +
            FEEDBACK_DATE + " DATE" +
            ")";

    // Câu lệnh tạo bảng Discuss
    private static final String CREATE_TBL_DISCUSS = "CREATE TABLE IF NOT EXISTS " + TBl_DISCUSS + "(" +
            DISCUSS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DISCUSS_PRODUCT_ID + " INTEGER REFERENCES " + TBl_PRODUCT + "(" + PRODUCT_ID + ")," +
            DISCUSS_CUS_EMAIL + " TEXT," +
            DISCUSS_CONTENT + " TEXT," +
            RESPOND_CONTENT + " TEXT," +
            DISCUSS_STATUS + " INTEGER DEFAULT 1" +
            ")";

    // Câu lệnh tạo bảng Order
    private static final String CREATE_TBL_ORDER = "CREATE TABLE IF NOT EXISTS " + TBl_ORDER + "(" +
            ORDER_ID + " INTEGER NOT NULL UNIQUE," +
            ORDER_CUSTOMER_ID + " INTEGER NOT NULL REFERENCES " + TBL_CUSTOMER + "(" + CUSTOMER_ID + ")," +
            ORDER_DATE + " TEXT NOT NULL," +
            PAYMENT_METHOD + " TEXT NOT NULL," +
            PAYMENT_ID + " INTEGER," +
            COUPON_ID + " TEXT REFERENCES " + TBl_COUPON + "(" + COUPON_ID + ")," +
            TOTAL_ORDER_VALUE + " REAL NOT NULL," +
            ORDER_STATUS + " TEXT NOT NULL," +
            ORDER_NOTE + " TEXT," +
            DELIVERY_DATE + " DATE," +
            DISCOUNT + " REAL," +
            SHIPPING_FEE + " REAL," +
            TOTAL_AMOUNT + " REAL," +
            PAYMENT_STATUS + " INTEGER DEFAULT 0," +
            ADDRESS_ID + " INTEGER REFERENCES " + TBl_ADDRESS + "(" + ADDRESS_ID + ")," +
            "PRIMARY KEY (" + ORDER_ID + ")" +
            ")";

    // Câu lệnh tạo bảng OrderLine
    private static final String CREATE_TBL_ORDER_LINE = "CREATE TABLE IF NOT EXISTS " + TBl_ORDER_LINE + "(" +
            ORDER_LINE_ID + " INTEGER NOT NULL UNIQUE," +
            ORDER_LINE_ORDER_ID + " INTEGER NOT NULL REFERENCES " + TBl_ORDER + "(" + ORDER_ID + ")," +
            ORDER_LINE_PRODUCT_ID + " INTEGER NOT NULL REFERENCES " + TBl_PRODUCT + "(" + PRODUCT_ID + ")," +
            ORDER_SALE_PRICE + " REAL NOT NULL," +
            QUANTITY + " INTEGER NOT NULL," +
            "PRIMARY KEY (" + ORDER_LINE_ID + ")" +
            ")";

    // Câu lệnh tạo bảng Address
    private static final String CREATE_TBL_ADDRESS = "CREATE TABLE IF NOT EXISTS " + TBl_ADDRESS + "(" +
            ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ADDRESS_CUSTOMER_ID + " INTEGER REFERENCES " + TBL_CUSTOMER + "(" + CUSTOMER_ID + ")," +
            RECEIVER_NAME + " TEXT," +
            RECEIVER_PHONE + " TEXT," +
            PROVINCE + " TEXT," +
            DISTRICT + " TEXT," +
            WARD + " TEXT," +
            ADDRESS_DETAILS + " TEXT," +
            IS_DEFAULT + " INTEGER DEFAULT 0," +
            ADDRESS_TYPE + " TEXT" +
            ")";
    // Câu lệnh tạo bảng Cart
    private static final String CREATE_TBL_CART = "CREATE TABLE IF NOT EXISTS " + TBl_CART + "(" +
            CART_LINE_ID + " INTEGER NOT NULL UNIQUE," +
            CART_CUSTOMER_ID + " INTEGER NOT NULL REFERENCES " + TBL_CUSTOMER + "(" + CUSTOMER_ID + ")," +
            CART_PRODUCT_ID + " INTEGER NOT NULL REFERENCES " + TBl_PRODUCT + "(" + PRODUCT_ID + ")," +
            CART_QUANTITY + " INTEGER NOT NULL," +
            "PRIMARY KEY (" + CART_LINE_ID + ")" +
            ")";

    // Câu lệnh tạo bảng Coupon
    private static final String CREATE_TBL_COUPON = "CREATE TABLE IF NOT EXISTS " + TBl_COUPON + "(" +
            COUPON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COUPON_CODE + " TEXT," +
            COUPON_TITLE + " TEXT NOT NULL," +
            SCORE_MIN + " INTEGER NOT NULL," +
            COUPON_TYPE + " TEXT NOT NULL," +
            COUPON_CATEGORY + " TEXT NOT NULL," +
            VALID_DATE + " DATE NOT NULL," +
            EXPIRE_DATE + " DATE NOT NULL," +
            MIN_ORDER_VALUE + " REAL NOT NULL," +
            MAXIMUM_DISCOUNT + " REAL NOT NULL," +
            COUPON_VALUE + " REAL NOT NULL," +
            MAXIMUM_USERS + " INTEGER DEFAULT 10," +
            CUSTOMER_IDS + " TEXT DEFAULT '[]'" + // Thêm cột CUSTOMER_IDS kiểu TEXT mặc định là một mảng JSON rỗng
            ")";

    // Câu lệnh tạo bảng Customer
    private static final String CREATE_TBL_CUSTOMER = "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMER + "(" +
            CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USERNAME + " TEXT," +
            FULLNAME + " TEXT," +
            GENDER + " TEXT, " +
            EMAIL + " TEXT NOT NULL," +
            PHONE + " TEXT," +
            PASSWORD + " TEXT NOT NULL," +
            MEMBERSHIP_SCORE + " INTEGER NOT NULL DEFAULT 0," +
            BIRTHDAY + " DATE," +
            CUSTOMER_THUMB + " BLOB," +
            CUSTOMER_TYPE + " TEXT" +
            ")";

    // Câu lệnh tạo bảng VoucherShip

    // Câu lệnh tạo bảng Wishlist
    private static final String CREATE_TBL_WISHLIST = "CREATE TABLE IF NOT EXISTS " + TBL_WISHLIST + "(" +
            "ProductID INTEGER NOT NULL," +
            "CustomerID INTEGER NOT NULL," +
            "PRIMARY KEY (CustomerID, ProductID)" +
            ")";

    // Câu lệnh tạo bảng Blog
    private static final String CREATE_TBL_BLOG = "CREATE TABLE IF NOT EXISTS " + TBL_BLOG + "(" +
            BLOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BLOG_TITLE + " TEXT," +
            BLOG_THUMB + " BLOB," +
            BLOG_CREATE_DATE + " DATE," +
            BLOG_AUTHOR + " TEXT," +
            BLOG_CONTENT + " BLOB" +
            ")";
    // Câu lệnh tạo bảng CUSTOMPRODUCT
    private static final String CREATE_TBL_CUSTOMPRODUCT = "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMPRODUCT + "(" +
            CUSTOMPRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            CUSTOMPRODUCT_NAME + " TEXT," +
            CUSTOMPRODUCT_PHONE + " TEXT," +
            CUSTOMPRODUCT_EMAIL + " TEXT," +
            CUSTOMPRODUCT_TITLE + " TEXT," +
            CUSTOMPRODUCT_DESFILE + " BLOB" +
            ")";

    //SELECT...
    public Cursor getData(String sql){
        try {
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }
        catch (Exception e){
            return null;
        }
    }

//    INSERT. UPDATE, DELETE
public boolean execSql(String sql){
    SQLiteDatabase db = getWritableDatabase();
    try{
        db.execSQL(sql);
        return true;
    }catch (Exception e){
        Log.e("Error: ", e.toString());
        return false;
    }

}
//    Ktra table có dữ liệu không

//    Kiểm tra bảng Coupon
public int numbOfRowsCoupon(){
    Cursor c = getData("SELECT * FROM " + TBl_COUPON);
    int numberOfRows = c.getCount();
    c.close();
    return numberOfRows;
}




//    Thêm dữ liệu mẫu
public void createSampleDataCart() {
    if (numbOfRowsCart() == 0) {
        execSql("INSERT INTO " + TBl_CART + " VALUES(null, 2, 2, 1)");
        execSql("INSERT INTO " + TBl_CART + " VALUES(null, 3, 3, 2)");
        execSql("INSERT INTO " + TBl_CART + " VALUES(null, 4, 4, 1)");
        execSql("INSERT INTO " + TBl_CART + " VALUES(null, 1, 4, 1)");
        execSql("INSERT INTO " + TBl_CART + " VALUES(null, 1, 5, 1)");
        execSql("INSERT INTO " + TBl_CART + " VALUES(null, 1, 6, 1)");
    }

}
    public int numbOfRowsCart(){
        Cursor c = getData("SELECT * FROM " + TBl_CART);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }
// Dữ liệu mẫu Coupon
public void createSampleDataCoupon() {
    if (numbOfRowsCoupon() == 0) {
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM10%', 'GIẢM 10% ĐƠN HÀNG CHO THÀNH VIÊN MỚI, GIẢM TỐI ĐA 30K', 100, 'percent', 'order', '2024-04-15', '2024-05-20', 100000, 30000, 0.10, 10, '[1, 2, 3]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM20%', 'GIẢM 20% ĐƠN HÀNG CHÀO MỪNG THÁNG 4, GIẢM TỐI ĐA 60K', 1000, 'percent', 'order', '2024-04-15', '2024-05-20', 200000, 60000, 0.20, 10, '[4, 5, 6]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM30%', 'GIẢM 30% ĐƠN HÀNG TRI ÂN THÀNH VIÊN BẠC, GIẢM TỐI ĐA 90K', 5000, 'percent', 'order', '2024-04-15', '2024-05-20', 300000, 90000, 0.30, 10, '[7, 8, 9]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM120K', 'GIẢM 120K ĐƠN HÀNG CHÀO MỪNG THÁNG 5', 10000, 'value', 'order', '2024-04-15', '2024-05-20', 400000, 120000, 120000, 10, '[10, 11, 12]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM150K', 'GIẢM 150K ĐƠN HÀNG TRI ÂN THÀNH VIÊN KIM CƯƠNG', 20000, 'value', 'order', '2024-04-15', '2024-05-20', 500000, 150000, 150000, 10, '[13, 14, 15]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM20%', 'GIẢM 20% PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 100000', 100, 'percent', 'ship', '2024-04-15', '2024-05-20', 100000, 30000, 0.20, 30, '[16, 17, 18]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM40%', 'GIẢM 40% PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 200000', 1000, 'percent', 'ship', '2024-04-15', '2024-05-20', 200000, 30000, 0.40, 30, '[19, 20, 21]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM60%', 'GIẢM 60% PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 300000', 5000, 'percent', 'ship', '2024-04-15', '2024-05-20', 300000, 30000, 0.6, 30, '[22, 23, 24]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM40K', 'GIẢM 40K PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 400000 ', 10000, 'value', 'ship', '2024-04-15', '2024-05-20', 400000, 40000, 400000, 30, '[25, 26, 27]')");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'FREESHIP', 'MIỄN PHÍ VẬN CHUYỂN', 20000, 'value', 'ship', '2024-04-15', '2024-05-20', 500000, 60000, 1, 30, '[28, 29, 30]')");
    }
}

    //Cập nhật coupon khi có người đổi điểm lấy quà
// Method to add a new customer_id to the existing array in customer_ids field based on coupon_id
    public void addCustomerIdToCoupon(int couponId, int newCustomerId) {
        // Thực hiện truy vấn để lấy danh sách customerIds cho couponId cụ thể từ cơ sở dữ liệu
        ArrayList<Integer> customerIds = new ArrayList<>();
        // Code truy vấn và gán vào customerIds ở đây

        // Thêm newCustomerId vào danh sách hiện có
        customerIds.add(newCustomerId);

        // Chuyển danh sách customerIds thành một chuỗi có thể lưu trữ trong cơ sở dữ liệu
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < customerIds.size(); i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(customerIds.get(i));
        }
        stringBuilder.append("]");
        String customerIdsString = stringBuilder.toString();

        // Chuẩn bị câu lệnh SQL để cập nhật dữ liệu
        String sql = "UPDATE " + TBl_COUPON +
                " SET " + CUSTOMER_IDS + " = '" + customerIdsString + "'" +
                " WHERE " + COUPON_ID + " = " + couponId;

        // Thực thi câu lệnh SQL
        execSql(sql);
    }

//   Hàm lấy mảng customerid
public ArrayList<Integer> parseCustomerIdsFromString(String customerIdsString) {
    ArrayList<Integer> customerIds = new ArrayList<>();
    if (customerIdsString != null && !customerIdsString.isEmpty()) {
        String[] ids = customerIdsString.replaceAll("\\[|\\]", "").split(",\\s*");
        for (String id : ids) {
            customerIds.add(Integer.parseInt(id.trim()));
        }
    }
    return customerIds;
}

    // Phương thức để lấy danh sách customerIds từ cơ sở dữ liệu cho couponId cụ thể
    // Phương thức để lấy danh sách customerIds từ cơ sở dữ liệu cho couponId cụ thể











    public int numbOfRowsOrder(){
        Cursor c = getData("SELECT * FROM " + TBl_ORDER);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }
    public boolean insertDataOrder(String ORDER_ID, String ORDER_CUSTOMER_ID, String ORDER_DATE, String PAYMENT_METHOD, String PAYMENT_ID, String COUPON_ID, double TOTAL_ORDER_VALUE, String ORDER_STATUS, String ORDER_NOTE, String DELIVERY_DATE, String DISCOUNT, double SHIPPING_FEE, double TOTAL_AMOUNT, String PAYMENT_STATUS, String ADDRESS_ID) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TBl_ORDER + "(" +
                ORDER_ID + ", " +
                ORDER_CUSTOMER_ID + ", " +
                ORDER_DATE + ", " +
                PAYMENT_METHOD + ", " +
                PAYMENT_ID + ", " +
                COUPON_ID + ", " +
                TOTAL_ORDER_VALUE + ", " +
                ORDER_STATUS + ", " +
                ORDER_NOTE + ", " +
                DELIVERY_DATE + ", " +
                DISCOUNT + ", " +
                SHIPPING_FEE + ", " +
                TOTAL_AMOUNT + ", " +
                PAYMENT_STATUS + ", " +
                ADDRESS_ID +
                ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);

// Chuyển đổi chuỗi ngày tháng thành đối tượng Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = sdf.parse(ORDER_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        statement.bindString(1, ORDER_ID);
        statement.bindString(2, ORDER_CUSTOMER_ID);
        statement.bindString(3, ORDER_DATE);
        statement.bindString(4, PAYMENT_METHOD);
        statement.bindString(5, PAYMENT_ID);
        statement.bindString(6, COUPON_ID);
        statement.bindDouble(7, TOTAL_ORDER_VALUE);
        statement.bindString(8, ORDER_STATUS);
        statement.bindString(9, ORDER_NOTE);
        statement.bindString(10, DELIVERY_DATE);
        statement.bindString(11, DISCOUNT);
        statement.bindDouble(12, SHIPPING_FEE);
        statement.bindDouble(13, TOTAL_AMOUNT);
        statement.bindString(14, PAYMENT_STATUS);
        statement.bindString(15, ADDRESS_ID);


        long result = statement.executeInsert();
        boolean  success = result != -1;
        Log.d("DatabaseHelper", "Insert data result: " + success);
        return success;
    }
    public void createSampleDataOrder(){
        insertDataOrder(null,null,"14-04-2024","COD", null, null, 235000, "Đang giao", "Che tên sản phẩm", "15-04-2024", "0", 35000, 200000, "Chưa thanh toán", null);
        insertDataOrder(null,null,"15-04-2003","COD", null, null, 165000, "Chờ lấy hàng", "Che tên sản phẩm", "16-04-2024", "10%", 35000, 130000, "Chưa thanh toán", null);
        insertDataOrder(null,null,"16-04-2003","COD", null, null, 185000, "Đang giao", "Che tên sản phẩm", "17-04-2024", "0", 35000, 150000, "Chưa thanh toán", null);
        insertDataOrder(null,null,"17-04-2003","COD", null, null, 175000, "Đang giao", "Che tên sản phẩm", "18-04-2024", "0", 35000, 140000, "Chưa thanh toán", null);
        insertDataOrder(null,null,"18-04-2003","COD", null, null, 215000, "Đang giao", "Che tên sản phẩm", "19-04-2024", "0", 35000, 180000, "Chưa thanh toán", null);
    }

    public int numbOfRowsOrderLine(){
        Cursor c = getData("SELECT * FROM " + TBl_ORDER_LINE);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public boolean insertDataOrderLine(String ORDER_LINE_ID, String ORDER_LINE_ORDER_ID, String ORDER_LINE_PRODUCT_ID, double ORDER_SALE_PRICE, String QUANTITY) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TBl_ORDER_LINE + "(" +
                ORDER_LINE_ID + ", " +
                ORDER_LINE_ORDER_ID + ", " +
                ORDER_LINE_PRODUCT_ID + ", " +
                ORDER_SALE_PRICE + ", " +
                QUANTITY +
                ") VALUES(?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);

//
        statement.bindString(1, ORDER_LINE_ID);
        statement.bindString(2, ORDER_LINE_ORDER_ID);
        statement.bindString(3, ORDER_LINE_PRODUCT_ID);
        statement.bindDouble(4, ORDER_SALE_PRICE);
        statement.bindString(5, QUANTITY);


        long result = statement.executeInsert();
        boolean  success = result != -1;
        Log.d("DatabaseHelper", "Insert data result: " + success);
        return success;
    }
    public void createSampleDataOrderLine(){
        insertDataOrderLine(null,null,null,160000, "4");
        insertDataOrderLine(null,null,null,150000, "2");
        insertDataOrderLine(null,null,null,140000, "3");
        insertDataOrderLine(null,null,null,150000, "1");
        insertDataOrderLine(null,null,null,170000, "2");
    }
    @SuppressLint("Range")
    public String getOrderStatus(String orderstatus) {
        SQLiteDatabase db = this.getReadableDatabase();
//        String customerId = -1; // Giá trị mặc định nếu không tìm thấy customerId
        String query = "SELECT * FROM " + TBl_ORDER + " WHERE " + ORDER_STATUS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{orderstatus});
        if (cursor != null && cursor.moveToFirst()) {
            orderstatus = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(FULLNAME));
            cursor.close();

        }

        // Đóng con trỏ và database
//        cursor.close();
//        sqLiteDatabase.close();
//

        db.close();

        return orderstatus;
    }
    public int numbOfRowsAddress(){
        Cursor c = getData("SELECT * FROM " + TBl_ADDRESS);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataAddress(){
        if (numbOfRowsAddress() == 0){
            execSql("INSERT INTO " + TBl_ADDRESS + " VALUES(null, 1, 'Lê Thị Tuyết Anh', '0911235896', 'TP HCM', 'Thủ Đức', 'Phường 2', '14 Nguyễn Tri Phương', 1, 'nhà riêng')");
            execSql("INSERT INTO " + TBl_ADDRESS + " VALUES(null, 1, 'Lê Thị Tuyết Anh', '0911235896', 'TP HCM', 'Tân Phú', 'Sơn Kỳ', '14 Tân Thắng', 0, 'nhà riêng')");
            execSql("INSERT INTO " + TBl_ADDRESS + " VALUES(null, 2, 'Đặng Thị Thanh Trúc', '0910587896', 'Huế', 'Phong Điền', 'Phường 4', '35/8 Trần Hưng Đạo', 1, 'nhà riêng')");
            execSql("INSERT INTO " + TBl_ADDRESS + " VALUES(null, 3, 'Đặng Lê Như Quỳnh', '0923535896', 'Bình Định', 'Tuy Phước', 'Phường 5', '40 Lê Duẩn', 1, 'nhà riêng')");
            execSql("INSERT INTO " + TBl_ADDRESS + " VALUES(null, 4, 'Hồ Lê Thanh Trúc', '0971237410', 'Quảng Ngãi', 'Bình Sơn', 'Phường 6', '12/246 Trần Bình Trọng', 1, 'nhà riêng')");
            execSql("INSERT INTO " + TBl_ADDRESS + " VALUES(null, 4, 'Nguyễn Thảo Nguyên', '0956335872', 'Phú Yên', 'Tuy Hoà', 'Phường 7', '79 Võ Thị Sáu', 1, 'nhà riêng')");
        }
    }

    public Customer getCustomerByEmail1(String email) {
        // Đọc cơ sở dữ liệu
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu truy vấn SQL
        String query = "SELECT * FROM " + TBL_CUSTOMER + " WHERE " + EMAIL + " = ?";

        // Thực thi câu truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{email});

        Customer customer = null;

        // Nếu có kết quả từ câu truy vấn
        if (cursor.moveToFirst()) {
            // Lấy thông tin từ cursor
            @SuppressLint("Range") int customerId = cursor.getInt(cursor.getColumnIndex(CUSTOMER_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(USERNAME));
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex(FULLNAME));
            @SuppressLint("Range") int gender = cursor.getInt(cursor.getColumnIndex(GENDER));
            @SuppressLint("Range") String emaill = cursor.getString(cursor.getColumnIndex(EMAIL));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(PHONE));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
            @SuppressLint("Range") int membershipScore = cursor.getInt(cursor.getColumnIndex(MEMBERSHIP_SCORE));
            @SuppressLint("Range") String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));
            @SuppressLint("Range") byte[] customerThumb = cursor.getBlob(cursor.getColumnIndex(CUSTOMER_THUMB));
            @SuppressLint("Range") String customerType = cursor.getString(cursor.getColumnIndex(CUSTOMER_TYPE));

// Tạo đối tượng Customer
            customer = new Customer(customerId, username, fullName, gender, emaill, phone, password, membershipScore, birthday, customerThumb, customerType);
        }

        // Đóng cursor và đóng cơ sở dữ liệu
        cursor.close();
        db.close();

        // Trả về đối tượng Customer
        return customer;
    }


    public int getLastInsertedAddressId() {
        SQLiteDatabase db = getReadableDatabase();
        int lastInsertedId = -1;
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if (cursor != null && cursor.moveToFirst()) {
            lastInsertedId = cursor.getInt(0);
            cursor.close();
        }
        return lastInsertedId;
    }
    public List<Address> getAddressesForCustomer(int customerId) {
        List<Address> addresses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String selection = ADDRESS_CUSTOMER_ID + " = ?";
            String[] selectionArgs = { String.valueOf(customerId) };
            cursor = db.query(TBl_ADDRESS, null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int columnIndexAddressId = cursor.getColumnIndex(ADDRESS_ID);
                    int addressId = cursor.getInt(columnIndexAddressId);

                    int columnIndexAddressCustomerId = cursor.getColumnIndex(ADDRESS_CUSTOMER_ID);
                    int addressCustomerId = cursor.getInt(columnIndexAddressCustomerId);

                    int columnIndexReceiverName = cursor.getColumnIndex(RECEIVER_NAME);
                    String receiverName = cursor.getString(columnIndexReceiverName);

                    int columnIndexReceiverPhone = cursor.getColumnIndex(RECEIVER_PHONE);
                    String receiverPhone = cursor.getString(columnIndexReceiverPhone);

                    int columnIndexProvince = cursor.getColumnIndex(PROVINCE);
                    String province = cursor.getString(columnIndexProvince);

                    int columnIndexDistrict = cursor.getColumnIndex(DISTRICT);
                    String district = cursor.getString(columnIndexDistrict);

                    int columnIndexWard = cursor.getColumnIndex(WARD);
                    String ward = cursor.getString(columnIndexWard);

                    int columnIndexAddressDetails = cursor.getColumnIndex(ADDRESS_DETAILS);
                    String addressDetails = cursor.getString(columnIndexAddressDetails);

                    int columnIndexIsDefault = cursor.getColumnIndex(IS_DEFAULT);
                    int isDefault = cursor.getInt(columnIndexIsDefault);

                    int columnIndexAddressType = cursor.getColumnIndex(ADDRESS_TYPE);
                    String addressType = cursor.getString(columnIndexAddressType);

                    // Tạo đối tượng Address từ dữ liệu lấy được từ Cursor
                    Address address = new Address(addressId, addressCustomerId, receiverName, receiverPhone, province, district, ward, addressDetails, isDefault, addressType, false);

                    // Thêm đối tượng Address vào danh sách addresses
                    addresses.add(address);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DATABASE_ERROR", "Error retrieving data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return addresses;
    }
    @SuppressLint("Range")
    public Address getAddressById(int addressId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Address address = null;
        Cursor cursor = null;
        try {
            // Query to get the address by its ID
            String query = "SELECT * FROM " + TBl_ADDRESS + " WHERE " + ADDRESS_ID + " = ?";

            // Execute the query with the provided addressId
            cursor = db.rawQuery(query, new String[]{String.valueOf(addressId)});

            // Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                // Extract data from the cursor and create an Address object
                address = new Address(
                        cursor.getInt(cursor.getColumnIndex(ADDRESS_ID)),
                        cursor.getInt(cursor.getColumnIndex(ADDRESS_CUSTOMER_ID)),
                        cursor.getString(cursor.getColumnIndex(RECEIVER_NAME)),
                        cursor.getString(cursor.getColumnIndex(RECEIVER_PHONE)),
                        cursor.getString(cursor.getColumnIndex(PROVINCE)),
                        cursor.getString(cursor.getColumnIndex(DISTRICT)),
                        cursor.getString(cursor.getColumnIndex(WARD)),
                        cursor.getString(cursor.getColumnIndex(ADDRESS_DETAILS)),
                        cursor.getInt(cursor.getColumnIndex(IS_DEFAULT)),
                        cursor.getString(cursor.getColumnIndex(ADDRESS_TYPE)),
                        false
                );
            }
        } catch (Exception e) {
            Log.e("Error", "Error while getting address by ID: " + e.getMessage());
        } finally {
            // Close the cursor and database connection
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return address;
    }
    public Address getDefaultAddress(int customerId) {
        // Lấy danh sách tất cả các địa chỉ của khách hàng
        List<Address> addresses = getAddressesForCustomer(customerId);

        // Tìm địa chỉ mặc định trong danh sách
        for (Address address : addresses) {
            if (address.getIsDefault() == 1) {
                return address; // Trả về địa chỉ mặc định nếu tìm thấy
            }
        }

        // Trả về null nếu không tìm thấy địa chỉ mặc định
        return null;
    }

    public Address getMaxAddress(int customerId) {
        // Lấy danh sách tất cả các địa chỉ của khách hàng
        List<Address> addresses = getAddressesForCustomer(customerId);

        // Tìm địa chỉ có addressId lớn nhất trong danh sách
        Address maxAddress = null;
        int maxAddressId = -1;
        for (Address address : addresses) {
            if (address.getAddressId() > maxAddressId) {
                maxAddressId = address.getAddressId();
                maxAddress = address;
            }
        }

        // Trả về địa chỉ có addressId lớn nhất hoặc null nếu không có địa chỉ nào
        return maxAddress;
    }















    // Kiểm tra bảng Customer
public int numbOfRowsCustomer(){
        Cursor c = getData("SELECT * FROM " + TBL_CUSTOMER);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
}

public boolean insertCustomer(String fullName, String email, String password) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(FULLNAME, fullName);
    values.put(EMAIL, email);
    values.put(PASSWORD, password);
    long result = db.insert(TBL_CUSTOMER, null, values);
    db.close();
    return result != -1;
}
public Customer getCustomerByEmail(String email) {
    // Đọc cơ sở dữ liệu
    SQLiteDatabase db = this.getReadableDatabase();

    // Câu truy vấn SQL
    String query = "SELECT * FROM " + TBL_CUSTOMER + " WHERE " + EMAIL + " = ?";

    // Thực thi câu truy vấn
    Cursor cursor = db.rawQuery(query, new String[]{email});

    Customer customer = null;

    // Nếu có kết quả từ câu truy vấn
    if (cursor.moveToFirst()) {
        // Lấy thông tin từ cursor
        @SuppressLint("Range") int customerId = cursor.getInt(cursor.getColumnIndex(CUSTOMER_ID));
        @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(USERNAME));
        @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
        // Tạo đối tượng Customer
        customer = new Customer(customerId, username, email, password);
    }

    // Đóng cursor và đóng cơ sở dữ liệu
    cursor.close();
    db.close();

    // Trả về đối tượng Customer
    return customer;
}



public boolean checkEmailExists(String email) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TBL_CUSTOMER,
            new String[]{CUSTOMER_ID},
            EMAIL + "=?",
            new String[]{email},
            null, null, null);
    int count = cursor.getCount();
    cursor.close();
    return count > 0;
}

public void createSampleDataCustomer(){
    if (numbOfRowsCustomer() == 0){
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'anhltt', 'Lê Thị Tuyết Anh', 'Nữ', 'anhltt21411@gmail.com', '0911235896', 'anhltt21411@', 25500, '01/02/2003', null, 'Kim cương')");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'trucdtt', 'Đặng Thị Thanh Trúc', 'Nữ', 'trucdtt21411@gmail.com', '0910587896', 'trucdtt21411@', 2000, '01/10/2003', null, 'Đồng')");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'quynhdln', 'Đặng Lê Như Quỳnh', 'Nữ', 'quynhdln21411@gmail.com', '0923535896', 'quynhdln21411@', 7000, '15/02/2003', null, 'Bạc')");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'truchlt', 'Hồ Lê Thanh Trúc', 'Nữ', 'truchlt21411@gmail.com', '0971237410', 'truchlt21411@', 1500, '21/08/2003', null, 'Vàng')");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'nguyennt', 'Nguyễn Thảo Nguyên', 'Nữ', 'nguyennt21411@gmail.com', '0956335872', 'nguyennt21411@', 22000, '11/12/2003', null, 'Kim Cương' )");
    }
}

// Ràng buộc Hạng tvien với Score
// Update điểm và hạng thành viên
public void updateCustomerMembership(int customerId, int newMembershipScore) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(MEMBERSHIP_SCORE, newMembershipScore);

    String newCustomerType = calculateCustomerType(newMembershipScore); // Tính toán loại khách hàng mới dựa trên điểm thành viên mới
    values.put(CUSTOMER_TYPE, newCustomerType);

    db.update(TBL_CUSTOMER, values, CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});
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


    public String getNextMembershipType(String currentType) {
        String nextType = null;
        switch (currentType) {
            case "Thường":
                nextType = "Đồng";
                break;
            case "Đồng":
                nextType = "Bạc";
                break;
            case "Bạc":
                nextType = "Vàng";
                break;
            case "Vàng":
                nextType = "Kim Cương";
                break;
            case "Kim Cương":
                // Nếu đã ở mức cuối cùng, không có mức tiếp theo
                break;
            default:
                // Xử lý trường hợp không xác định
                break;
        }
        return nextType;
    }

    public int getNextMembershipTarget(int currentScore) {
        int nextTarget = 0;

        if (currentScore >= 0 && currentScore < 1000) {
            nextTarget = 999;
        } else if (currentScore >= 1000 && currentScore < 5000) {
            nextTarget = 4999;
        } else if (currentScore >= 5000 && currentScore < 10000) {
            nextTarget = 9999;
        } else if (currentScore >= 10000 && currentScore < 20000) {
            nextTarget = 19999;
        } else if (currentScore >= 20000) {
            // Nếu đã đạt mức điểm cao nhất, không có mục tiêu tiếp theo
            nextTarget = currentScore;
        }

        return nextTarget;
    }





    //    Kiểm tra bảng Product
    public int numbOfRowsProduct(){
        Cursor c = getData("SELECT * FROM " + TBl_PRODUCT);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public boolean insertData(String ProductName, double ProductPrice, String ProductDescription, byte[] ProductThumb, int Hot, String Category, int Inventory, double ProductRate, double SalePrice, int SoldQuantiy, String CreatedDate, int Status) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "INSERT INTO " + TBl_PRODUCT + "(" +
                PRODUCT_NAME + ", " +
                PRODUCT_PRICE + ", " +
                PRODUCT_DESCRIPTION + ", " +
                PRODUCT_THUMB + ", " +
                HOT + ", " +
                CATEGORY + ", " +
                INVENTORY + ", " +
                PRODUCT_RATE + ", " +
                SALE_PRICE + ", " +
                SOLD_QUANTITY + ", " +
                CREATED_DATE + ", " +
                STATUS +
                ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, ProductName);
        statement.bindDouble(2, ProductPrice);
        statement.bindString(3, ProductDescription);
        statement.bindBlob(4, ProductThumb);
        statement.bindLong(5, Hot);
        statement.bindString(6, Category);
        statement.bindLong(7, Inventory);
        statement.bindDouble(8, ProductRate);
        statement.bindDouble(9, SalePrice);
        statement.bindLong(10, SoldQuantiy);
        statement.bindString(11, CreatedDate);
        statement.bindLong(12, Status);

        long result = statement.executeInsert();
        boolean  success = result != -1; // Trả về true nếu chèn thành công, false nếu không
        Log.d("DatabaseHelper", "Insert data result: " + success);
        return success;
    }

    public void createSampleProduct() {
        if (numbOfRowsProduct() == 0){
            insertData("Đĩa nhỏ", 120000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc  giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này.", convertPhoto(context, R.drawable.dgd_dia1), 1, "Đồ gia dụng", 60, 4.5, 100000, 87, "2024/04/10", 1);
            insertData("Khay đựng xà phòng", 170000, "Khay đựng bánh xà phòng từ nhựa tái chế là sự kết hợp hoàn hảo giữa tính thực tế và cam kết với môi trường. Với nguyên liệu là nhựa tái chế, sản phẩm này không chỉ giúp giảm lượng chất thải nhựa mà còn thể hiện tinh thần chăm sóc đối với hành tinh xanh của chúng ta. Thiết kế của khay đựng bánh xà phòng không chỉ đơn giản mà còn linh hoạt, phù hợp với mọi không gian nhà tắm. Sự sáng tạo trong cách sử dụng nguyên liệu tái chế không chỉ làm cho sản phẩm trở nên độc đáo mà còn đặt ra một tiêu chí mới cho việc chọn lựa sản phẩm gia dụng có trách nhiệm với môi trường. Khay đựng bánh xà phòng từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian nhà tắm mà còn là một bước nhỏ nhưng ý nghĩa trong hành trình chung của chúng ta để bảo vệ và giữ gìn cho hành tinh xanh, trong từng lựa chọn hàng ngày của chúng ta.", convertPhoto(context, R.drawable.dgd_xaphong1), 0, "Đồ gia dụng", 60, 4.7, 150000, 45, "2024/04/10", 1);
            insertData("Lót ly", 110000, "Đồ lót ly từ nhựa tái chế là sự kết hợp độc đáo giữa sự thoải mái và cam kết với môi trường. Với việc sử dụng nguyên liệu là nhựa tái chế, sản phẩm không chỉ mang lại cảm giác mềm mại và thoải mái cho người sử dụng mà còn đóng góp vào việc giảm lượng chất thải nhựa. Thiết kế của đồ lót ly không chỉ chú trọng đến sự thoải mái và tôn lên vẻ đẹp tự nhiên, mà còn thể hiện sự chấp nhận trách nhiệm với môi trường. Việc sử dụng nhựa tái chế không chỉ là một xu hướng tiêu dùng thông minh mà còn là sự đóng góp tích cực vào việc bảo vệ nguồn tài nguyên tự nhiên và giảm thiểu ảnh hưởng tiêu cực đối với hệ sinh thái. Đồ lót ly từ nhựa tái chế không chỉ là lựa chọn thông minh cho sự thoải mái hàng ngày mà còn là một cách để chúng ta cùng nhau xây dựng một lối sống thân thiện với môi trường, đồng thời thể hiện sự quan tâm đến sức khỏe và hành tinh xanh của chúng ta.", convertPhoto(context, R.drawable.dgd_lotly1), 1, "Đồ gia dụng", 60, 4.0, 100000, 100, "2024/04/10", 1);
            insertData("Giá đỡ laptop", 220000, "Giá đỡ laptop từ nhựa tái chế là một phụ kiện không thể thiếu cho những người sử dụng máy tính xách tay, kết hợp giữa tính thực tế và tầm nhìn bền vững. Với sự sáng tạo trong việc sử dụng nhựa tái chế, sản phẩm không chỉ tạo ra một nơi thoải mái để đặt laptop mà còn là cách nhỏ nhưng tích cực để giảm lượng chất thải nhựa. Thiết kế nhẹ nhàng và linh hoạt của giá đỡ không chỉ giúp người dùng duy trì tư duy làm việc hiệu quả mà còn hỗ trợ vào nỗ lực chung của cộng đồng trong việc giữ gìn môi trường. Sự cam kết đối với nhựa tái chế không chỉ là một xu hướng tiêu dùng mà còn là một lối sống, và sản phẩm giá đỡ laptop này là minh chứng rõ ràng cho sự hài hòa giữa tiện ích và sự chấp nhận trách nhiệm với môi trường. Hãy lựa chọn giá đỡ laptop từ nhựa tái chế để không chỉ tận hưởng sự thuận tiện mà còn tham gia vào cuộc hành trình bảo vệ hành tinh của chúng ta.", convertPhoto(context, R.drawable.dgd_laptop1), 0, "Đồ gia dụng", 60, 5, 200000, 30, "2024/04/10", 1);
            insertData("Dây cờ trang trí tiệc", 120000, "Dây cờ là một sản phẩm trang trí không thể thiếu cho bất kỳ buổi tiệc nào, và đặc biệt, chúng tôi tự hào giới thiệu dòng sản phẩm dây cờ được làm từ nhựa tái chế. Sự sáng tạo trong thiết kế không chỉ tạo ra không khí vui tươi và phấn khích cho bất kỳ dịp lễ nào mà còn góp phần tích cực vào nỗ lực bảo vệ môi trường. Với việc sử dụng nhựa tái chế, chúng tôi cam kết giảm lượng chất thải nhựa và tái sử dụng nguyên liệu, giữ cho không gian tiệc tùng trở nên thú vị hơn mà không ảnh hưởng đến môi trường. Dây cờ từ nhựa tái chế không chỉ đẹp mắt mà còn là một cách thúc đẩy ý thức về trách nhiệm xã hội và bảo vệ hành tinh xanh chúng ta. Hãy tận hưởng những khoảnh khắc vui vẻ và đồng thời hỗ trợ vào việc giữ cho hành tinh của chúng ta trở nên bền vững hơn.", convertPhoto(context, R.drawable.dtt_dayco1), 1, "Đồ trang trí", 60, 4.5, 100000, 25, "2024/04/03", 1);
            insertData("Đồ trang trí giáng sinh", 90000, "Đồ trang trí Giáng Sinh 3D không chỉ làm mới không khí của mùa lễ hội mà còn là biểu tượng của sự sang trọng và ý thức về môi trường. Với việc sử dụng nhựa tái chế, sản phẩm này không chỉ tạo ra một không gian lễ hội ấm cúng mà còn đóng góp tích cực vào việc giảm lượng chất thải nhựa. Mỗi chiếc đồ trang trí được chế tạo với kỹ thuật 3D độc đáo, tạo nên hiệu ứng thị giác đặc sắc và sống động, làm tôn lên vẻ đẹp của mùa Giáng Sinh. Việc tái chế nhựa không chỉ giúp giảm tác động tiêu cực đối với môi trường mà còn thúc đẩy ý thức về việc sử dụng tài nguyên tái chế trong sản xuất. Đồ trang trí Giáng Sinh 3D là sự kết hợp hoàn hảo giữa sự sang trọng, sáng tạo và ý thức môi trường. Bằng cách chọn lựa sản phẩm này, chúng ta không chỉ tận hưởng không khí lễ hội phấn khích mà còn thể hiện sự quan tâm đến bảo vệ môi trường và chọn lựa bền vững trong mọi hoạt động.", convertPhoto(context, R.drawable.dtt_giangsinh1), 0, "Đồ trang trí", 60, 4.6, 70000, 80, "2024/04/10", 1);
            insertData("Đồng hồ treo tường", 370000, "Đồng hồ treo tường từ nhựa tái chế không chỉ là một sản phẩm thời gian mà còn là biểu tượng của sự sáng tạo và tôn trọng đối với môi trường. Với thiết kế độc đáo, sản phẩm này là sự kết hợp hoàn hảo giữa vẻ ngoại hình tinh tế và cam kết với lối sống bền vững. Bằng cách sử dụng nhựa tái chế, đồng hồ treo tường không chỉ giảm lượng rác thải nhựa mà còn giúp tái chế nguyên liệu, đóng góp vào việc bảo vệ môi trường. Sự linh hoạt trong việc tạo hình và màu sắc của sản phẩm này không chỉ làm mới không gian sống mà còn thể hiện tầm quan trọng của việc chọn lựa sản phẩm có trách nhiệm với môi trường. Đồng hồ treo tường từ nhựa tái chế không chỉ đơn thuần là một phụ kiện trang trí, mà còn là biểu tượng của lối sống ý thức về môi trường. Việc đặt mình vào ngôi nhà của bạn không chỉ làm tăng thêm vẻ đẹp mà còn là bước nhỏ nhưng ý nghĩa để góp phần vào việc bảo vệ hành tinh của chúng ta.", convertPhoto(context, R.drawable.dtt_dongho1), 1, "Đồ trang trí", 60, 4.2, 350000, 100, "2024/04/03", 1);
            insertData("Móc khóa hình đảo Kos", 110000, "Móc khóa hình đảo Kos là một tác phẩm sáng tạo không chỉ đẹp mắt mà còn mang đến sự ý thức về môi trường. Được tạo ra từ nhựa tái chế, sản phẩm này là biểu tượng của sự kết hợp giữa nghệ thuật và bảo vệ môi trường. Hình ảnh đảo Kos được minh họa trên móc khóa không chỉ đẹp mắt mà còn là cách tuyệt vời để kỷ niệm và tôn vinh vẻ đẹp của đảo nổi tiếng này. Với việc sử dụng nguyên liệu tái chế, chúng ta không chỉ giảm lượng chất thải nhựa mà còn thúc đẩy tư duy bền vững trong sản xuất. Mỗi chiếc móc khóa không chỉ là một sản phẩm thực tế, mà còn là một cách để chia sẻ thông điệp về ý thức môi trường và sự cần thiết của việc bảo vệ những địa điểm đẹp tự nhiên như đảo Kos. Đặt mình vào túi của bạn, sản phẩm này không chỉ là một chiếc móc khóa mà còn là một tuyên ngôn về sự đồng lòng trong việc bảo vệ hành tinh của chúng ta.", convertPhoto(context, R.drawable.pk_mockhoakos1), 0, "Đồ trang trí", 60, 5.0, 90000, 67, "2024/04/03", 1);
            insertData("Bông tai hình bông hoa", 160000, "Bông tai hình bông hoa là một biểu tượng của sự thanh lịch và sáng tạo, mang đến cho người đeo không chỉ vẻ đẹp tinh tế mà còn là niềm tự hào về việc chọn lựa có trách nhiệm với môi trường. Sản phẩm này được chế tạo từ nhựa tái chế, đó là một bước tiến quan trọng trong việc giảm lượng chất thải nhựa và giữ cho tài nguyên tự nhiên được bảo vệ. Bông hoa tinh tế được tái tạo từ nhựa tái chế không chỉ là một tuyên ngôn về sự đẹp đẽ mà còn là một cam kết vững chắc đối với bảo vệ môi trường. Việc sử dụng nguyên liệu tái chế giúp giảm áp lực đặt ra cho hệ sinh thái và hỗ trợ trong việc xây dựng một tương lai bền vững. Bông tai hình bông hoa không chỉ là một phụ kiện thời trang, mà còn là biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc duy trì sự cân bằng giữa thời trang và bảo vệ môi trường.", convertPhoto(context, R.drawable.pk_bongtai_hoa1), 1, "Phụ kiện", 60, 4.5, 140000, 56, "2024/04/10", 1);
            insertData("Bông tai hình chữ nhật", 120000, "Bông tai hình chữ nhật không chỉ là một biểu tượng của sự đơn giản và hiện đại trong thế giới thời trang mà còn là một minh chứng cho sự sáng tạo và chăm sóc đối với môi trường. Sản phẩm này được tạo ra từ nhựa tái chế, một lựa chọn thông minh và đảm bảo, giúp giảm lượng chất thải nhựa và bảo vệ nguồn tài nguyên tự nhiên. Thiết kế hình chữ nhật đơn giản nhưng tinh tế của bông tai mang lại sự linh hoạt trong việc kết hợp với nhiều phong cách thời trang khác nhau. Đồng thời, việc sử dụng nhựa tái chế không chỉ giúp giảm áp lực đặt ra cho môi trường mà còn thể hiện cam kết đối với phong cách sống bền vững và tiêu thụ có trách nhiệm. Bông tai hình chữ nhật không chỉ là một chiếc phụ kiện thời trang độc đáo mà còn là biểu tượng của ý thức môi trường. Sử dụng sản phẩm này không chỉ là một cách để thể hiện cái tôi cá nhân mà còn là bước nhỏ nhưng ý nghĩa trong việc góp phần vào việc giữ cho hành tinh của chúng ta trở nên bền vững hơn", convertPhoto(context, R.drawable.pk_bongtai_hcn1), 0, "Phụ kiện", 60, 4.7, 100000, 48, "2024/04/03", 1);
            insertData("Móc khóa hình mặt cười", 110000, "Móc khóa hình mặt cười là một phụ kiện vui nhộn và thân thiện với môi trường, được sáng tạo từ nhựa tái chế. Thiết kế này không chỉ mang lại sự hứng khởi với hình ảnh mặt cười thân quen mà còn góp phần giảm lượng chất thải nhựa đối với môi trường. Sử dụng nhựa tái chế là một cam kết đối với bảo vệ hành tinh của chúng ta, tạo ra một sản phẩm không chỉ phản ánh tính cách lạc quan mà còn thể hiện tinh thần chăm sóc môi trường. Mỗi chiếc móc khóa không chỉ là một biểu tượng vui nhộn mà còn là một bước tiến tích cực trong việc hướng tới một lối sống bền vững và ý thức môi trường. Sở hữu một chiếc móc khóa hình mặt cười không chỉ là thêm vào bộ sưu tập phụ kiện cá nhân của bạn mà còn là cách để bạn thể hiện sự quan tâm đến môi trường.", convertPhoto(context, R.drawable.pk_mockhoacuoi), 1, "Phụ kiện", 60, 4.0, 90000, 89, "2024/04/03", 1);
            insertData("Móc khóa rùa biển", 110000, "Móc khóa hình rùa biển là một sáng tạo độc đáo kết hợp giữa thiết kế đáng yêu và tôn trọng môi trường. Sản phẩm này làm từ nhựa tái chế, chú trọng đến việc giảm lượng chất thải nhựa và ảnh hưởng tích cực đến bảo vệ hệ sinh thái biển cả. Hình rùa biển được chọn làm điểm nhấn cho móc khóa không chỉ vì sự đáng yêu mà còn vì ý nghĩa mà chúng mang lại trong việc góp phần bảo vệ động vật biển. Sự kết hợp giữa ý thức môi trường và thiết kế sáng tạo khiến cho sản phẩm này trở thành một cách tuyệt vời để thể hiện phong cách cá nhân của bạn trong khi đồng thời chung tay bảo vệ môi trường xanh - nơi rùa biển và nhiều loài động vật khác gọi là nhà.", convertPhoto(context, R.drawable.pk_mockhoarua1), 0, "Phụ kiện", 60, 4.5, 90000, 50, "2024/04/10", 1);

        }

    }
    private byte[] convertPhoto(Context context, int resourceId) {
        BitmapDrawable drawable = (BitmapDrawable) context.getDrawable(resourceId);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

//    Kiểm tra bảng Discuss
    public int numbOfRowsDiscuss(){
        Cursor c = getData("SELECT * FROM " + TBl_DISCUSS);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataDiscuss(){
        if (numbOfRowsCustomer() == 0){
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '1', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '2', 'anhlethi@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '3', 'danghoangmai23@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '4', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '5', ''anhlethi@gmail.com, 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '6', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '7', 'tranthimy96@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '8', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '9', 'danghoangmai23@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '10', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '11', 'lananhvu@st.uel.edu.vn', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '12', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn., 1)");

        }
    }


    //    Kiểm tra bảng Feedback
    public int numbOfRowsFeedback(){
        Cursor c = getData("SELECT * FROM " + TBl_FEEDBACK);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataFeedback(){
        if (numbOfRowsCustomer() == 0){
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '1', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '2', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.0, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '3', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 5.0, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '4', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '5', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '6', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '7', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '8', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '9', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '10', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '11', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");
            execSql("INSERT INTO " + TBl_FEEDBACK + " VALUES(null, '12', '1', '1', 'Sản phẩm có chất lượng tốt, giá cả hợp lí', 4.5, '2024-04-14')");



        }
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu truy vấn SQL để lấy danh sách sản phẩm theo category
        String selectQuery = "SELECT * FROM " + TBl_PRODUCT + " WHERE " + CATEGORY + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{category});

        // Lặp qua tất cả các hàng và thêm các sản phẩm vào danh sách productList
        if (cursor.moveToFirst()) {
            do {
                products.add(new Product(
                        cursor.getInt(0), //ProductID
                        cursor.getString(1), //ProductName
                        cursor.getDouble(2), // ProductPrice
                        cursor.getString(3), //ProductDescription
                        cursor.getBlob(4), //ProductThumb
                        cursor.getInt(5), //Hot
                        cursor.getString(6), //Category
                        cursor.getInt(7), //Inventory
                        cursor.getDouble(8), //ProductRate
                        cursor.getDouble(9), //SalePrice
                        cursor.getInt(10), //SoldQuantity
                        cursor.getString(11), //CreatedDate
                        cursor.getInt(12)
                ));
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và đóng kết nối cơ sở dữ liệu
        cursor.close();
        db.close();

        return products;
    }
    public ArrayList<UserInfo> getLoggedinUserDetailsMain(String email) {
        ArrayList<UserInfo> customers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TBL_CUSTOMER + " WHERE " + EMAIL + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
        Customer customer = null;

        // Nếu có kết quả từ câu truy vấn
        if (cursor.moveToFirst()) {
            // Lấy thông tin từ cursor
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(FULLNAME));

            UserInfo userInfo = new UserInfo();
//            customer.getFullName(), customer.getUsername(), customer.getPhone(), String.valueOf(customer.getGender()), customer.getBirthday(), customer.getEmail(), customer.getCustomerId();
            userInfo.setFullName(name);

            customers.add(userInfo);
        }

        // Đóng con trỏ và database
        cursor.close();
        sqLiteDatabase.close();

        return customers;
    }
    public ArrayList<UserInfo> getLoggedinUserDetails(String email){
        ArrayList<UserInfo> customers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TBL_CUSTOMER + " WHERE " + EMAIL + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
        Customer customer = null;

        // Nếu có kết quả từ câu truy vấn
        if (cursor.moveToFirst()) {
            // Lấy thông tin từ cursor
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(FULLNAME));
            @SuppressLint("Range") String emails = cursor.getString(cursor.getColumnIndex(EMAIL));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(USERNAME));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(PHONE));
            @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(GENDER));
            @SuppressLint("Range") String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));


            UserInfo userInfo = new UserInfo();
            userInfo.setFullName(name);
            userInfo.setUserName(username);
            userInfo.setPhone(phone);
            userInfo.setEmail(emails);
            userInfo.setGender(gender);
            userInfo.setBirthday(birthday);

            customers.add(userInfo);
        }

        // Đóng con trỏ và database
        cursor.close();
        sqLiteDatabase.close();

        return customers;
    }
    @SuppressLint("Range")
    public int getCustomerIdFromCustomer(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int customerId = -1; // Giá trị mặc định nếu không tìm thấy customerId
        String query = "SELECT " + CUSTOMER_ID + " FROM " + TBL_CUSTOMER + " WHERE " + EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            customerId = cursor.getInt(cursor.getColumnIndex(CUSTOMER_ID));
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(FULLNAME));
            cursor.close();
        }

        // Đóng con trỏ và database
//        cursor.close();
//        sqLiteDatabase.close();
//
//        return customers;
        db.close();
        return customerId;
    }

    //Custom product
    public long insertData(String customerName, String email, String phone, String productCusName, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMPRODUCT_NAME, customerName);
        values.put(CUSTOMPRODUCT_EMAIL, email);
        values.put(CUSTOMPRODUCT_PHONE, phone);
        values.put(CUSTOMPRODUCT_TITLE, productCusName);
        values.put(CUSTOMPRODUCT_DESFILE, imageUri);
        long newRowId = db.insert(TBL_CUSTOMPRODUCT, null, values);
        db.close();
        return newRowId;
    }
    public boolean upDateUserProfile(String email, String fullname1, String username1, String phone1, String gender1, String birthday1){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULLNAME, fullname1);
        values.put(USERNAME, username1);
        values.put(PHONE, phone1);
        values.put(EMAIL, email);
        values.put(GENDER, gender1);
        values.put(BIRTHDAY, birthday1);
        int i =sqLiteDatabase.update(TBL_CUSTOMER, values, "EMAIL=?", new String[] {email});
        if (i>0){
            return true;
        }else {
            return false;
        }
    }


}





