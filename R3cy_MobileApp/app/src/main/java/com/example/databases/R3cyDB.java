package com.example.databases;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import static java.sql.DriverManager.getConnection;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.models.Address;
import com.example.models.Blog;
import com.example.models.Customer;
import com.example.models.Product;
import com.example.models.ProductAtb;
import com.example.models.UserInfo;
import com.example.r3cy_mobileapp.R;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;


public class R3cyDB extends SQLiteOpenHelper {
    Context context;
    R3cyDB db;
    ArrayList<Product> products;
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
//    public static final String DISCOUNT = "Discount";
    public static final String COUPON_ORDER ="couponOrder";
    public static final String  COUPON_SHIPPING="couponShipping";
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
            STATUS + " INTEGER DEFAULT 1" +
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
            COUPON_ORDER + " REAL," +
            COUPON_SHIPPING + " REAL," +
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
            CUSTOMER_TYPE + " TEXT NOT NULL DEFAULT 'Thường'" +
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
//    public boolean insertDataOrder(String ORDER_ID, String ORDER_CUSTOMER_ID, String ORDER_DATE, String PAYMENT_METHOD, String PAYMENT_ID, String COUPON_ID, double TOTAL_ORDER_VALUE, String ORDER_STATUS, String ORDER_NOTE, String DELIVERY_DATE, String DISCOUNT, double SHIPPING_FEE, double TOTAL_AMOUNT, String PAYMENT_STATUS, String ADDRESS_ID) {
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO " + TBl_ORDER + "(" +
//                ORDER_ID + ", " +
//                ORDER_CUSTOMER_ID + ", " +
//                ORDER_DATE + ", " +
//                PAYMENT_METHOD + ", " +
//                PAYMENT_ID + ", " +
//                COUPON_ID + ", " +
//                TOTAL_ORDER_VALUE + ", " +
//                ORDER_STATUS + ", " +
//                ORDER_NOTE + ", " +
//                DELIVERY_DATE + ", " +
//                DISCOUNT + ", " +
//                SHIPPING_FEE + ", " +
//                TOTAL_AMOUNT + ", " +
//                PAYMENT_STATUS + ", " +
//                ADDRESS_ID +
//                ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//        SQLiteStatement statement = database.compileStatement(sql);
//
//// Chuyển đổi chuỗi ngày tháng thành đối tượng Date
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Date date;
//        try {
//            date = sdf.parse(ORDER_DATE);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false;
//        }
//        statement.bindString(1, ORDER_ID);
//        statement.bindString(2, ORDER_CUSTOMER_ID);
//        statement.bindString(3, ORDER_DATE);
//        statement.bindString(4, PAYMENT_METHOD);
//        statement.bindString(5, PAYMENT_ID);
//        statement.bindString(6, COUPON_ID);
//        statement.bindDouble(7, TOTAL_ORDER_VALUE);
//        statement.bindString(8, ORDER_STATUS);
//        statement.bindString(9, ORDER_NOTE);
//        statement.bindString(10, DELIVERY_DATE);
//        statement.bindString(11, DISCOUNT);
//        statement.bindDouble(12, SHIPPING_FEE);
//        statement.bindDouble(13, TOTAL_AMOUNT);
//        statement.bindString(14, PAYMENT_STATUS);
//        statement.bindString(15, ADDRESS_ID);
//
//
//        long result = statement.executeInsert();
//        boolean  success = result != -1;
//        Log.d("DatabaseHelper", "Insert data result: " + success);
//        return success;
//    }
    public void createSampleDataOrder(){
        execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 1, '14-04-2024', 'COD', null, null, 236000, 'Đang giao', 'Che tên sản phẩm', '15-04-2024', '0', 35000, 20000, 200000, 0, null)");
        execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 2, '15-04-2024', 'COD', null, null, 232000, 'Chờ xử lý', 'Che tên sản phẩm', '16-04-2024', '0', 20000,20000, 220000, 0, null)");
        execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 3, '16-04-2024', 'COD', null, null, 205000, 'Chờ lấy hàng', 'Che tên sản phẩm', '17-04-2024', '0', 25000,20000, 210000, 0, null)");
        execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 4, '17-04-2024', 'COD', null, null, 135000, 'Hoàn thành', 'Che tên sản phẩm', '18-04-2024', '0', 15000,20000, 250000, 0, null)");
        execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 5, '18-04-2024', 'COD', null, null, 165000, 'Đang giao', 'Che tên sản phẩm', '19-04-2024', '0', 35000,20000, 260000, 0, null)");
    }

    public int numbOfRowsOrderLine(){
        Cursor c = getData("SELECT * FROM " + TBl_ORDER_LINE);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

//    public boolean insertDataOrderLine(String ORDER_LINE_ID, String ORDER_LINE_ORDER_ID, String ORDER_LINE_PRODUCT_ID, double ORDER_SALE_PRICE, String QUANTITY) {
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "INSERT INTO " + TBl_ORDER_LINE + "(" +
//                ORDER_LINE_ID + ", " +
//                ORDER_LINE_ORDER_ID + ", " +
//                ORDER_LINE_PRODUCT_ID + ", " +
//                ORDER_SALE_PRICE + ", " +
//                QUANTITY +
//                ") VALUES(?,?,?,?,?)";
//
//        SQLiteStatement statement = database.compileStatement(sql);
//
////
//        statement.bindString(1, ORDER_LINE_ID);
//        statement.bindString(2, ORDER_LINE_ORDER_ID);
//        statement.bindString(3, ORDER_LINE_PRODUCT_ID);
//        statement.bindDouble(4, ORDER_SALE_PRICE);
//        statement.bindString(5, QUANTITY);
//
//
//        long result = statement.executeInsert();
//        boolean  success = result != -1;
//        Log.d("DatabaseHelper", "Insert data result: " + success);
//        return success;
//    }
    public void createSampleDataOrderLine(){
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 1, 1, 160000, 4)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 2, 2, 150000, 3)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 3, 3, 140000, 3)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 4, 4, 150000, 2)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 5, 5, 170000, 1)");
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

public boolean updatePasswordByEmail(String email, String newPassword) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(PASSWORD, newPassword);

    int rowsAffected = db.update(TBL_CUSTOMER, values, EMAIL + " = ?", new String[]{email});
    db.close();

    return rowsAffected > 0;
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

//Hàm tích lũy điểm
public void accumulateMembershipScore(int customerId, double orderValue) {
    // Quy đổi 3k cho 1 điểm
    int scoreToAdd = (int) (orderValue / 3000);

    SQLiteDatabase db = this.getWritableDatabase();

    // Lấy số điểm tích lũy hiện tại của khách hàng
    int currentScore = getMembershipScore(customerId);

    // Tính toán số điểm mới
    int newScore = currentScore + scoreToAdd;

    ContentValues values = new ContentValues();
    values.put(MEMBERSHIP_SCORE, newScore);

    // Cập nhật số điểm mới vào cơ sở dữ liệu
    db.update(TBL_CUSTOMER, values, CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});

    // Cập nhật loại khách hàng mới dựa trên số điểm mới
    String newCustomerType = calculateCustomerType(newScore);
    values.put(CUSTOMER_TYPE, newCustomerType);

    db.update(TBL_CUSTOMER, values, CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});

    db.close();
}

    @SuppressLint("Range")
    public int getMembershipScore(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int membershipScore = 0;

        Cursor cursor = db.query(TBL_CUSTOMER, new String[]{MEMBERSHIP_SCORE}, CUSTOMER_ID + " = ?",
                new String[]{String.valueOf(customerId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            membershipScore = cursor.getInt(cursor.getColumnIndex(MEMBERSHIP_SCORE));
            cursor.close();
        }

        return membershipScore;
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


    public boolean insertData(Product product, ProductAtb productAtb, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME, product.getProductName());
        contentValues.put(PRODUCT_PRICE, productAtb.getProductPrice());
        contentValues.put(PRODUCT_DESCRIPTION, product.getProductDescription());

        byte[] thumbByteArray = product.getProductThumb();
        contentValues.put(PRODUCT_THUMB, thumbByteArray);

        contentValues.put(HOT, productAtb.getHot());
        contentValues.put(CATEGORY, product.getCategory());
        contentValues.put(INVENTORY, productAtb.getInventory());
        contentValues.put(PRODUCT_RATE, product.getProductRate());
        contentValues.put(SALE_PRICE, product.getSalePrice());
        contentValues.put(SOLD_QUANTITY, productAtb.getSoldQuantity());
        contentValues.put(CREATED_DATE, productAtb.getCreatedDate());
        contentValues.put(STATUS, productAtb.getStatus());

        long result = db.insert(TBl_PRODUCT, null, contentValues);

        return result != -1;
    }

    public  void createSampleProduct(){
        if (numbOfRowsProduct() == 0) {
            SQLiteDatabase db = getReadableDatabase();

//        Sản phẩm 1
            Product product1 = new Product();
            product1.setProductThumb(convertPhoto(context, R.drawable.dgd_dia1));
            product1.setProductName("Đĩa nhỏ");
            product1.setSalePrice(100000);
            product1.setCategory("Đồ gia dụng");
            product1.setProductDescription("Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu..");
            product1.setProductRate(4.5);

            ProductAtb productAtb1 = new ProductAtb();
            productAtb1.setProductPrice(120000);
            productAtb1.setHot(1);
            productAtb1.setInventory(60);
            productAtb1.setSoldQuantity(87);
            productAtb1.setCreatedDate("2024/04/03");
            productAtb1.setStatus(1);

            insertData(product1, productAtb1, db);

            //        Sản phẩm 2
            Product product2 = new Product();
            product2.setProductThumb(convertPhoto(context, R.drawable.dgd_xaphong2));
            product2.setProductName("Khay đựng xà phòng");
            product2.setSalePrice(150000);
            product2.setCategory("Đồ gia dụng");
            product2.setProductDescription("Khay đựng bánh xà phòng từ nhựa tái chế là sự kết hợp hoàn hảo giữa tính thực tế và cam kết với môi trường. Với nguyên liệu là nhựa tái chế, sản phẩm này không chỉ giúp giảm lượng chất thải nhựa mà còn thể hiện tinh thần chăm sóc đối với hành tinh xanh của chúng ta.  ");
            product2.setProductRate(4.5);

            ProductAtb productAtb2 = new ProductAtb();
            productAtb2.setProductPrice(170000);
            productAtb2.setHot(1);
            productAtb2.setInventory(60);
            productAtb2.setSoldQuantity(65);
            productAtb2.setCreatedDate("2024/04/03");
            productAtb2.setStatus(1);

            insertData(product2, productAtb2, db);

            //        Sản phẩm 3
            Product product3 = new Product();
            product3.setProductThumb(convertPhoto(context, R.drawable.dgd_lotly1));
            product3.setProductName("Lót ly");
            product3.setSalePrice(90000);
            product3.setCategory("Đồ gia dụng");
            product3.setProductDescription(" Đồ lót ly từ nhựa tái chế là sự kết hợp độc đáo giữa sự thoải mái và cam kết với môi trường. Với việc sử dụng nguyên liệu là nhựa tái chế, sản phẩm không chỉ mang lại cảm giác mềm mại và thoải mái cho người sử dụng mà còn đóng góp vào việc giảm lượng chất thải nhựa. ");
            product3.setProductRate(4.5);

            ProductAtb productAtb3 = new ProductAtb();
            productAtb3.setProductPrice(110000);
            productAtb3.setHot(1);
            productAtb3.setInventory(60);
            productAtb3.setSoldQuantity(65);
            productAtb3.setCreatedDate("2024/04/03");
            productAtb3.setStatus(1);

            insertData(product3, productAtb3, db);

            //        Sản phẩm 4
            Product product4 = new Product();
            product4.setProductThumb(convertPhoto(context, R.drawable.dgd_laptop1));
            product4.setProductName("Giá đỡ laptop");
            product4.setSalePrice(180000);
            product4.setCategory("Đồ gia dụng");
            product4.setProductDescription("Giá treo đồ từ nhựa tái chế là sự kết hợp tuyệt vời giữa tính tiện ích và ý thức bảo vệ môi trường. Với nguyên liệu chính từ nhựa tái chế, sản phẩm không chỉ giúp giảm lượng chất thải nhựa mà còn là biểu tượng của cam kết với lối sống bền vững.");
            product4.setProductRate(4.0);

            ProductAtb productAtb4 = new ProductAtb();
            productAtb4.setProductPrice(200000);
            productAtb4.setHot(0);
            productAtb4.setInventory(60);
            productAtb4.setSoldQuantity(15);
            productAtb4.setCreatedDate("2024/04/03");
            productAtb4.setStatus(1);
            insertData(product4, productAtb4, db);

            //        Sản phẩm 5
            Product product5 = new Product();
            product5.setProductThumb(convertPhoto(context, R.drawable.dtt_giangsinh1));
            product5.setProductName("Đồ trang trí giáng sinh 3D");
            product5.setSalePrice(80000);
            product5.setCategory("Đồ trang trí");
            product5.setProductDescription(" Đồ trang trí Giáng Sinh 3D không chỉ làm mới không khí của mùa lễ hội mà còn là biểu tượng của sự sang trọng và ý thức về môi trường. Với việc sử dụng nhựa tái chế, sản phẩm này không chỉ tạo ra một không gian lễ hội ấm cúng mà còn đóng góp tích cực vào việc giảm lượng chất thải nhựa.");
            product5.setProductRate(4.0);

            ProductAtb productAtb5 = new ProductAtb();
            productAtb5.setProductPrice(100000);
            productAtb5.setHot(1);
            productAtb5.setInventory(60);
            productAtb5.setSoldQuantity(50);
            productAtb5.setCreatedDate("2024/04/03");
            productAtb5.setStatus(1);

            insertData(product5, productAtb5, db);

            //        Sản phẩm 6
            Product product6 = new Product();
            product6.setProductThumb(convertPhoto(context, R.drawable.dtt_dongho2));
            product6.setProductName("Đồng hồ treo tường");
            product6.setSalePrice(350000);
            product6.setCategory("Đồ trang trí");
            product6.setProductDescription("Đồng hồ treo tường từ nhựa tái chế không chỉ là một sản phẩm thời gian mà còn là biểu tượng của sự sáng tạo và tôn trọng đối với môi trường. Với thiết kế độc đáo, sản phẩm này là sự kết hợp hoàn hảo giữa vẻ ngoại hình tinh tế và cam kết với lối sống bền vững.");
            product6.setProductRate(4.5);

            ProductAtb productAtb6 = new ProductAtb();
            productAtb6.setProductPrice(370000);
            productAtb6.setHot(1);
            productAtb6.setInventory(60);
            productAtb6.setSoldQuantity(65);
            productAtb6.setCreatedDate("2024/04/03");
            productAtb6.setStatus(1);

            insertData(product6, productAtb6, db);

            //        Sản phẩm 7
            Product product7 = new Product();
            product7.setProductThumb(convertPhoto(context, R.drawable.pk_mockhoakos1));
            product7.setProductName("Móc khóa hình đảo Kos");
            product7.setSalePrice(90000);
            product7.setCategory("Đồ trang trí");
            product7.setProductDescription("Móc khóa hình đảo Kos là một tác phẩm sáng tạo không chỉ đẹp mắt mà còn mang đến sự ý thức về môi trường. Được tạo ra từ nhựa tái chế, sản phẩm này là biểu tượng của sự kết hợp giữa nghệ thuật và bảo vệ môi trường.");
            product7.setProductRate(5.0);

            ProductAtb productAtb7 = new ProductAtb();
            productAtb7.setProductPrice(110000);
            productAtb7.setHot(0);
            productAtb7.setInventory(60);
            productAtb7.setSoldQuantity(51);
            productAtb7.setCreatedDate("2024/04/03");
            productAtb7.setStatus(1);

            insertData(product7, productAtb7, db);

            //        Sản phẩm 8
            Product product8 = new Product();
            product8.setProductThumb(convertPhoto(context, R.drawable.dtt_dayco1));
            product8.setProductName("Dây cờ trang trí tiệc");
            product8.setSalePrice(140000);
            product8.setCategory("Đồ trang trí");
            product8.setProductDescription("Dây cờ là một sản phẩm trang trí không thể thiếu cho bất kỳ buổi tiệc nào, và đặc biệt, chúng tôi tự hào giới thiệu dòng sản phẩm dây cờ được làm từ nhựa tái chế. Sự sáng tạo trong thiết kế không chỉ tạo ra không khí vui tươi và phấn khích cho bất kỳ dịp lễ nào mà còn góp phần tích cực vào nỗ lực bảo vệ môi trường.");
            product8.setProductRate(4.0);

            ProductAtb productAtb8 = new ProductAtb();
            productAtb8.setProductPrice(160000);
            productAtb8.setHot(1);
            productAtb8.setInventory(60);
            productAtb8.setSoldQuantity(65);
            productAtb8.setCreatedDate("2024/04/03");
            productAtb8.setStatus(1);

            insertData(product8, productAtb8, db);

            //        Sản phẩm 9
            Product product9 = new Product();
            product9.setProductThumb(convertPhoto(context, R.drawable.pk_bongtai_hcn1));
            product9.setProductName("Bông tai hình chữ nhật");
            product9.setSalePrice(140000);
            product9.setCategory("Phụ kiện");
            product9.setProductDescription("Bông tai hình chữ nhật không chỉ là một biểu tượng của sự đơn giản và hiện đại trong thế giới thời trang mà còn là một minh chứng cho sự sáng tạo và chăm sóc đối với môi trường. Thiết kế hình chữ nhật đơn giản nhưng tinh tế của bông tai mang lại sự linh hoạt trong việc kết hợp với nhiều phong cách thời trang khác nhau. ");
            product9.setProductRate(4.0);

            ProductAtb productAtb9 = new ProductAtb();
            productAtb9.setProductPrice(160000);
            productAtb9.setHot(1);
            productAtb9.setInventory(60);
            productAtb9.setSoldQuantity(65);
            productAtb9.setCreatedDate("2024/04/03");
            productAtb9.setStatus(1);

            insertData(product9, productAtb9, db);

            //        Sản phẩm 10
            Product product10 = new Product();
            product10.setProductThumb(convertPhoto(context, R.drawable.pk_bongtairua2));
            product10.setProductName("Bông tai hình rùa biển");
            product10.setSalePrice(140000);
            product10.setCategory("Phụ kiện");
            product10.setProductDescription("Bông tai hình rùa biển không chỉ là một phụ kiện thời trang quyến rũ mà còn là biểu tượng của sự ý thức về bảo vệ môi trường. Với thiết kế tinh tế và độc đáo, sản phẩm này không chỉ thu hút sự chú ý bởi vẻ đẹp của mình mà còn gửi đi một thông điệp mạnh mẽ về sự cần thiết của việc bảo vệ động vật biển.");
            product10.setProductRate(4.5);

            ProductAtb productAtb10 = new ProductAtb();
            productAtb10.setProductPrice(160000);
            productAtb10.setHot(0);
            productAtb10.setInventory(60);
            productAtb10.setSoldQuantity(65);
            productAtb10.setCreatedDate("2024/04/03");
            productAtb10.setStatus(1);

            insertData(product10, productAtb10, db);

            //        Sản phẩm 11
            Product product11 = new Product();
            product11.setProductThumb(convertPhoto(context, R.drawable.pk_bongtai_hoa2));
            product11.setProductName("Bông tai hình bông hoa");
            product11.setSalePrice(140000);
            product11.setCategory("Phụ kiện");
            product11.setProductDescription("Bông tai hình bông hoa là một biểu tượng của sự thanh lịch và sáng tạo, mang đến cho người đeo không chỉ vẻ đẹp tinh tế mà còn là niềm tự hào về việc chọn lựa có trách nhiệm với môi trường. ");
            product11.setProductRate(5.0);

            ProductAtb productAtb11 = new ProductAtb();
            productAtb11.setProductPrice(160000);
            productAtb11.setHot(0);
            productAtb11.setInventory(60);
            productAtb11.setSoldQuantity(65);
            productAtb11.setCreatedDate("2024/04/03");
            productAtb11.setStatus(1);

            insertData(product11, productAtb11, db);

            //        Sản phẩm 12
            Product product12 = new Product();
            product12.setProductThumb(convertPhoto(context, R.drawable.pk_mockhoacuoi));
            product12.setProductName("Móc khóa hình mặt cười");
            product12.setSalePrice(90000);
            product12.setCategory("Phụ kiện");
            product12.setProductDescription("Móc khóa hình mặt cười là một phụ kiện vui nhộn và thân thiện với môi trường, được sáng tạo từ nhựa tái chế. Thiết kế này không chỉ mang lại sự hứng khởi với hình ảnh mặt cười thân quen mà còn góp phần giảm lượng chất thải nhựa đối với môi trường.");
            product12.setProductRate(4.0);

            ProductAtb productAtb12 = new ProductAtb();
            productAtb12.setProductPrice(110000);
            productAtb12.setHot(0);
            productAtb12.setInventory(60);
            productAtb12.setSoldQuantity(65);
            productAtb12.setCreatedDate("2024/04/03");
            productAtb12.setStatus(1);

            insertData(product12, productAtb12, db);
        }

    }


    private byte[] convertPhoto(Context context, int resourceId) {
        BitmapDrawable drawable = (BitmapDrawable) context.getDrawable(resourceId);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }


    public Product getProductByID(int productID) {
        db = new R3cyDB(context);
        db.createSampleProduct();
        products = new ArrayList<>();

        Cursor cursor = db.getData("SELECT * FROM " + R3cyDB.TBl_PRODUCT);
        while (cursor.moveToNext()) {
            try {
                Product product = new Product(
                        cursor.getInt(0), // ProductID
                        cursor.getBlob(4), // ProductThumb
                        cursor.getString(1), // ProductName
                        cursor.getDouble(9), // SalePrice
                        cursor.getString(6), // Category
                        cursor.getString(3), // Description
                        cursor.getDouble(8) // ProductRate
                );

                if (product.getProductID() == productID) {
                    cursor.close();
                    return product; // Trả về sản phẩm nếu tìm thấy ID tương ứng
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return null;

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
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '1', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '2', 'anhlethi@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '3', 'danghoangmai23@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '4', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '5', ''anhlethi@gmail.com, 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '6', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '7', 'tranthimy96@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '8', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '9', 'danghoangmai23@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '10', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '11', 'lananhvu@st.uel.edu.vn', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");
            execSql("INSERT INTO " + TBl_DISCUSS + " VALUES(null, '12', 'leha@gmail.com', 'Sản phẩm này dùng có bền không?', 'Tôi đã mua sản phẩm này được 2 tháng, đến hiện tại dùng vẫn ổn.', 1)");

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

//    public List<Product> getProductsByCategory(String category) {
//        List<Product> products = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // Câu truy vấn SQL để lấy danh sách sản phẩm theo category
//        String selectQuery = "SELECT * FROM " + TBl_PRODUCT + " WHERE " + CATEGORY + " = ?";
//
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{category});
//
//        // Lặp qua tất cả các hàng và thêm các sản phẩm vào danh sách productList
//        if (cursor.moveToFirst()) {
//            do {
//                products.add(new Product(
//                        cursor.getInt(0), //ProductID
//                        cursor.getString(1), //ProductName
//                        cursor.getDouble(2), // ProductPrice
//                        cursor.getString(3), //ProductDescription
//                        cursor.getBlob(4), //ProductThumb
//                        cursor.getInt(5), //Hot
//                        cursor.getString(6), //Category
//                        cursor.getInt(7), //Inventory
//                        cursor.getDouble(8), //ProductRate
//                        cursor.getDouble(9), //SalePrice
//
//                ));
//            } while (cursor.moveToNext());
//        }
//
//        // Đóng con trỏ và đóng kết nối cơ sở dữ liệu
//        cursor.close();
//        db.close();
//
//        return products;
//    }
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
            @SuppressLint("Range") byte[] thumb = cursor.getBlob(cursor.getColumnIndex(CUSTOMER_THUMB));

            UserInfo userInfo = new UserInfo();
//            customer.getFullName(), customer.getUsername(), customer.getPhone(), String.valueOf(customer.getGender()), customer.getBirthday(), customer.getEmail(), customer.getCustomerId();
            userInfo.setFullName(name);
            if (thumb != null) {
                userInfo.setThumb(thumb);
            } else {
                userInfo.setThumb(convertPhoto(context, R.drawable.dgd_dia3));

            }

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
            @SuppressLint("Range") byte[] thumb = cursor.getBlob(cursor.getColumnIndex(CUSTOMER_THUMB));



            UserInfo userInfo = new UserInfo();
            userInfo.setFullName(name);
            userInfo.setUserName(username);
            userInfo.setPhone(phone);
            userInfo.setEmail(emails);
            userInfo.setGender(gender);
            userInfo.setBirthday(birthday);
            if (thumb != null) {
                userInfo.setThumb(thumb);
            } else {
                userInfo.setThumb(convertPhoto(context, R.drawable.dgd_dia3));

            }

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
            cursor.close();        }

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
    public boolean upDateUserImg(byte [] imgview, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_THUMB, imgview);

        int i =sqLiteDatabase.update(TBL_CUSTOMER, values, "EMAIL=?", new String[] {email});
        if (i>0){
            return true;
        }else {
            return false;
        }
    }



//    @SuppressLint("Range")
//    public String getOrderStatus(String orderstatus) {
//        SQLiteDatabase db = this.getReadableDatabase();
////        String customerId = -1; // Giá trị mặc định nếu không tìm thấy customerId
//        String query = "SELECT * FROM " + TBl_ORDER + " WHERE " + ORDER_STATUS + " = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{orderstatus});
//        if (cursor != null && cursor.moveToFirst()) {
//            orderstatus = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
////            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(FULLNAME));
//            cursor.close();
//
//        }
//
//        // Đóng con trỏ và database
////        cursor.close();
////        sqLiteDatabase.close();
////
//
//        db.close();
//
//        return orderstatus;
//    }
public Customer getCustomerByEmail3(String email) {
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
        @SuppressLint("Range") String email1 = cursor.getString(cursor.getColumnIndex(EMAIL));
        // Tạo đối tượng Customer
        customer = new Customer();
    }

    // Đóng cursor và đóng cơ sở dữ liệu
    cursor.close();
    db.close();

    // Trả về đối tượng Customer
    return customer;
}
    public ArrayList<Customer> getLoggedinUserOrder(String email){
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TBL_CUSTOMER + " WHERE " + EMAIL + " = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
        Customer customer = null;

        // Nếu có kết quả từ câu truy vấn
        if (cursor.moveToFirst()) {
            // Lấy thông tin từ cursor
            @SuppressLint("Range") String emails = cursor.getString(cursor.getColumnIndex(EMAIL));


            Customer userInfo = new Customer();

            userInfo.setEmail(emails);

            customers.add(userInfo);
        }

        // Đóng con trỏ và database
        cursor.close();
        sqLiteDatabase.close();

        return customers;
    }

    public int numbOfRowsBlog(){
        Cursor c = getData("SELECT * FROM " + TBL_BLOG);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataBlog() {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        if (numbOfRowsBlog() ==0){
            // Ví dụ 1
            values.put(BLOG_TITLE, "R3cy: Vấn nạn rác thải nhựa và giải pháp tái chế");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.dtt_giangsinh1));
            values.put(BLOG_CREATE_DATE, "2024-03-22");
            values.put(BLOG_AUTHOR, "Hồ Lê Thanh Trúc");
            values.put(BLOG_CONTENT, "Nội dung bài viết 1");
            db.insert(TBL_BLOG, null, values);

            // Ví dụ 2
            values.clear(); // Xóa các giá trị trước đó để chèn giá trị mới
            values.put(BLOG_TITLE, "Tiêu đề bài viết 2");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.dtt_giangsinh3));
            values.put(BLOG_CREATE_DATE, "2024-04-10");
            values.put(BLOG_AUTHOR, "Người viết bài 2");
            values.put(BLOG_CONTENT, "Nội dung bài viết 2");
            db.insert(TBL_BLOG, null, values);
        }

    }
    // Phương thức để lấy tất cả các blog từ cơ sở dữ liệu
    public List<Blog> getAllBlogs() {
        List<Blog> blogList = new ArrayList<>();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TBL_BLOG;
            cursor = getData(query);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ các cột tương ứng trong Cursor
                    @SuppressLint("Range") int blogId = cursor.getInt(cursor.getColumnIndex(BLOG_ID));
                    @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(BLOG_TITLE));
                    @SuppressLint("Range") byte[] thumb = cursor.getBlob(cursor.getColumnIndex(BLOG_THUMB));
                    @SuppressLint("Range") long createDateMillis = cursor.getLong(cursor.getColumnIndex(BLOG_CREATE_DATE));
                    Date createBlogDate = new Date(createDateMillis);
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(BLOG_AUTHOR));
                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(BLOG_CONTENT));

                    // Tạo đối tượng Blog từ dữ liệu lấy được
                    Blog blog = new Blog(blogId, title, author, createBlogDate,thumb, content);
                    blogList.add(blog);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
        } finally {
            // Đóng Cursor
            if (cursor != null) {
                cursor.close();
            }
        }

        return blogList;
    }




}





