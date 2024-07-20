package com.example.orderfoodapp.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ProductHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product_manager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "product_manager";
    private static final String ID = "id";
    private static final String IMAGE = "imageProduct";
    private static final String PRODUCT = "product";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String SALE = "sale";
    private static final String TRENDING = "trending";
    private static final String INGREDIENT = "ingredient";
    private static final String CLASSIFY = "classify";

    public ProductHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + IMAGE + " TEXT,"
                + PRODUCT + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + PRICE + " INTEGER,"
                + SALE + " REAL,"
                + TRENDING + " INTEGER," // Use INTEGER for boolean representation (0 or 1)
                + INGREDIENT + " TEXT,"
                + CLASSIFY + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    @SuppressLint("RestrictedApi")
    public void initData() {
        SQLiteDatabase db = this.getWritableDatabase(); // Lấy database có thể ghi

        String sql = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" +
                IMAGE + ", " + PRODUCT + ", " + PRICE + ", " + SALE + ", " +
                TRENDING + ", " + INGREDIENT + ", " + CLASSIFY + ", " + DESCRIPTION + ") VALUES " +
                "('burgerbo', 'Burger bò', 75000, 0, 1, 'bo, banhmi, rau, sot', 'MonChinh', 'Burger thịt bò thơm ngon với lớp thịt bò mềm mại được nướng chín vàng, kèm với sốt mayonnaise tươi mát và rau xanh tươi ngon, đặc trưng của nhà hàng'), " +
                "('burgerga', 'Burger gà', 70000, 0.1, 0, 'ga, banhmi, rau, sot', 'MonChinh', 'Burger thịt gà tươi ngon với thịt gà dai và thơm ngon, phủ một lớp sốt cà chua và rau sống giòn ngon, tạo nên hương vị đậm đà'), " +
                "('burgerchay', 'Burger chay', 60000, 0.1, 1, 'rau, banhmi, sot', 'MonChinh', 'Burger chay thanh mát với lớp bánh mì giòn tan, phủ nhiều loại rau xanh tươi và sốt mayonnaise chay, là sự lựa chọn hoàn hảo cho những ai yêu thích ẩm thực tươi mới'), " +
                "('my', 'Mỳ Ý Spaghetti', 80000, 0.1, 1, 'my, thit bo, sot', 'MonChinh', 'Mỳ Ý sốt bò thơm ngon với sợi mỳ Ý dai và sốt thịt bò thấm đều, phủ một lớp phô mai tan chảy, mang đến trải nghiệm ẩm thực ngon miệng'), " +
                "('pizza', 'Pizza', 120000, 0.1, 0, 'bot mi, pho mai, sot ca chua, cac loai nhan', 'MonChinh', 'Pizza sốt cà chua thượng hạng với lớp vỏ bánh giòn rụm, phủ đầy phô mai và các loại nhân hảo hạng, là món không thể bỏ qua trong menu của quán'), " +
                "('tacos', 'Tacos', 70000, 0.1, 0, 'vo banh mi, thit bo, rau, sot', 'MonChinh', 'Tacos hương vị độc đáo với vỏ bánh mềm mại, nhân thịt bò thơm ngon và sốt salsa tươi, tạo nên một trải nghiệm ẩm thực đa dạng'), " +
                "('nachos', 'Nachos', 65000, 0.1, 0, 'banh nacho, pho mai, sot', 'MonChinh', 'Nachos thơm ngon với bánh nacho giòn rụm, phủ lớp phô mai béo ngậy và sốt thấm đều'), " +
                "('hotdog', 'Hot Dog', 50000, 0.1, 0, 'banh mi dai, xuc xich, sot', 'MonChinh', 'Hot Dog với xúc xích thơm ngon bên trong lớp bánh mì mềm mại, phủ lớp sốt mayonnaise và rau xanh giòn ngon'), " +
                "('garan', 'Gà rán', 75000, 0.1, 0, 'ga, bot mi, dau an, gia vi', 'MonChinh', 'Gà rán giòn ngon với lớp vỏ bọc bên ngoài giòn tan và thịt gà tươi sốt nướng đậm đà'), " +
                "('club_sandwich', 'Club Sandwich', 65000, 0.1, 1, 'banh mi, thit ga, bacon, rau, sot', 'MonChinh', 'Club Sandwich thượng hạng với lớp bánh mì mềm mại, thịt gà, thịt lợn muối, bơ, trứng và các loại rau sống tươi'), " +
                "('macandcheese', 'Mac and Cheese', 70000, 0.1, 0, 'nui, pho mai, sua, bot mi', 'MonChinh', 'Mac and Cheese thơm ngon với mỳ ý hấp và sốt phô mai béo ngậy'), " +
                "('lasagna', 'Lasagna', 95000, 0, 0, 'bot mi, thit bo, pho mai, sot ca chua', 'MonChinh', 'Lasagna thơm ngon với lớp bánh mì mềm mại, thịt bò thơm ngon và sốt cà chua đậm đà'), " +
                "('kem_vani', 'Kem vani', 25000, 0, 0, 'sua, duong, vanilla', 'TrangMieng', 'Kem vani thơm ngon với hương vị vani đậm đà, là sự lựa chọn hoàn hảo cho món tráng miệng mát lạnh'), " +
                "('kem_socola', 'Kem socola', 27000, 0.1, 1, 'sua, duong, socola', 'TrangMieng', 'Kem sô-cô-la ngọt ngào với hương vị sô-cô-la đậm đà, là sự lựa chọn lý tưởng để kết thúc bữa ăn'), " +
                "('kem_dau', 'Kem dâu', 26000, 0.1, 0, 'sua, duong, dau', 'TrangMieng', 'Kem dâu tươi ngon với hương vị dâu tươi và thơm ngon, là món tráng miệng hoàn hảo cho mọi dịp'), " +
                "('che_dau_xanh', 'Chè đậu xanh', 20000, 0.1, 0, 'dau xanh, duong, nuoc cot dua', 'TrangMieng', 'Chè đậu xanh ngon mát với hương vị đậu xanh thơm ngon và dịu nhẹ, là món tráng miệng phổ biến'), " +
                "('che_thap_cam', 'Chè thập cẩm', 22000, 0.1, 0, 'dau, tra, sua', 'TrangMieng', 'Chè thập cẩm phong phú với nhiều loại nguyên liệu như đậu, trà và sữa, mang đến hương vị đa dạng và thơm ngon'), " +
                "('che_dau_do', 'Chè đậu đỏ', 20000, 0.1, 0, 'dau do, duong, nuoc cot dua', 'TrangMieng', 'Chè đậu đỏ thơm ngon với hương vị đậu đỏ và dịu nhẹ của nước cốt dừa'), " +
                "('banh_flan', 'Bánh flan', 30000, 0, 0, 'trung, sua, duong, caramel', 'TrangMieng', 'Bánh flan mềm mịn và thơm ngon với lớp caramel ngọt ngào, là món tráng miệng không thể thiếu'), " +
                "('banh_pudding', 'Bánh pudding', 32000, 0.1, 0, 'sua, duong, gelatin', 'TrangMieng', 'Bánh pudding ngon mềm mịn với hương vị sữa và gelatin, là sự lựa chọn hoàn hảo cho bữa tiệc tráng miệng'), " +
                "('trai_cay_tuoi', 'Trái cây tươi', 20000, 0.1, 1, 'trai cay', 'TrangMieng', 'Trái cây tươi tươi ngon với nhiều loại trái cây tươi mát, là món tráng miệng lành mạnh và bổ dưỡng'), " +
                "('trai_cay_dam', 'Trái cây dầm', 22000, 0.1, 1, 'trai cay, sua dac', 'TrangMieng', 'Trái cây dầm mát lạnh với hương vị trái cây đa dạng và sữa đặc, là lựa chọn lý tưởng cho món tráng miệng nhẹ nhàng và ngon miệng'), " +
                "('americano', 'Americano', 39000, 0, 0, 'coffee, đường, nước', 'MonUong', 'Cà phê Americano thơm ngon với hương vị đậm đà và nồng nàn, là món đồ uống không thể thiếu cho những người yêu thích cà phê'), " +
                "('cappuchino', 'Cappuchino', 49000, 0.1, 0, 'coffee, đường, nước', 'MonUong', 'Cappuchino thơm ngon với hương vị cà phê đậm đà và lớp sữa béo ngậy, là sự lựa chọn hoàn hảo cho ngày mới'), " +
                "('matchalattenong', 'Matcha Latte Nóng', 60000, 0.1, 1, 'coffee, đường, nước, matcha', 'MonUong', 'Matcha Latte Nóng thơm ngon với hương vị matcha đậm đà và lớp sữa béo ngậy, là món đồ uống hoàn hảo để bạn thư giãn'), " +
                "('socolanong', 'Socola nóng', 60000, 0.1, 0, 'socola, đường, nước', 'MonUong', 'Sô-cô-la nóng thơm ngon với hương vị sô-cô-la đậm đà và nồng nàn, là món đồ uống ấm áp cho mọi thời điểm trong ngày'), " +
                "('lattenong', 'Latte Nóng', 60000, 0.1, 1, 'coffee, đường, nước', 'MonUong', 'Latte Nóng thơm ngon với hương vị cà phê đậm đà và lớp sữa béo ngậy, là sự lựa chọn hoàn hảo cho ngày nắng'), " +
                "('caphemocha', 'Cà phê Mocha', 59000, 0.1, 0, 'coffee,mocha , đường, nước', 'MonUong', 'Cà phê Mocha thơm ngon với hương vị cà phê và sô-cô-la đậm đà, là món đồ uống lý tưởng cho những người yêu thích sự kết hợp giữa cà phê và sô-cô-la'), " +
                "('cafedennong', 'Cafe đen nóng', 35000, 0.1, 0, 'coffee, đường, nước', 'MonUong', 'Cafe đen nóng thơm ngon với hương vị cà phê đậm đà và nồng nàn, là món đồ uống giúp bạn tỉnh táo vào mỗi buổi sáng'), " +
                "('cafenaunong', 'Cafe nâu nóng', 60000, 0, 0, 'coffee, đường, nước', 'MonUong', 'Cafe nâu nóng thơm ngon với hương vị cà phê đậm đà và nồng nàn, là món đồ uống lý tưởng cho những ngày lạnh giá'), " +
                "('caphesuada', 'Cà phê sữa đá', 35000, 0.1, 0, 'coffee,sữa , đường, nước', 'MonUong', 'Cà phê sữa đá thơm ngon với hương vị cà phê đậm đà và lớp sữa béo ngậy, là món đồ uống giải nhiệt tuyệt vời vào mùa hè'), " +
                "('capheamericanoda', 'Cà phê Americano đá', 45000, 0.1, 0, 'coffee, đường, nước', 'MonUong', 'Cà phê Americano đá thơm ngon với hương vị cà phê đậm đà và mát lạnh của đá, là sự lựa chọn tuyệt vời cho ngày nắng'), " +
                "('caphelatteda', 'Cà phê Latte đá', 55000, 0.1, 0, 'coffee, đường, nước', 'MonUong', 'Cà phê Latte đá thơm ngon với hương vị cà phê đậm đà và lớp sữa béo ngậy, là món đồ uống giải nhiệt tuyệt vời vào mùa hè'), " +
                "('caphedenda', 'Cà phê đen đá', 35000, 0.1, 0, 'coffee, đường, nước', 'MonUong', 'Cà phê đen đá thơm ngon với hương vị cà phê đậm đà và mát lạnh của đá, là sự lựa chọn giải nhiệt lý tưởng cho mọi thời điểm'), " +
                "('trakemsua', 'Trà kem sữa', 49000, 0, 1, 'kem ,sữa , đường, nước', 'MonUong', 'Trà kem sữa thơm ngon với hương vị trà và lớp kem béo ngậy, là sự lựa chọn lý tưởng cho những ngày nắng nóng'), " +
                "('tradao', 'Trà đào', 49000, 0.1, 1, 'trà ,đào , đường, nước', 'MonUong', 'Trà đào thơm ngon với hương vị trà và trái đào tươi mát, là món đồ uống giải nhiệt lý tưởng'), " +
                "('travai', 'Trà vải', 60000, 0.1, 1, 'trà ,vải , đường, nước', 'MonUong', 'Trà vải thơm ngon với hương vị trà và trái vải tươi mát, là sự lựa chọn hoàn hảo cho một ngày nắng'), " +
                "('tacmuoimo', 'Tắc muối mơ ngâm hạt chia', 39000, 0.1, 0, 'tắc,mơ , đường, nước', 'MonUong', 'Tắc muối mơ ngâm hạt chia thơm ngon với hương vị tắc và mơ đậm đà, là sự lựa chọn lý tưởng cho một ngày nắng'), " +
                "('socolamoca', 'Sô-cô-la moca', 60000, 0.1, 0, 'socola, đường, nước', 'MonUong', 'Sô-cô-la moca thơm ngon với hương vị sô-cô-la đậm đà và nồng nàn, là món đồ uống hoàn hảo vào mọi thời điểm trong ngày'), " +
                "('socolanong', 'Sô-cô-la nóng', 60000, 0.1, 0, 'socola, đường, nước', 'MonUong', 'Sô-cô-la nóng thơm ngon với hương vị sô-cô-la đậm đà và nồng nàn, là món đồ uống ấm áp cho mọi thời điểm trong ngày'), " +
                "('sinhtochanhday', 'Sinh tố Chanh dây', 35000, 0.1, 0, 'sinh to, chanh day, sua', 'MonUong', 'Sinh tố Chanh dây thơm ngon với hương vị chanh dây tươi mát và sữa béo ngậy, là món đồ uống giải nhiệt tuyệt vời vào mùa hè'), " +
                "('sinhtoxoai', 'Sinh tố Xoài', 35000, 0.1, 0, 'sinh to, xoai, sua', 'MonUong', 'Sinh tố Xoài thơm ngon với hương vị xoài tươi mát và sữa béo ngậy, là món đồ uống giải nhiệt tuyệt vời vào mùa hè'), " +
                "('sinhtodau', 'Sinh tố Dâu', 35000, 0.1, 0, 'sinh to, dau, sua', 'MonUong', 'Sinh tố Dâu thơm ngon với hương vị dâu tươi mát và sữa béo ngậy, là món đồ uống giải nhiệt tuyệt vời vào mùa hè'), " +
                "('sinhtobo', 'Sinh tố Bơ', 35000, 0.1, 0, 'sinh to, bo, sua', 'MonUong', 'Sinh tố Bơ thơm ngon với hương vị bơ tươi mát và sữa béo ngậy, là món đồ uống giải nhiệt tuyệt vời vào mùa hè')," +
                "('frenchfries', 'Khoai tây chiên', 35000, 0.1, 0, 'khoai tây, dầu ăn, muối', 'MonPhu', 'Crispy French fries'), " +
                "('springrolls', 'Chả giò', 40000, 0.1, 0, 'thịt heo, bánh tráng, rau, miến', 'MonPhu', 'Vietnamese spring rolls'), " +
                "('salad', 'Salad rau', 45000, 0.1, 0, 'rau sống, dầu dấm, cà chua, dưa chuột', 'MonPhu', 'Fresh vegetable salad'), " +
                "('onionrings', 'Hành tây chiên giòn', 38000, 0.1, 0, 'hành tây, bột chiên giòn, dầu ăn', 'MonPhu', 'Crispy onion rings'), " +
                "('garlicbread', 'Bánh mì bơ tỏi', 32000, 0.1, 0, 'bánh mì, bơ, tỏi', 'MonPhu', 'Garlic butter bread'), " +
                "('mozarellasticks', 'Mozzarella Sticks', 50000, 0.1, 0, 'phô mai mozzarella, bột chiên xù, dầu ăn', 'MonPhu', 'Crispy mozzarella sticks'), " +
                "('nachos', 'Nachos', 45000, 0.1, 0, 'bánh nacho, pho mai, sot', 'MonPhu', 'Crispy nachos with cheese and sauce'), " +
                "('popcornchicken', 'Gà popcorn', 52000, 0.1, 0, 'thịt gà, bột chiên xù, dầu ăn', 'MonPhu', 'Crispy popcorn chicken'), " +
                "('samosa', 'Samosa', 38000, 0.1, 0, 'bột mì, khoai tây, đậu Hà Lan, gia vị', 'MonPhu', 'Indian samosa with potato and peas')";

        db.execSQL(sql);
        db.close();
    }

    @SuppressLint("RestrictedApi")
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                product.setImageProduct(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                product.setProduct(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                product.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(PRICE)));
                product.setSale(cursor.getFloat(cursor.getColumnIndexOrThrow(SALE)));
                product.setTrending(cursor.getInt(cursor.getColumnIndexOrThrow(TRENDING)));
                product.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow(INGREDIENT)));
                product.setClassify(cursor.getString(cursor.getColumnIndexOrThrow(CLASSIFY)));

                productList.add(product); // Thêm sản phẩm vào danh sách kết quả
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
