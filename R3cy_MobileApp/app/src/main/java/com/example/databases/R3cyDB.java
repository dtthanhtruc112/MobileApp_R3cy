package com.example.databases;

import static android.os.Build.PRODUCT;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class R3cyDB extends SQLiteOpenHelper {
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
    public static final String VALID_DATE = "ValidDate";
    public static final String EXPIRE_DATE = "ExpireDate";
    public static final String MIN_ORDER_VALUE = "MinOrderValue";
    public static final String MAXIMUM_DISCOUNT = "MaximumDiscount";
    public static final String COUPON_VALUE = "CouponValue";
    public static final String MAXIMUM_USERS = "MaximumUsers";

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
        db.execSQL(CREATE_TBL_VOUCHERSHIP);
        db.execSQL(CREATE_TBL_WISHLIST);
        db.execSQL(CREATE_TBL_BLOG);
        db.execSQL(CREATE_TBL_CUSTOMPRODUCT);
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

        // Tạo lại bảng mới
        onCreate(db);
    }

    // Câu lệnh tạo bảng PRODUCT
    private static final String CREATE_TBL_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TBl_PRODUCT + "(" +
            PRODUCT_ID + " INTEGER NOT NULL UNIQUE," +
            PRODUCT_NAME + " TEXT NOT NULL," +
            CATEGORY + " TEXT NOT NULL," +
            PRODUCT_THUMB + " BLOB," +
            PRODUCT_PRICE + " REAL NOT NULL," +
            SALE_PRICE + " REAL NOT NULL," +
            PRODUCT_DESCRIPTION + " TEXT NOT NULL," +
            INVENTORY + " INTEGER DEFAULT 60," +
            PRODUCT_RATE + " REAL," +
            SOLD_QUANTITY + " INTEGER," +
            STATUS + " INTEGER DEFAULT 1," +
            PRODUCT_IMG1 + " BLOB DEFAULT NULL," +
            PRODUCT_IMG2 + " BLOB," +
            PRODUCT_IMG3 + " BLOB," +
            CREATED_DATE + " DATE DEFAULT ('2024-04-03')," +
            HOT + " INTEGER DEFAULT 0," +
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
            VALID_DATE + " DATE NOT NULL," +
            EXPIRE_DATE + " DATE NOT NULL," +
            MIN_ORDER_VALUE + " REAL NOT NULL," +
            MAXIMUM_DISCOUNT + " REAL NOT NULL," +
            COUPON_VALUE + " REAL NOT NULL," +
            MAXIMUM_USERS + " INTEGER DEFAULT 10" +
            ")";
    // Câu lệnh tạo bảng Customer
    private static final String CREATE_TBL_CUSTOMER = "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMER + "(" +
            CUSTOMER_ID + " INTEGER NOT NULL UNIQUE," +
            USERNAME + " TEXT," +
            FULLNAME + " TEXT," +
            GENDER + " INTEGER NOT NULL DEFAULT 1," +
            EMAIL + " TEXT NOT NULL," +
            PHONE + " TEXT," +
            PASSWORD + " TEXT NOT NULL," +
            MEMBERSHIP_SCORE + " INTEGER NOT NULL DEFAULT 0," +
            BIRTHDAY + " DATE," +
            CUSTOMER_THUMB + " BLOB," +
            CUSTOMER_TYPE + " TEXT," +
            "PRIMARY KEY (" + CUSTOMER_ID + " AUTOINCREMENT)" +
            ")";
    // Câu lệnh tạo bảng VoucherShip
    private static final String CREATE_TBL_VOUCHERSHIP = "CREATE TABLE IF NOT EXISTS " + TBl_VOUCHER_SHIP + "(" +
            VOUCHER_SHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VOUCHER_SHIP_CODE + " TEXT NOT NULL," +
            VOUCHER_SHIP_TITLE + " TEXT," +
            SCORE_MIN + " INTEGER NOT NULL," +
            VALID_DATE + " DATE," +
            EXPIRE_DATE + " DATE," +
            DISCOUNT_TYPE + " TEXT," +
            VOUCHER_SHIP_VALUE + " REAL," +
            MIN_ORDER_VALUE + " REAL," +
            MAXIMUM_DISCOUNT + " REAL," +
            MAXIMUM_USERS + " INTEGER DEFAULT 20" +
            ")";

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

//Kiểm tra bảng VoucherShip
public int numbOfRowsVoucher(){
    Cursor c = getData("SELECT * FROM " + TBl_VOUCHER_SHIP);
    int numberOfRows = c.getCount();
    c.close();
    return numberOfRows;
}

//    Thêm dữ liệu mẫu

// Dữ liệu mẫu Coupon
public void createSampleDataCoupon(){
    if (numbOfRowsCoupon() == 0){
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM10%', 'GIẢM 10% CHO THÀNH VIÊN MỚI, GIẢM TỐI ĐA 30K', 100, 'percent', 2024/04/15, 2024/05/20, 100000, 30000, 0.10, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM20%', 'GIẢM 20% CHÀO MỪNG THÁNG 4, GIẢM TỐI ĐA 60K', 1000, 'percent', 2024/04/15, 2024/05/20, 200000, 60000, 0.20, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM30%', 'GIẢM 30% TRI ÂN THÀNH VIÊN BẠC, GIẢM TỐI ĐA 90K', 5000, 'percent', 2024/04/15, 2024/05/20, 300000, 90000, 0.30, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM120K', 'GIẢM 120K CHÀO MỪNG THÁNG 5', 10000, 'value', 2024/04/15, 2024/05/20, 400000, 120000, 120000, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM150K', 'GIẢM 150K TRI ÂN THÀNH VIÊN KIM CƯƠNG', 20000, 'value', 2024/04/15, 2024/05/20, 500000, 150000, 150000, 10)");
    }
}

// Dữ liệu mẫu VoucherSHip
public void createSampleDataVoucher(){
    if (numbOfRowsVoucher() == 0){
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM20%', 'GIẢM 20% ĐƠN HÀNG TRÊN 100000', 100, 2024/04/15, 2024/05/20, 'percent', 0.20, 100000, 30000, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM40%', 'GIẢM 40% ĐƠN HÀNG TRÊN 200000', 1000, 2024/04/15, 2024/05/20, 'percent', 0.40, 200000, 30000, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM60%', 'GIẢM 60% ĐƠN HÀNG TRÊN 300000', 5000, 2024/04/15, 2024/05/20, 'percent', 0.6, 300000, 30000, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM40K', 'GIẢM 40K ĐƠN HÀNG TRÊN 400000 ', 10000, 2024/04/15, 2024/05/20, 'value', 400000, 400000, 40000, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'FREESHIP', 'MIỄN PHÍ VẬN CHUYỂN', 20000, 2024/04/15, 2024/05/20, 'value', 1, 500000, 60000, 30)");
    }
}

// Kiểm tra bảng Customer
public int numbOfRowsCustomer(){
        Cursor c = getData("SELECT * FROM " + TBL_CUSTOMER);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
}

public void createSampleDataCustomer(){
    if (numbOfRowsCustomer() == 0){
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'Lê Thị Tuyết Anh', 'anhltt', '0911235896','anhltt21411@gmail.com', 'anhltt21411@', 'Nữ', '01/02/2003', null, 'Thường', 500)");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'Đặng Thị Thanh Trúc', 'trucdtt', '0910587896','trucdtt21411@gmail.com', 'trucdtt21411@', 'Nữ', '01/10/2003', null, 'Đồng', 2000)");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'Đặng Lê Như Quỳnh', 'quynhdln', '0923535896','quynhdln21411@gmail.com', 'quynhdln21411@', 'Nữ', '15/02/2003', null, 'Bạc', 7000)");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'Hồ Lê Thanh Trúc', 'truchlt', '0971237410','truchlt21411@gmail.com', 'truchlt21411@', 'Nữ', '21/08/2003', null, 'Vàng', 15000 )");
        execSql("INSERT INTO " + TBL_CUSTOMER + " VALUES(null, 'Nguyễn Thảo Nguyên', 'nguyennt', '0956335872','nguyennt21411@gmail.com', 'nguyennt21411@', 'Nữ', '11/12/2003', null, 'Kim Cương', 22000 )");
    }
}


}
