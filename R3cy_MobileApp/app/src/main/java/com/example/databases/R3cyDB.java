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
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.models.Address;
import com.example.models.Blog;
import com.example.models.Customer;
import com.example.models.Order;
import com.example.models.Product;
import com.example.models.ProductAtb;
import com.example.models.UserInfo;
import com.example.models.VoucherCheckout;
import com.example.r3cy_mobileapp.R;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


public class R3cyDB extends SQLiteOpenHelper {
    Context context;
    R3cyDB db;
    ArrayList<Product> products;
    public static final String DATABASE_NAME = "r3cy_database.db";
    // Phiên bản cơ sở dữ liệu
    public static final int DATABASE_VERSION = 2;

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
    public static final String COUPON_ORDER = "couponOrder";
    public static final String COUPON_SHIPPING = "couponShipping";
    public static final String SHIPPING_FEE = "Shippingfee";
    public static final String TOTAL_AMOUNT = "TotalAmount";
    public static final String PAYMENT_STATUS = "Paymentstatus";
    public static final String ADDRESS_ID = "AddressID";
    public static final String CANCELLATION_REASON = "CancellationReason";

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
    public static final String CUSTOMER_IDS = "Customer_IDs";

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
            CANCELLATION_REASON + " TEXT DEFAULT NULL," +
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
            BLOG_CONTENT + " TEXT" +
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
    public Cursor getData(String sql) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        } catch (Exception e) {
            return null;
        }
    }

    //    INSERT. UPDATE, DELETE
    public boolean execSql(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
            return false;
        }

    }
//    Ktra table có dữ liệu không

    //    Kiểm tra bảng Coupon
    public int numbOfRowsCoupon() {
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

    public int numbOfRowsCart() {
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


    public int numbOfRowsOrder() {
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
    public void createSampleDataOrder() {
        if (numbOfRowsOrder() == 0) {
            execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 1, '14-04-2024', 'COD', null, null, 236000, 'Đang giao', 'Che tên sản phẩm', '15-04-2024', '0', 35000, 20000, 200000, 0, null, null)");
            execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 1, '14-04-2024', 'COD', null, null, 236000, 'Hoàn thành', 'Che tên sản phẩm', '15-04-2024', '0', 35000, 20000, 200000, 0, null, null)");
            execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 2, '15-04-2024', 'COD', null, null, 232000, 'Chờ xử lý', 'Che tên sản phẩm', '16-04-2024', '0', 20000,20000, 220000, 0, null, null)");
            execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 3, '16-04-2024', 'COD', null, null, 205000, 'Chờ lấy hàng', 'Che tên sản phẩm', '17-04-2024', '0', 25000,20000, 210000, 0, null, null)");
            execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 4, '17-04-2024', 'COD', null, null, 135000, 'Hoàn thành', 'Che tên sản phẩm', '18-04-2024', '0', 15000,20000, 250000, 0, null, null)");
            execSql("INSERT INTO " + TBl_ORDER + " VALUES(null, 5, '18-04-2024', 'COD', null, null, 165000, 'Đang giao', 'Che tên sản phẩm', '19-04-2024', '0', 35000,20000, 260000, 0, null, null)");
        }
    }

    public int numbOfRowsOrderLine() {
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
    public void createSampleDataOrderLine() {
        if (numbOfRowsOrderLine() == 0){
            execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 1, 1, 160000, 4)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 2, 2, 150000, 3)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 3, 3, 140000, 3)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 4, 4, 150000, 2)");
        execSql("INSERT INTO " + TBl_ORDER_LINE + " VALUES(null, 5, 5, 170000, 1)");
        }
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

    public int numbOfRowsAddress() {
        Cursor c = getData("SELECT * FROM " + TBl_ADDRESS);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataAddress() {
        if (numbOfRowsAddress() == 0) {
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
            String[] selectionArgs = {String.valueOf(customerId)};
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
    public int numbOfRowsCustomer() {
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

    public void createSampleDataCustomer() {
        if (numbOfRowsCustomer() == 0) {
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
    public int numbOfRowsProduct() {
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

    public void createSampleProduct() {
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
    public int numbOfRowsDiscuss() {
        Cursor c = getData("SELECT * FROM " + TBl_DISCUSS);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataDiscuss() {
        if (numbOfRowsCustomer() == 0) {
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
    public int numbOfRowsFeedback() {
        Cursor c = getData("SELECT * FROM " + TBl_FEEDBACK);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataFeedback() {
        if (numbOfRowsCustomer() == 0) {
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


    public ArrayList<UserInfo> getLoggedinUserDetails(String email) {
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
            cursor.close();
        }

        // Đóng con trỏ và database
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

    public boolean upDateUserProfile(String email, String fullname1,
                                     String username1, String phone1,
                                     String gender1, String birthday1) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FULLNAME, fullname1);
        values.put(USERNAME, username1);
        values.put(PHONE, phone1);
        values.put(EMAIL, email);
        values.put(GENDER, gender1);
        values.put(BIRTHDAY, birthday1);

        int i = sqLiteDatabase.update(TBL_CUSTOMER, values, "EMAIL=?", new String[]{email});
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean upDateUserImg(byte[] imgview, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_THUMB, imgview);

        int i = sqLiteDatabase.update(TBL_CUSTOMER, values, "EMAIL=?", new String[]{email});
        if (i > 0) {
            return true;
        } else {
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

    public ArrayList<Customer> getLoggedinUserOrder(String email) {
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

    public int numbOfRowsBlog() {
        Cursor c = getData("SELECT * FROM " + TBL_BLOG);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public void createSampleDataBlog() {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        if (numbOfRowsBlog() == 0) {
            // Ví dụ 1
            values.put(BLOG_TITLE, "Xu Hướng Mới: Sử Dụng Sản Phẩm Nhựa Tái Chế - Điểm Nhấn Cho Tương Lai Bền Vững \uD83C\uDF0E♻\uFE0F");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.dtt_dongho2));
            values.put(BLOG_CREATE_DATE, "2024-03-22");
            values.put(BLOG_AUTHOR, "Hồ Lê Thanh Trúc");
            values.put(BLOG_CONTENT, "Chào các bạn thân yêu của R3cy! Hôm nay, chúng ta sẽ cùng nhau khám phá ý nghĩa sâu sắc đằng sau việc tái chế nhựa - một hành động nhỏ có thể tạo nên sự thay đổi lớn đối với hành tinh mà chúng ta đang sống. \uD83C\uDF31♻\uFE0F\n" +
                    "\n" +
                    "1. Bảo Vệ Môi Trường: Mỗi tấm vật liệu nhựa tái chế không chỉ là việc giảm lượng rác thải mà còn là cơ hội để giữ cho đại dương trong xanh, rừng già xanh mướt và không khí sạch sẽ. Chúng ta đang góp phần vào việc giữ gìn vẻ đẹp tự nhiên của hành tinh chúng ta.\n" +
                    "2. Tiết Kiệm Năng Lượng và Tài Nguyên: Tái chế nhựa giúp tiết kiệm năng lượng và tài nguyên so với việc sản xuất nhựa mới. Bằng cách này, chúng ta đang hỗ trợ sự bền vững và giúp giảm lượng khí thải và ô nhiễm.\n" +
                    "3. Khuyến Khích Sự Sáng Tạo: Tái chế nhựa không chỉ giúp giữ gìn môi trường mà còn khuyến khích sự sáng tạo. Những sản phẩm nhựa tái chế có thể trở thành nguồn cảm hứng cho các nhà thiết kế và nghệ nhân, giúp họ tạo ra những tác phẩm độc đáo và thú vị.\n" +
                    "4. Đào Tạo Cộng Đồng và Tạo Ra Cơ Hội Việc Làm: Việc tái chế nhựa tạo ra cơ hội việc làm cho các cộng đồng địa phương. Đồng thời, nó còn giúp tăng cường nhận thức về vấn đề môi trường và khuyến khích hành động tích cực từ cộng đồng.\n" +
                    "5. Điều Chỉnh Thái Độ và Thay Đổi Lối Sống: Việc tham gia vào quá trình tái chế giúp chúng ta thay đổi lối sống và điều chỉnh thái độ của mình đối với việc sử dụng nhựa. Nó là bước đầu tiên để trở thành người tiêu dùng thông thái, chúng ta không chỉ tôn trọng môi trường mà còn giúp giữ cho hành tinh chúng ta xanh sạch và tươi mới.\n" +
                    "\n" +
                    "Hãy cùng nhau lan tỏa thông điệp về ý nghĩa của việc tái chế nhựa. Mỗi hành động nhỏ từ bạn đều có ý nghĩa lớn đối với tương lai của chúng ta. Hãy hợp tác và chia sẻ để tạo nên một môi trường sống bền vững cho tất cả mọi người!\n" +
                    "#TáiChếNhựa #BảoVệMôiTrường #R3cyChămSócHànhTinh \uD83C\uDF3F♻\uFE0F\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Xu Hướng Mới: Sử Dụng Sản Phẩm Nhựa Tái Chế - Điểm Nhấn Cho Tương Lai Bền Vững \uD83C\uDF0E♻\uFE0F\n" +
                    "\n" +
                    "Xin chào cả nhà, hôm nay R3cy xin được chia sẻ với bạn về một xu hướng quan trọng đang được lan tỏa trên toàn cầu và cả tại Việt Nam - sử dụng sản phẩm từ nhựa tái chế. Đây là một xu hướng đáng kể trong việc bảo vệ môi trường và xây dựng một tương lai bền vững.\n" +
                    "\uD83C\uDF3F\uD83D\uDC9A Trên toàn cầu, nhận thức về vấn đề ô nhiễm nhựa và tác động tiêu cực của nó đến môi trường đang gia tăng. Vì vậy, người tiêu dùng ngày càng quan tâm và chọn lựa sử dụng sản phẩm từ nhựa tái chế. Điều này không chỉ giúp giảm thiểu lượng rác thải nhựa và ô nhiễm môi trường, mà còn tạo ra một chuỗi cung ứng tái chế nhựa mạnh mẽ.\n" +
                    "\uD83C\uDF0D Ở Việt Nam, xu hướng này cũng đang phát triển mạnh mẽ. Ngày càng có nhiều công ty và thương hiệu nhận thức về tầm quan trọng của việc sử dụng lại nhựa đã qua sử dụng và tạo ra các sản phẩm tái chế. Chúng ta có thể thấy sự xuất hiện của các sản phẩm từ nhựa tái chế trong nhiều lĩnh vực, từ đồ dùng gia đình, đồ chơi, túi xách, đến sản phẩm nội thất và xây dựng. Điều này làm cho việc sử dụng sản phẩm từ nhựa tái chế trở thành một phong cách sống mới, giúp mọi người thể hiện sự quan tâm và đóng góp vào việc bảo vệ môi trường.\n" +
                    "✨\uD83D\uDCA1 Lợi ích của việc sử dụng sản phẩm từ nhựa tái chế rất đa dạng. Đầu tiên, nó giúp giảm lượng rác thải nhựa và ngăn chặn ô nhiễm môi trường. Thay vì chế tạo nhựa mới, sử dụng lại nhựa đã qua sử dụng giúp tiết kiệm tài nguyên và năng lượng. Ngoài ra, sản phẩm từ nhựa tái chế thường có chất lượng tương đương và giá cả phải chăng. Điều này không chỉ giúp người tiêu dùng tiết kiệm chi phí mà còn khuyến khích sự phát triển của ngành công nghiệp tái chế.\n" +
                    "\uD83C\uDF3F\uD83C\uDF0D R3cy cam kết đóng góp vào xu hướng sử dụng sản phẩm từ nhựa tái chế. Chúng tôi không chỉ tạo ra những sản phẩm chất lượng cao từ nhựa tái chế, mà còn khuyến khích mọi người tham gia vào cuộc sống bền vững thông qua việc sử dụng các sản phẩm thân thiện với môi trường.\n" +
                    "\uD83C\uDF31 Hãy cùng nhau tham gia vào xu hướng sử dụng sản phẩm từ nhựa tái chế để bảo vệ môi trường và xây dựng một tương lai bền vững. Hãy là những người tiêu dùng thông minh và lựa chọn sản phẩmtừ nhựa tái chế để tạo ra sự thay đổi tích cực cho hành tinh của chúng ta.\n" +
                    "Hãy ghé thăm trang web của R3cy ngay hôm nay để khám phá các sản phẩm từ nhựa tái chế và tham gia vào cuộc sống bền vững! Cùng nhau, chúng ta có thể tạo ra sự khác biệt và bảo vệ môi trường cho thế hệ tương lai.\n" +
                    "#R3cy #NhựaTáiChế #BảoVệMôiTrường #SốngBềnVững\n");
            db.insert(TBL_BLOG, null, values);

            // Ví dụ 2
            values.clear(); // Xóa các giá trị trước đó để chèn giá trị mới
            values.put(BLOG_TITLE, "Tất tần tật thứ từ nhựa tái chế với R3cy - Giải pháp bảo vệ môi trường");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.dtt_giangsinh3));
            values.put(BLOG_CREATE_DATE, "2024-04-10");
            values.put(BLOG_AUTHOR, "Lê Thị Tuyết Anh");
            values.put(BLOG_CONTENT, "Với sứ mệnh góp phần giảm thiểu rác thải nhựa và bảo vệ môi trường.R3cy là thương hiệu phụ kiện, sản phẩm gia dụng, trang trí nhà cửa, góc học tập, làm việc, tiệm cafe, cửa hàng quần áo,... từ nhựa tái chế. Các sản phẩm của R3cy được làm từ những nguyên liệu nhựa tái chế, mang đến vẻ đẹp độc đáo và tinh tế, đồng thời góp phần bảo vệ môi trường.\n" +
                    "Nạn rác thải nhựa đang ngày càng nghiêm trọng\n" +
                    "Rác thải nhựa là một vấn đề nghiêm trọng đang đe dọa môi trường và sức khỏe con người. Theo thống kê của Tổ chức Bảo vệ Môi trường (EPA), mỗi năm có khoảng 8,3 triệu tấn rác thải nhựa đổ ra đại dương. Rác thải nhựa có thể tồn tại trong môi trường hàng trăm năm, gây ô nhiễm môi trường đất, nước, không khí, và đe dọa đến hệ sinh thái.\n" +
                    "Phụ kiện, sản phẩm decor từ nhựa tái chế - Giải pháp bảo vệ môi trường\n" +
                    "Phụ kiện, đồ decor từ nhựa tái chế là những sản phẩm được làm từ những nguyên liệu nhựa tái chế, mang đến vẻ đẹp độc đáo và tinh tế, đồng thời góp phần giảm thiểu rác thải nhựa và bảo vệ môi trường. \n" +
                    "Ưu điểm của phụ kiện, đồ decor từ nhựa tái chế\n" +
                    "Thân thiện với môi trường: Phụ kiện, đồ decor từ nhựa tái chế được làm từ những nguyên liệu nhựa tái chế, góp phần giảm thiểu rác thải nhựa.\n" +
                    "Độc đáo và tinh tế: Phụ kiện, đồ decor từ nhựa tái chế được thiết kế với nhiều kiểu dáng, mẫu mã đa dạng, mang đến vẻ đẹp độc đáo và tinh tế.\n" +
                    "Giá thành hợp lý: Phụ kiện, đồ decor từ nhựa tái chế có giá thành hợp lý, phù hợp với túi tiền của nhiều người.\n" +
                    "Các sản phẩm phụ kiện, đồ decor từ nhựa tái chế của R3cy\n" +
                    "R3cy là thương hiệu phụ kiện, đồ decor từ nhựa tái chế, với sứ mệnh góp phần giảm thiểu rác thải nhựa và bảo vệ môi trường. Các sản phẩm của R3cy được làm từ những nguyên liệu nhựa tái chế, mang đến vẻ đẹp độc đáo và tinh tế, đồng thời góp phần bảo vệ môi trường.\n" +
                    "\n" +
                    "\n" +
                    "Phụ kiện thời trang: R3cy cung cấp các sản phẩm phụ kiện thời trang từ nhựa tái chế, bao gồm vòng tay, bông tai, móc khóa,...\n" +
                    "Đồ decor: R3cy cung cấp các sản phẩm đồ decor từ nhựa tái chế, bao gồm khung ảnh, đồng hồ treo tường, bảng hiệu cửa hàng,...\n" +
                    "\n" +
                    "Sản phẩm phụ kiện, đồng hồ từ nhựa tái chế của R3cy\n" +
                    "\n" +
                    "Sản phẩm bông tai, giá đỡ điện thoại từ nhựa tái chế của R3cy\n" +
                    "Phụ kiện, đồ decor từ nhựa tái chế là một giải pháp bảo vệ môi trường hiệu quả. Với vẻ đẹp độc đáo và tinh tế, các sản phẩm của R3cy là lựa chọn phù hợp cho những ai yêu thích thời trang và muốn góp phần bảo vệ môi trường.\n" +
                    "\n" +
                    "Từ khóa:phụ kiện nhựa tái chế, R3cy, tái chế nhựa, bảo vệ môi trường\n");
            db.insert(TBL_BLOG, null, values);

            // Ví dụ 3
            values.clear();
            values.put(BLOG_TITLE, "Sứ mệnh R3cy");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.dtt_giangsinh1));
            values.put(BLOG_CREATE_DATE, "2024-03-22");
            values.put(BLOG_AUTHOR, "Hồ Lê Thanh Trúc");
            values.put(BLOG_CONTENT, "R3cy theo đuổi giá trị cốt lõi là phụng sự môi trường và cộng đồng, với tôn chỉ hạn chế tối đa tổn hại đến môi trường và hạn chế tối đa dấu chân carbon trong quá trình sản xuất.\n" +
                    "\n" +
                    "Theo số liệu thống kê từ Bộ Tài nguyên và Môi trường (TN&MT), mỗi năm tại Việt Nam có khoảng 1,8 triệu tấn rác thải nhựa thải ra môi trường, 0,28 triệu đến 0,73 triệu tấn trong số đó bị thải ra biển – nhưng chỉ 27% trong số đó được tái chế, tận dụng bởi các cơ sở, doanh nghiệp. Điều đáng nói là việc xử lý và tái chế rác thải nhựa ở Việt Nam còn nhiều hạn chế. Theo thống kê, có đến 90% rác thải nhựa được xử lý theo cách chôn, lấp, đốt. Phương pháp này không chỉ gây ô nhiễm môi trường mà còn làm lãng phí tài nguyên thiên nhiên. Tại TP.Hồ Chí Minh số lượng rác thải nhựa mỗi ngày đưa ra môi trường lên đến 80 tấn. hành phố đã có nhiều giải pháp để giảm thiểu rác thải nhựa, chẳng hạn như thu gom rác thải nhựa tái chế, khuyến khích người dân sử dụng túi vải, ống hút giấy,... Tuy nhiên, những giải pháp này vẫn chưa mang lại hiệu quả cao.\n" +
                    "\n" +
                    "\n" +
                    "R3cy bắt đầu hành trình mang sản phẩm từ nhựa tái chế đến khách hàng với mong muốn góp phần bảo vệ môi trường. Chúng tôi nhìn thấy môi trường sống xung quanh đang bị tổn hại trầm trọng bởi rác thải nhựa, và chúng tôi muốn làm điều gì đó để thay đổi. Tái chế nhựa là một cách hiệu quả để giảm thiểu lượng rác thải nhựa ra môi trường. Bằng tái chế, R3cy đã mang đến cho những loại nhựa cũ, đã qua sử dụng tới một cuộc sống mới, thay vì để chúng vùi lấp dưới những hố sâu hàng chục, trăm năm.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Với sứ mệnh đưa đến người tiêu dùng sản phẩm chất lượng, gia tăng giá trị tái chế của nhựa, nguyên liệu thân thiện môi trường và sản xuất tại Việt Nam. Bên cạnh đó, khuyến khích mọi người sử dụng sản phẩm tái chế, tham gia tái chế nhựa và các hoạt động về môi trường.\n");
            db.insert(TBL_BLOG, null, values);

            // Ví dụ 4
            values.clear(); // Xóa các giá trị trước đó để chèn giá trị mới
            values.put(BLOG_TITLE, "Đồ thủ công từ nhựa tái chế: Tại sao không?");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.dtt_giangsinh2));
            values.put(BLOG_CREATE_DATE, "2024-04-10");
            values.put(BLOG_AUTHOR, "Lê Thị Tuyết Anh");
            values.put(BLOG_CONTENT, "Tác động mạnh mẽ của việc sử dụng nhựa đối với môi trường đã trở thành một vấn đề quan trọng trong thời gian gần đây. Chúng ta đã chứng kiến sự gia tăng về ý thức bảo vệ môi trường và nỗ lực tái chế nhựa để giảm thiểu tác động tiêu cực lên hành tinh.\n" +
                    "Việc sử dụng các sản phẩm thủ công tái chế nhựa không chỉ mang lại sự độc đáo và cá nhân hóa cho người sử dụng, mà còn có tác động tích cực đến môi trường. Bằng cách ủng hộ và mua sắm các sản phẩm tái chế từ nhựa, bạn đang góp phần vào việc giảm thiểu lượng rác thải nhựa và hỗ trợ cho một cộng đồng thân thiện với môi trường. Bạn cũng có cơ hội chia sẻ thông điệp về sự quan tâm đến bảo vệ môi trường với mọi người xung quanh.\n" +
                    "\n" +
                    "\n" +
                    "Đồ thủ công từ việc tái chế nhựa không chỉ là một xu hướng mới mà còn là một nỗ lực quan trọng trong việc xây dựng một tương lai bền vững. Trong tương lai, chúng ta hy vọng sẽ có nhiều doanh nghiệp với mô hình kinh doanh như chúng tôi, tạo ra cơ hội cho những người yêu thích đồ thủ công và đồ handmade để thể hiện sự sáng tạo của mình và đồng thời giúp bảo vệ môi trường. Việc tái chế nhựa không chỉ giúp giảm thiểu lượng rác thải mà còn khuyến khích sự tận dụng lại tài nguyên và giúp chúng ta xây dựng một thế giới bền vững hơn.\n" +
                    "Hãy tham gia vào các kênh cộng đồng của R3cy và khám phá thế giới đồ thủ công từ việc tái chế nhựa. Bạn sẽ không chỉ có được những sản phẩm độc đáo và sáng tạo mà còn góp phần vào việc bảo vệ môi trường và xây dựng một tương lai tươi sáng hơn cho thế hệ mai sau. \uD83D\uDC9A\uD83D\uDCF1\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Những bước đầu trong việc tái chế nhựa tại nhà: Đóng góp của mỗi cá nhân vào bảo vệ môi trường\n" +
                    "\n" +
                    "\n" +
                    "Trong thời đại hiện nay, việc bảo vệ môi trường trở thành một vấn đề cấp bách. Một trong những cách đơn giản và hiệu quả để đóng góp cho môi trường là tái chế nhựa. Trong bài viết này, chúng ta sẽ khám phá những bước đầu trong việc tái chế nhựa tại nhà và cách mỗi cá nhân có thể đóng góp vào bảo vệ môi trường.\n" +
                    "\n" +
                    "Tìm hiểu về các loại nhựa tái chế: Đầu tiên, hãy tìm hiểu về các loại nhựa có thể tái chế và cách phân loại chúng. Mỗi loại nhựa có một mã số đặc trưng, ví dụ như mã số PET (Polyethylene terephthalate) cho chai nước và mã số HDPE (High-Density Polyethylene) cho bình chứa. Hiểu rõ về các loại nhựa này sẽ giúp bạn phân loại và tái chế chúng một cách hiệu quả.\n" +
                    "\n" +
                    "Phân loại và thu thập nhựa: Bắt đầu bằng việc phân loại và thu thập nhựa tại nhà. Hãy có các thùng chứa riêng biệt cho từng loại nhựa, và đảm bảo rằng nhựa đã được rửa sạch và làm khô trước khi đặt vào thùng chứa. Điều này giúp tiết kiệm công sức cho quá trình tái chế sau này.\n" +
                    "\n" +
                    "Sử dụng lại và tái chế nhựa: Có nhiều cách để sử dụng lại và tái chế nhựa tại nhà. Bạn có thể tạo ra các đồ thủ công như bình hoa, hộp đựng, khay, vòng cổ, hoặc thậm chí là đồ trang trí nhà cửa từ những vật liệu nhựa tái chế. Sáng tạo và tìm kiếm những ý tưởng trên Internet có thể giúp bạn biến đồ thải nhựa thành những sản phẩm có giá trị.\n" +
                    "\n" +
                    "Khám phá cộng đồng tái chế: Tham gia cộng đồng tái chế địa phương để chia sẻ ý tưởng và kinh nghiệm với những người khác. Có thể có những nhóm hoặc tổ chức trong khu vực của bạn đã tổ chức các hoạt động tái chế nhựa hoặc dự án cộng đồng. Tham gia vào những hoạt động này sẽ giúp bạn học hỏi và tạo ra sự lan tỏa ý thức bảo vệ môi trường lớn hơn.\n" +
                    "\n" +
                    "Lan tỏa thông điệp: Hãy lan tỏa thông điệp về tái chế nhựa và ý thức bảo vệ môi trường cho bạn bè, gia đình và cộng đồng của bạn. Chia sẻ những bước đơn giản để tái chế nhựa và lợi ích của việc làm này đối với môi trường. Một hành động nhỏ có thể truyền cảm hứng cho người khác và tạo ra tác động lớn hơn đến sự bảo vệ môi trường.\n" +
                    "\n" +
                    "Tái chế nhựa tại nhà không chỉ giúp giảm ô nhiễm môi trường mà còn mang lại lợi ích kinh tế và sáng tạo cho mỗi cá nhân. Bằng cách thực hiện những bước đơn giản này, chúng ta có thể đóng góp vào việc bảo vệ môi trường và hướng tới một tương lai bền vững hơn. Bắt đầu ngay hôm nay và hãy trở thành một phần của cuộc cách mạng tái chế nhựa!\n");
            db.insert(TBL_BLOG, null, values);

            // Ví dụ 5
            values.put(BLOG_TITLE, "Tái Chế Nhựa: Hành Động Nhỏ, Ý Nghĩa Lớn Cho Hành Tinh Của Chúng Ta \uD83C\uDF0D♻\uFE0F");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.pk_bongtai_hcn3));
            values.put(BLOG_CREATE_DATE, "2024-03-22");
            values.put(BLOG_AUTHOR, "Đặng Thị Thanh Trúc");
            values.put(BLOG_CONTENT, "Chào các bạn thân yêu của R3cy! Hôm nay, chúng ta sẽ cùng nhau khám phá ý nghĩa sâu sắc đằng sau việc tái chế nhựa - một hành động nhỏ có thể tạo nên sự thay đổi lớn đối với hành tinh mà chúng ta đang sống. \uD83C\uDF31♻\uFE0F\n" +
                    "\n" +
                    "\n" +
                    "1. Bảo Vệ Môi Trường: Mỗi tấm vật liệu nhựa tái chế không chỉ là việc giảm lượng rác thải mà còn là cơ hội để giữ cho đại dương trong xanh, rừng già xanh mướt và không khí sạch sẽ. Chúng ta đang góp phần vào việc giữ gìn vẻ đẹp tự nhiên của hành tinh chúng ta.\n" +
                    "\n" +
                    "2. Tiết Kiệm Năng Lượng và Tài Nguyên: Tái chế nhựa giúp tiết kiệm năng lượng và tài nguyên so với việc sản xuất nhựa mới. Bằng cách này, chúng ta đang hỗ trợ sự bền vững và giúp giảm lượng khí thải và ô nhiễm.\n" +
                    "\n" +
                    "3. Khuyến Khích Sự Sáng Tạo: Tái chế nhựa không chỉ giúp giữ gìn môi trường mà còn khuyến khích sự sáng tạo. Những sản phẩm nhựa tái chế có thể trở thành nguồn cảm hứng cho các nhà thiết kế và nghệ nhân, giúp họ tạo ra những tác phẩm độc đáo và thú vị.\n" +
                    "\n" +
                    "4. Đào Tạo Cộng Đồng và Tạo Ra Cơ Hội Việc Làm: Việc tái chế nhựa tạo ra cơ hội việc làm cho các cộng đồng địa phương. Đồng thời, nó còn giúp tăng cường nhận thức về vấn đề môi trường và khuyến khích hành động tích cực từ cộng đồng.\n" +
                    "\n" +
                    "5. Điều Chỉnh Thái Độ và Thay Đổi Lối Sống: Việc tham gia vào quá trình tái chế giúp chúng ta thay đổi lối sống và điều chỉnh thái độ của mình đối với việc sử dụng nhựa. Nó là bước đầu tiên để trở thành người tiêu dùng thông thái, chúng ta không chỉ tôn trọng môi trường mà còn giúp giữ cho hành tinh chúng ta xanh sạch và tươi mới.\n" +
                    "\n" +
                    "Hãy cùng nhau lan tỏa thông điệp về ý nghĩa của việc tái chế nhựa. Mỗi hành động nhỏ từ bạn đều có ý nghĩa lớn đối với tương lai của chúng ta. Hãy hợp tác và chia sẻ để tạo nên một môi trường sống bền vững cho tất cả mọi người!\n" +
                    "#TáiChếNhựa #BảoVệMôiTrường #R3cyChămSócHànhTinh \uD83C\uDF3F♻\uFE0F\n" +
                    "\n");
            db.insert(TBL_BLOG, null, values);

            // Ví dụ 6
            values.put(BLOG_TITLE, "R3cy: Vấn nạn rác thải nhựa và giải pháp tái chế \uD83C\uDF0D♻\uFE0F");
            values.put(BLOG_THUMB, convertPhoto(context, R.drawable.pk_mockhoakos2));
            values.put(BLOG_CREATE_DATE, "2024-03-22");
            values.put(BLOG_AUTHOR, "Nguyễn Thảo Nguyên");
            values.put(BLOG_CONTENT, "Môi trường hiện nay đang bị ảnh hưởng nặng nề bởi rác thải, đặc biệt là rác thải nhựa. Để khắc phục điều đó nhiều tổ chức vì cộng đồng đã được lập ra nhằm kêu gọi mọi người nâng cao ý thức, trách nhiệm tái chế, tái sử dụng nhựa đã qua sử dụng. Vậy Recy đã làm gì để hạn chế rác thải nhựa và bảo vệ môi trường? Cùng chúng tôi tìm hiểu qua bài viết này nhé!!\n" +
                    "\n" +
                    "1. Thực trạng về rác thải nhựa hiện nay\n" +
                    "Rác thải nhựa được định nghĩa là những sản phẩm được làm từ nhựa, khó phân hủy trong môi trường và đã qua sử dụng, bao gồm chai lọ, túi ni lông, đồ chơi cũ,…\n" +
                    "\n" +
                    "Mỗi năm, theo thống kê thế giới thải ra hơn 300 triệu tấn rác thải nhựa, trong đó có 8 triệu tấn chất thải nhựa được đổ ra đại dương. Theo báo cáo của Liên hợp quốc, lượng rác thải nhựa mỗi năm đủ bao quanh trái đất 4 lần.\n" +
                    "Theo kết quả nghiên cứu của Đại học Georgia, Việt Nam là 1 trong 20 nước thải rác nhiều nhất, và là 1 trong 5 nước có lượng rác thải nhựa ra đại dương lớn nhất thế giới. Mỗi năm Việt Nam thải khoảng 1,8 triệu tấn rác thải nhựa ra biển.\n" +
                    "\n" +
                    "Căn cứ vào mức độ sử dụng hiện nay, các chuyên gia cho rằng sẽ có hơn 13 tỷ tấn rác thải nhựa được chôn lấp trong các bãi rác hoặc đổ xuống biển gây ô nhiễm môi trường nghiêm trọng.\n" +
                    "2. Tác hại, hậu quả của rác thải nhựa đến môi trường\n" +
                    "Rác thải nhựa rất khó phân hủy trong môi trường tự nhiên. Mỗi loại chất nhựa sẽ có thời gian phân hủy khác nhau từ vài chục năm cho đến hàng nghìn năm. Ví dụ chai nhựa sẽ phân hủy sau 450 – 1000 năm, nắp chai nhựa, ống hút sẽ phân hủy sau 100 – 500 năm…\n" +
                    "Các loài động vật sau khi ăn phải rác thải nhựa có thể chết, dẫn đến tuyệt chủng, gây mất cân bằng hệ sinh thái\n" +
                    "Rác thải nhựa không được xử lý đúng cách sẽ ảnh hưởng trực tiếp đến không khí và môi trường nước\n" +
                    "Khi đốt: chất thải nhựa sẽ sinh ra khí độc đi-ô-xin, furan gây ô nhiễm không khí, gây ngộ độc, ảnh hưởng đến tuyến nội tiết, làm giảm hệ miễn dịch, gây ung thư\n" +
                    "Khi chôn lấp: rác thải nhựa sẽ làm cho đất không giữ được nước, dinh dưỡng, gây cản trở đến sự sinh trưởng của cây trồng\n" +
                    "Rác thải nhựa gây ra tình trạng “ô nhiễm trắng” tại các điểm du lịch, gây mất mỹ quan, ảnh hưởng đến sự nghỉ ngơi, thư giãn của con người.\n" +
                    "\n" +
                    "\n" +
                    "Có thể thấy ảnh hưởng của rác thải nhựa vô cùng nghiêm trọng, ảnh hưởng sâu rộng đến tất cả sinh vật trong hệ sinh thái của chúng ta. Vì vậy không ít các hoạt động đã được tổ chức thường niên nhằm mục đích thu gom rác thải nhựa, nhiều biển báo, khẩu ngữ “phân loại rác đúng quy định”, “tái chế, tái sử dụng” đã xuất hiện ngày càng nhiều nhằm nhắc nhở mọi người nêu cao ý thức, trách nhiệm bảo vệ môi trường và cộng đồng.\n" +
                    "\n" +
                    "3. R3cy trong hành trình tái chế rác thải nhựa, góp phần bảo vệ môi trường\n" +
                    "\n" +
                    "Giờ đây những rác thải nhựa đã qua sử dụng thay vì bỏ đi gây hại cho môi trường đã được chúng tôi tái sinh một vòng đời mới. Những phụ kiện xinh xắn, những vật dụng nội thất mang lại cho chúng ta những sản phẩm trang trí và giúp ích cho cuộc sống hằng ngày, hơn thế nữa, đây là cách trực tiếp mang tới những giá trị tốt đẹp cho người dùng, cho xã hội và cho môi trường. Nói cách khác, sử dụng đồ tái chế R3cy cũng là một cách bạn góp phần tái chế, giảm thiểu rác thải nhựa ra môi trường.\n" +
                    "\n" +
                    " \n" +
                    "Hành trình phát triển bền vững, lý tưởng ghi dấu những bước chân xanh của R3cy còn dài và nhiều thử thách. Chúng tôi tin rằng hành trình đó luôn có sự đồng hành và ủng hộ của các bạn như cách các bạn đang nỗ lực vì một môi trường xanh hơn, sạch hơn mỗi ngày. \n" +
                    "\n" +
                    "Thế hệ trẻ hôm nay là tôi, là bạn, là tất cả chúng ta hãy cùng chung tay hành động ngay hôm nay để góp phần bảo vệ thiên nhiên, phục hồi hệ sinh thái, bảo vệ môi trường sống của chính chúng ta cũng như để lại màu xanh bền vững cho thế hệ tương lai.\n");

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
                    @SuppressLint("Range") String createDateStr = cursor.getString(cursor.getColumnIndex(BLOG_CREATE_DATE));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date createBlogDate = dateFormat.parse(createDateStr);
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(BLOG_AUTHOR));
                    @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(BLOG_CONTENT));

                    // Tạo đối tượng Blog từ dữ liệu lấy được
                    Blog blog = new Blog(blogId, title, author, createBlogDate, thumb, content);
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

    public Blog getBlogById(int blogId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Blog blog = null;
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TBL_BLOG + " WHERE " + BLOG_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(blogId)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy dữ liệu từ các cột tương ứng trong Cursor
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(BLOG_TITLE));
                @SuppressLint("Range") byte[] thumb = cursor.getBlob(cursor.getColumnIndex(BLOG_THUMB));
                @SuppressLint("Range") String createDateStr = cursor.getString(cursor.getColumnIndex(BLOG_CREATE_DATE));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date createBlogDate = dateFormat.parse(createDateStr);
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(BLOG_AUTHOR));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(BLOG_CONTENT));

                // Tạo đối tượng Blog từ dữ liệu lấy được
                blog = new Blog(blogId, title, author, createBlogDate, thumb, content);
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

        return blog;
    }

    public VoucherCheckout getCouponById(int couponId) {
        SQLiteDatabase db = getReadableDatabase();
        VoucherCheckout coupon = null;
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TBl_COUPON + " WHERE " + COUPON_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(couponId)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy dữ liệu từ cột trong Cursor và tạo đối tượng Coupon
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COUPON_ID));
                @SuppressLint("Range") String code = cursor.getString(cursor.getColumnIndex(COUPON_CODE));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COUPON_TITLE));
                @SuppressLint("Range") int scoreMin = cursor.getInt(cursor.getColumnIndex(SCORE_MIN));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(COUPON_TYPE));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(COUPON_CATEGORY));
                @SuppressLint("Range") String validDateStr = cursor.getString(cursor.getColumnIndex(VALID_DATE));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date validDate = dateFormat.parse(validDateStr);
                @SuppressLint("Range") String expireDateStr = cursor.getString(cursor.getColumnIndex(EXPIRE_DATE));
                Date expireDate = dateFormat.parse(expireDateStr);
                @SuppressLint("Range") double minOrderValue = cursor.getDouble(cursor.getColumnIndex(MIN_ORDER_VALUE));
                @SuppressLint("Range") double maxDiscount = cursor.getDouble(cursor.getColumnIndex(MAXIMUM_DISCOUNT));
                @SuppressLint("Range") double value = cursor.getDouble(cursor.getColumnIndex(COUPON_VALUE));
                @SuppressLint("Range") int maxUsers = cursor.getInt(cursor.getColumnIndex(MAXIMUM_USERS));
                @SuppressLint("Range") String customerIdsStr = cursor.getString(cursor.getColumnIndex(CUSTOMER_IDS));
                ArrayList<Integer> customerIds = parseCustomerIdsFromString(customerIdsStr);

                // Khởi tạo đối tượng Coupon với dữ liệu từ cơ sở dữ liệu
                coupon = new VoucherCheckout(id, code, title, scoreMin, type, category, validDate, expireDate, minOrderValue, maxDiscount, value, maxUsers, customerIds, true, true);

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
        return coupon;

    }

    public long insertDataFeedback(int productId, String content, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FEEDBACK_PRODUCT_ID, productId);
        values.put(FEEDBACK_CONTENT, content);
        values.put(FEEDBACK_RATING, rating);
//        values.put(FEEDBACK_CUSTOMER_ID, customerId);

        long newRowId = db.insert(TBl_FEEDBACK, null, values);
        db.close();
        return newRowId;
    }
    @SuppressLint("Range")
    public int getProductIdFromProductName(String productName) {
        SQLiteDatabase db = getReadableDatabase();
        int productId = -1;
        String query = "SELECT " + R3cyDB.PRODUCT_ID + " FROM " + R3cyDB.TBl_PRODUCT + " WHERE " + R3cyDB.PRODUCT_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{productName});

        if (cursor != null && cursor.moveToFirst()) {
            productId = cursor.getInt(cursor.getColumnIndex(R3cyDB.PRODUCT_ID));
            cursor.close();
        }
        db.close();
        return productId;// Close the database
    }
    @SuppressLint("Range")
    public  int getOrderByID (int OrderId){
        SQLiteDatabase db = getReadableDatabase();
        Order order = null;
        Cursor cursor = null;
//        try {
            int orderId = -1;
            String query = "SELECT * FROM " + TBl_ORDER + " WHERE " + ORDER_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(OrderId)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy dữ liệu từ cột trong Cursor và tạo đối tượng
                orderId = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_ID));
//                @SuppressLint("Range") int OrderLineID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_ID));
//                @SuppressLint("Range") int OrderLineProductID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_LINE_PRODUCT_ID));
//                @SuppressLint("Range") double OrderSalePrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.ORDER_SALE_PRICE));
//                @SuppressLint("Range") double ProductPrice = cursor.getDouble(cursor.getColumnIndex(R3cyDB.PRODUCT_PRICE));
//                @SuppressLint("Range") String Quantity = cursor.getString(cursor.getColumnIndex(R3cyDB.QUANTITY));
//                @SuppressLint("Range") int OrderCustomerID = cursor.getInt(cursor.getColumnIndex(R3cyDB.ORDER_CUSTOMER_ID));
//                @SuppressLint("Range") double TotalOrderValue = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_ORDER_VALUE));
//                @SuppressLint("Range") String OrderStatus = cursor.getString(cursor.getColumnIndex(R3cyDB.ORDER_STATUS));
//                @SuppressLint("Range") double TotalAmount = cursor.getDouble(cursor.getColumnIndex(R3cyDB.TOTAL_AMOUNT));
//                @SuppressLint("Range") byte[] ProductImg = cursor.getBlob(cursor.getColumnIndex(R3cyDB.PRODUCT_THUMB));
//                @SuppressLint("Range") String ProductName = cursor.getString(cursor.getColumnIndex(R3cyDB.PRODUCT_NAME));
//
//                // Khởi tạo đối tượng với dữ liệu từ cơ sở dữ liệu
//                 order = new Order(orderId, OrderLineID, OrderLineProductID, OrderSalePrice, Quantity, OrderCustomerID, ProductPrice, TotalOrderValue, OrderStatus, TotalAmount, ProductImg, ProductName);

//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Xử lý ngoại lệ nếu có
//        } finally {
//            // Đóng Cursor
//            if (cursor != null) {
                cursor.close();
            }
            db.close();
//        }
        return orderId;

    }
//    @SuppressLint("Range")
//    public int getFirstOrderLineByID(int OrderLineId) {
//        SQLiteDatabase db = getReadableDatabase();
//        int minOrderLineId = -1;
//        Cursor cursor = null;
//
//            String query = "SELECT MIN(" + ORDER_LINE_ID + ") FROM " + TBl_ORDER_LINE + " WHERE " + ORDER_LINE_ORDER_ID + " = ?";
//            cursor = db.rawQuery(query, new String[]{String.valueOf(OrderLineId)});
//
//            if (cursor != null && cursor.moveToFirst()) {
//                minOrderLineId = cursor.getInt(0);
//                cursor.close();
//            }
//            db.close();
////
//
//        return minOrderLineId;
//    }

}
