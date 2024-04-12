package com.example.databases;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

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

        import com.example.r3cy_mobileapp.R;

import java.io.ByteArrayOutputStream;

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
            PRODUCT_IMG1 + " BLOB DEFAULT NULL," +
            PRODUCT_IMG2 + " BLOB," +
            PRODUCT_IMG3 + " BLOB," +
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

// Dữ liệu mẫu Coupon
public void createSampleDataCoupon(){
    if (numbOfRowsCoupon() == 0){
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM10%', 'GIẢM 10% ĐƠN HÀNG CHO THÀNH VIÊN MỚI, GIẢM TỐI ĐA 30K', 100, 'percent', 'order', 2024/04/15, 2024/05/20, 100000, 30000, 0.10, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM20%', 'GIẢM 20% ĐƠN HÀNG CHÀO MỪNG THÁNG 4, GIẢM TỐI ĐA 60K', 1000, 'percent', 'order', 2024/04/15, 2024/05/20, 200000, 60000, 0.20, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM30%', 'GIẢM 30% ĐƠN HÀNG TRI ÂN THÀNH VIÊN BẠC, GIẢM TỐI ĐA 90K', 5000, 'percent', 'order', 2024/04/15, 2024/05/20, 300000, 90000, 0.30, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM120K', 'GIẢM 120K ĐƠN HÀNG CHÀO MỪNG THÁNG 5', 10000, 'value', 'order', 2024/04/15, 2024/05/20, 400000, 120000, 120000, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM150K', 'GIẢM 150K ĐƠN HÀNG TRI ÂN THÀNH VIÊN KIM CƯƠNG', 20000, 'value', 'order', 2024/04/15, 2024/05/20, 500000, 150000, 150000, 10)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM20%', 'GIẢM 20% PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 100000', 100, 'percent', 'ship', 2024/04/15, 2024/05/20, 100000, 30000, 0.20, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM40%', 'GIẢM 40% PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 200000', 1000, 'percent', 'ship', 2024/04/15, 2024/05/20, 200000, 30000, 0.40, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM60%', 'GIẢM 60% PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 300000', 5000, 'percent', 'ship', 2024/04/15, 2024/05/20, 300000, 30000, 0.6, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'GIAM40K', 'GIẢM 40K PHÍ VẬN CHUYỂN CHO ĐƠN HÀNG TRÊN 400000 ', 10000, 'value', 'ship', 2024/04/15, 2024/05/20, 400000, 40000, 400000, 30)");
        execSql("INSERT INTO " + TBl_COUPON + " VALUES(null, 'FREESHIP', 'MIỄN PHÍ VẬN CHUYỂN', 20000, 'value', 'ship', 2024/04/15, 2024/05/20, 500000, 60000, 1, 30)");
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

    //    Kiểm tra bảng Product
    public int numbOfRowsProduct(){
        Cursor c = getData("SELECT * FROM " + TBl_PRODUCT);
        int numberOfRows = c.getCount();
        c.close();
        return numberOfRows;
    }

    public boolean insertData(String ProductName, double ProductPrice, String ProductDescription, byte[] ProductThumb, int Hot, String Category, int Inventory, double ProductRate, double SalePrice, int SoldQuantiy, String CreatedDate, int Status, byte[] Img1, byte[] Img2, byte[] Img3) {
        SQLiteDatabase database = getWritableDatabase();
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
                STATUS + ", " +
                PRODUCT_IMG1 + ", " +
                PRODUCT_IMG2 + ", " +
                PRODUCT_IMG3 +
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
        statement.bindBlob(13, Img1);
        statement.bindBlob(14, Img2);
        statement.bindBlob(15, Img3);

        long result = statement.executeInsert();
        boolean  success = result != -1; // Trả về true nếu chèn thành công, false nếu không
        Log.d("DatabaseHelper", "Insert data result: " + success);
        return success;
    }

    public void createSampleProduct() {
        insertData("Đĩa nhỏ", 120000, "Đĩa nhỏ từ nhựa tái chế là một sự sáng tạo độc đáo, kết hợp giữa tính tiện ích và lòng yêu thương đối với môi trường. Với nguyên liệu chủ đạo là nhựa tái chế, sản phẩm không chỉ làm giảm lượng chất thải nhựa mà còn thể hiện cam kết đối với bảo vệ môi trường. Sự linh hoạt trong việc sử dụng đồ lưu trữ nhỏ gọn này không chỉ giúp tối ưu hóa không gian lưu trữ mà còn tạo điểm nhấn cho việc tái chế nguyên liệu. Thiết kế nhỏ gọn và tiện lợi làm cho sản phẩm trở thành người bạn đồng hành lý tưởng, không chỉ phục vụ nhu cầu hàng ngày mà còn thúc đẩy ý thức về một lối sống bền vững. Đĩa nhỏ từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian sống mà còn là một biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc  giữ gìn cho hành tinh xanh của chúng ta. Hãy chọn lựa thông minh và hòa mình vào những giải pháp bảo vệ môi trường với sản phẩm độc đáo này.", convertPhoto(context, R.drawable.dgd_dia1), 1, "Đồ gia dụng", 60, 4.5, 100000, 87, "2024/04/10", 1, convertPhoto(context, R.drawable.dgd_dia1), convertPhoto(context, R.drawable.dgd_dia2), convertPhoto(context, R.drawable.dgd_dia3));
        insertData("Khay đựng xà phòng", 170000, "Khay đựng bánh xà phòng từ nhựa tái chế là sự kết hợp hoàn hảo giữa tính thực tế và cam kết với môi trường. Với nguyên liệu là nhựa tái chế, sản phẩm này không chỉ giúp giảm lượng chất thải nhựa mà còn thể hiện tinh thần chăm sóc đối với hành tinh xanh của chúng ta. Thiết kế của khay đựng bánh xà phòng không chỉ đơn giản mà còn linh hoạt, phù hợp với mọi không gian nhà tắm. Sự sáng tạo trong cách sử dụng nguyên liệu tái chế không chỉ làm cho sản phẩm trở nên độc đáo mà còn đặt ra một tiêu chí mới cho việc chọn lựa sản phẩm gia dụng có trách nhiệm với môi trường. Khay đựng bánh xà phòng từ nhựa tái chế không chỉ là một phụ kiện hữu ích trong việc tổ chức không gian nhà tắm mà còn là một bước nhỏ nhưng ý nghĩa trong hành trình chung của chúng ta để bảo vệ và giữ gìn cho hành tinh xanh, trong từng lựa chọn hàng ngày của chúng ta.", convertPhoto(context, R.drawable.dgd_xaphong1), 0, "Đồ gia dụng", 60, 4.7, 150000, 45, "2024/04/10", 1, convertPhoto(context, R.drawable.dgd_xaphong1), convertPhoto(context, R.drawable.dgd_xaphong2), convertPhoto(context, R.drawable.dgd_xaphong3));
        insertData("Lót ly", 110000, "Đồ lót ly từ nhựa tái chế là sự kết hợp độc đáo giữa sự thoải mái và cam kết với môi trường. Với việc sử dụng nguyên liệu là nhựa tái chế, sản phẩm không chỉ mang lại cảm giác mềm mại và thoải mái cho người sử dụng mà còn đóng góp vào việc giảm lượng chất thải nhựa. Thiết kế của đồ lót ly không chỉ chú trọng đến sự thoải mái và tôn lên vẻ đẹp tự nhiên, mà còn thể hiện sự chấp nhận trách nhiệm với môi trường. Việc sử dụng nhựa tái chế không chỉ là một xu hướng tiêu dùng thông minh mà còn là sự đóng góp tích cực vào việc bảo vệ nguồn tài nguyên tự nhiên và giảm thiểu ảnh hưởng tiêu cực đối với hệ sinh thái. Đồ lót ly từ nhựa tái chế không chỉ là lựa chọn thông minh cho sự thoải mái hàng ngày mà còn là một cách để chúng ta cùng nhau xây dựng một lối sống thân thiện với môi trường, đồng thời thể hiện sự quan tâm đến sức khỏe và hành tinh xanh của chúng ta.", convertPhoto(context, R.drawable.dgd_lotly1), 1, "Đồ gia dụng", 60, 4.0, 100000, 100, "2024/04/10", 1, convertPhoto(context, R.drawable.dgd_lotly1), convertPhoto(context, R.drawable.dgd_lotly2), convertPhoto(context, R.drawable.dgd_lotly3));
        insertData("Giá đỡ laptop", 220000, "Giá đỡ laptop từ nhựa tái chế là một phụ kiện không thể thiếu cho những người sử dụng máy tính xách tay, kết hợp giữa tính thực tế và tầm nhìn bền vững. Với sự sáng tạo trong việc sử dụng nhựa tái chế, sản phẩm không chỉ tạo ra một nơi thoải mái để đặt laptop mà còn là cách nhỏ nhưng tích cực để giảm lượng chất thải nhựa. Thiết kế nhẹ nhàng và linh hoạt của giá đỡ không chỉ giúp người dùng duy trì tư duy làm việc hiệu quả mà còn hỗ trợ vào nỗ lực chung của cộng đồng trong việc giữ gìn môi trường. Sự cam kết đối với nhựa tái chế không chỉ là một xu hướng tiêu dùng mà còn là một lối sống, và sản phẩm giá đỡ laptop này là minh chứng rõ ràng cho sự hài hòa giữa tiện ích và sự chấp nhận trách nhiệm với môi trường. Hãy lựa chọn giá đỡ laptop từ nhựa tái chế để không chỉ tận hưởng sự thuận tiện mà còn tham gia vào cuộc hành trình bảo vệ hành tinh của chúng ta.", convertPhoto(context, R.drawable.dgd_laptop1), 0, "Đồ gia dụng", 60, 5, 200000, 30, "2024/04/10", 1, convertPhoto(context, R.drawable.dgd_laptop1), convertPhoto(context, R.drawable.dgd_laptop2), convertPhoto(context, R.drawable.dgd_latop3));
        insertData("Dây cờ trang trí tiệc", 120000, "Dây cờ là một sản phẩm trang trí không thể thiếu cho bất kỳ buổi tiệc nào, và đặc biệt, chúng tôi tự hào giới thiệu dòng sản phẩm dây cờ được làm từ nhựa tái chế. Sự sáng tạo trong thiết kế không chỉ tạo ra không khí vui tươi và phấn khích cho bất kỳ dịp lễ nào mà còn góp phần tích cực vào nỗ lực bảo vệ môi trường. Với việc sử dụng nhựa tái chế, chúng tôi cam kết giảm lượng chất thải nhựa và tái sử dụng nguyên liệu, giữ cho không gian tiệc tùng trở nên thú vị hơn mà không ảnh hưởng đến môi trường. Dây cờ từ nhựa tái chế không chỉ đẹp mắt mà còn là một cách thúc đẩy ý thức về trách nhiệm xã hội và bảo vệ hành tinh xanh chúng ta. Hãy tận hưởng những khoảnh khắc vui vẻ và đồng thời hỗ trợ vào việc giữ cho hành tinh của chúng ta trở nên bền vững hơn.", convertPhoto(context, R.drawable.dtt_dayco1), 1, "Đồ trang trí", 60, 4.5, 100000, 25, "2024/04/03", 1, convertPhoto(context, R.drawable.dtt_dayco1), convertPhoto(context, R.drawable.dtt_dayco2), convertPhoto(context, R.drawable.dtt_dayco3));
        insertData("Đồ trang trí giáng sinh", 90000, "Đồ trang trí Giáng Sinh 3D không chỉ làm mới không khí của mùa lễ hội mà còn là biểu tượng của sự sang trọng và ý thức về môi trường. Với việc sử dụng nhựa tái chế, sản phẩm này không chỉ tạo ra một không gian lễ hội ấm cúng mà còn đóng góp tích cực vào việc giảm lượng chất thải nhựa. Mỗi chiếc đồ trang trí được chế tạo với kỹ thuật 3D độc đáo, tạo nên hiệu ứng thị giác đặc sắc và sống động, làm tôn lên vẻ đẹp của mùa Giáng Sinh. Việc tái chế nhựa không chỉ giúp giảm tác động tiêu cực đối với môi trường mà còn thúc đẩy ý thức về việc sử dụng tài nguyên tái chế trong sản xuất. Đồ trang trí Giáng Sinh 3D là sự kết hợp hoàn hảo giữa sự sang trọng, sáng tạo và ý thức môi trường. Bằng cách chọn lựa sản phẩm này, chúng ta không chỉ tận hưởng không khí lễ hội phấn khích mà còn thể hiện sự quan tâm đến bảo vệ môi trường và chọn lựa bền vững trong mọi hoạt động.", convertPhoto(context, R.drawable.dtt_giangsinh1), 0, "Đồ trang trí", 60, 4.6, 70000, 80, "2024/04/10", 1, convertPhoto(context, R.drawable.dtt_giangsinh1), convertPhoto(context, R.drawable.dtt_giangsinh2), convertPhoto(context, R.drawable.dtt_giangsinh3));
        insertData("Đồng hồ treo tường", 370000, "Đồng hồ treo tường từ nhựa tái chế không chỉ là một sản phẩm thời gian mà còn là biểu tượng của sự sáng tạo và tôn trọng đối với môi trường. Với thiết kế độc đáo, sản phẩm này là sự kết hợp hoàn hảo giữa vẻ ngoại hình tinh tế và cam kết với lối sống bền vững. Bằng cách sử dụng nhựa tái chế, đồng hồ treo tường không chỉ giảm lượng rác thải nhựa mà còn giúp tái chế nguyên liệu, đóng góp vào việc bảo vệ môi trường. Sự linh hoạt trong việc tạo hình và màu sắc của sản phẩm này không chỉ làm mới không gian sống mà còn thể hiện tầm quan trọng của việc chọn lựa sản phẩm có trách nhiệm với môi trường. Đồng hồ treo tường từ nhựa tái chế không chỉ đơn thuần là một phụ kiện trang trí, mà còn là biểu tượng của lối sống ý thức về môi trường. Việc đặt mình vào ngôi nhà của bạn không chỉ làm tăng thêm vẻ đẹp mà còn là bước nhỏ nhưng ý nghĩa để góp phần vào việc bảo vệ hành tinh của chúng ta.", convertPhoto(context, R.drawable.dtt_dongho1), 1, "Đồ trang trí", 60, 4.2, 350000, 100, "2024/04/03", 1, convertPhoto(context, R.drawable.dtt_dongho1), convertPhoto(context, R.drawable.dtt_dongho2), convertPhoto(context, R.drawable.dtt_dongho3));
        insertData("Móc khóa hình đảo Kos", 110000, "Móc khóa hình đảo Kos là một tác phẩm sáng tạo không chỉ đẹp mắt mà còn mang đến sự ý thức về môi trường. Được tạo ra từ nhựa tái chế, sản phẩm này là biểu tượng của sự kết hợp giữa nghệ thuật và bảo vệ môi trường. Hình ảnh đảo Kos được minh họa trên móc khóa không chỉ đẹp mắt mà còn là cách tuyệt vời để kỷ niệm và tôn vinh vẻ đẹp của đảo nổi tiếng này. Với việc sử dụng nguyên liệu tái chế, chúng ta không chỉ giảm lượng chất thải nhựa mà còn thúc đẩy tư duy bền vững trong sản xuất. Mỗi chiếc móc khóa không chỉ là một sản phẩm thực tế, mà còn là một cách để chia sẻ thông điệp về ý thức môi trường và sự cần thiết của việc bảo vệ những địa điểm đẹp tự nhiên như đảo Kos. Đặt mình vào túi của bạn, sản phẩm này không chỉ là một chiếc móc khóa mà còn là một tuyên ngôn về sự đồng lòng trong việc bảo vệ hành tinh của chúng ta.", convertPhoto(context, R.drawable.pk_mockhoakos1), 0, "Đồ trang trí", 60, 5.0, 90000, 67, "2024/04/03", 1, convertPhoto(context, R.drawable.pk_mockhoakos1), convertPhoto(context, R.drawable.pk_mockhoakos2), convertPhoto(context, R.drawable.pk_mockhoakos1));
        insertData("Bông tai hình bông hoa", 160000, "Bông tai hình bông hoa là một biểu tượng của sự thanh lịch và sáng tạo, mang đến cho người đeo không chỉ vẻ đẹp tinh tế mà còn là niềm tự hào về việc chọn lựa có trách nhiệm với môi trường. Sản phẩm này được chế tạo từ nhựa tái chế, đó là một bước tiến quan trọng trong việc giảm lượng chất thải nhựa và giữ cho tài nguyên tự nhiên được bảo vệ. Bông hoa tinh tế được tái tạo từ nhựa tái chế không chỉ là một tuyên ngôn về sự đẹp đẽ mà còn là một cam kết vững chắc đối với bảo vệ môi trường. Việc sử dụng nguyên liệu tái chế giúp giảm áp lực đặt ra cho hệ sinh thái và hỗ trợ trong việc xây dựng một tương lai bền vững. Bông tai hình bông hoa không chỉ là một phụ kiện thời trang, mà còn là biểu tượng của sự chấp nhận trách nhiệm cá nhân trong việc duy trì sự cân bằng giữa thời trang và bảo vệ môi trường.", convertPhoto(context, R.drawable.pk_bongtai_hoa1), 1, "Phụ kiện", 60, 4.5, 140000, 56, "2024/04/10", 1, convertPhoto(context, R.drawable.pk_bongtai_hoa1), convertPhoto(context, R.drawable.pk_bongtai_hoa2), convertPhoto(context, R.drawable.pk_bongtai_hoa3));
        insertData("Bông tai hình chữ nhật", 120000, "Bông tai hình chữ nhật không chỉ là một biểu tượng của sự đơn giản và hiện đại trong thế giới thời trang mà còn là một minh chứng cho sự sáng tạo và chăm sóc đối với môi trường. Sản phẩm này được tạo ra từ nhựa tái chế, một lựa chọn thông minh và đảm bảo, giúp giảm lượng chất thải nhựa và bảo vệ nguồn tài nguyên tự nhiên. Thiết kế hình chữ nhật đơn giản nhưng tinh tế của bông tai mang lại sự linh hoạt trong việc kết hợp với nhiều phong cách thời trang khác nhau. Đồng thời, việc sử dụng nhựa tái chế không chỉ giúp giảm áp lực đặt ra cho môi trường mà còn thể hiện cam kết đối với phong cách sống bền vững và tiêu thụ có trách nhiệm. Bông tai hình chữ nhật không chỉ là một chiếc phụ kiện thời trang độc đáo mà còn là biểu tượng của ý thức môi trường. Sử dụng sản phẩm này không chỉ là một cách để thể hiện cái tôi cá nhân mà còn là bước nhỏ nhưng ý nghĩa trong việc góp phần vào việc giữ cho hành tinh của chúng ta trở nên bền vững hơn", convertPhoto(context, R.drawable.pk_bongtai_hcn1), 0, "Phụ kiện", 60, 4.7, 100000, 48, "2024/04/03", 1, convertPhoto(context, R.drawable.pk_bongtai_hcn1), convertPhoto(context, R.drawable.pk_bongtai_hcn2), convertPhoto(context, R.drawable.pk_bongtai_hcn3));
        insertData("Móc khóa hình mặt cười", 110000, "Móc khóa hình mặt cười là một phụ kiện vui nhộn và thân thiện với môi trường, được sáng tạo từ nhựa tái chế. Thiết kế này không chỉ mang lại sự hứng khởi với hình ảnh mặt cười thân quen mà còn góp phần giảm lượng chất thải nhựa đối với môi trường. Sử dụng nhựa tái chế là một cam kết đối với bảo vệ hành tinh của chúng ta, tạo ra một sản phẩm không chỉ phản ánh tính cách lạc quan mà còn thể hiện tinh thần chăm sóc môi trường. Mỗi chiếc móc khóa không chỉ là một biểu tượng vui nhộn mà còn là một bước tiến tích cực trong việc hướng tới một lối sống bền vững và ý thức môi trường. Sở hữu một chiếc móc khóa hình mặt cười không chỉ là thêm vào bộ sưu tập phụ kiện cá nhân của bạn mà còn là cách để bạn thể hiện sự quan tâm đến môi trường.", convertPhoto(context, R.drawable.pk_mockhoacuoi), 1, "Phụ kiện", 60, 4.0, 90000, 89, "2024/04/03", 1, convertPhoto(context, R.drawable.pk_mockhoacuoi), convertPhoto(context, R.drawable.pk_mockhoacuoi), convertPhoto(context, R.drawable.pk_mockhoacuoi));
        insertData("Móc khóa rùa biển", 110000, "Móc khóa hình rùa biển là một sáng tạo độc đáo kết hợp giữa thiết kế đáng yêu và tôn trọng môi trường. Sản phẩm này làm từ nhựa tái chế, chú trọng đến việc giảm lượng chất thải nhựa và ảnh hưởng tích cực đến bảo vệ hệ sinh thái biển cả. Hình rùa biển được chọn làm điểm nhấn cho móc khóa không chỉ vì sự đáng yêu mà còn vì ý nghĩa mà chúng mang lại trong việc góp phần bảo vệ động vật biển. Sự kết hợp giữa ý thức môi trường và thiết kế sáng tạo khiến cho sản phẩm này trở thành một cách tuyệt vời để thể hiện phong cách cá nhân của bạn trong khi đồng thời chung tay bảo vệ môi trường xanh - nơi rùa biển và nhiều loài động vật khác gọi là nhà.", convertPhoto(context, R.drawable.pk_mockhoarua1), 0, "Phụ kiện", 60, 4.5, 90000, 50, "2024/04/10", 1, convertPhoto(context, R.drawable.pk_mockhoarua1), convertPhoto(context, R.drawable.pk_mockhoarua2), convertPhoto(context, R.drawable.pk_mockhoarua3));

    }
    private byte[] convertPhoto(Context context, int resourceId) {
        BitmapDrawable drawable = (BitmapDrawable) context.getDrawable(resourceId);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }


    }





