package com.redshift.rs_vapourtraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "traindb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Product";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DESCRIPTION_COL = "description";
    private static final String IMG_URL_COL = "imageURL";
    private static final String PRICE_COL = "price";
    private static final String CREDITSWORTH_COL = "credits";

    // User
    private static final String TABLE_NAME2 = "Users";
    private static final String ID_COL2 = "id";
    private static final String NAME_COL2 = "name";
    private static final String PASSWORD_COL = "password";
    private static final String CREDITS_COL = "Credits";

    // Credit card and payment info
    private static final String TABLE_NAME3 = "cardINFO";
    private static final String ID_COL3 = "id";
    private static final String USERID_COL3 = "userid";
    private static final String CARDNUMMBER_COL= "cardNummber";
    private static final String CVV_COL = "cvv";
    private static final String EXPIRYDATE_COL = "expiryDate";
    private static final String NAMEONCARD_COL = "nameoncard";

    // Games owned
    private static final String TABLE_NAME4 = "gamePurchases";
    private static final String ID_COL4 = "purchaseid";
    private static final String USERID_COL4 = "userid";
    private static final String GAMEID_COL = "gameid";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + IMG_URL_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + CREDITSWORTH_COL + " INTEGER)";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_NAME2 + " ("
                + ID_COL2 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL2 + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + CREDITS_COL + " INTEGER)";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_NAME3 + " ("
                + ID_COL3 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERID_COL3 + " TEXT NOT NULL,"
                + CARDNUMMBER_COL + " TEXT,"
                + CVV_COL + " TEXT,"
                + EXPIRYDATE_COL + " TEXT,"
                + NAMEONCARD_COL + " TEXT)";
        db.execSQL(query3);

        String query4 = "CREATE TABLE " + TABLE_NAME4 + " ("
                + ID_COL4 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERID_COL4 + " INTEGER NOT NULL,"
                + GAMEID_COL + " INTEGER NOT NULL)";
        db.execSQL(query4);

        // Create credit card table & payment info
        // User -> user purchasing in txt file on phone.
        // Show if game is purchased
        // User fincial situation
    }


    public void addNewUser(String username, String password, int creds){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL2, username);
        values.put(PASSWORD_COL, password);
        values.put(CREDITS_COL, creds);
        db.insert(TABLE_NAME2, null, values);
        db.close();
    }

    public void addNewProduct (String prodName, String desc, String url, String price, int credits){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, prodName);
        values.put(DESCRIPTION_COL, desc);
        values.put(IMG_URL_COL, url);
        values.put(PRICE_COL, price);
        values.put(CREDITSWORTH_COL, credits);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addCreditCard (String userID, String cardNummber, String cvv, String expiryDate, String nameOnCard){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERID_COL3, userID);
        values.put(CARDNUMMBER_COL, cardNummber);
        values.put(CVV_COL, cvv);
        values.put(EXPIRYDATE_COL, expiryDate);
        values.put(NAMEONCARD_COL, nameOnCard);
        db.insert(TABLE_NAME3, null, values);
        db.close();
    }

    public String checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        //sql injectable
        Cursor cursorSQL= db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "+ NAME_COL2 +" = '" + username +"' AND " + PASSWORD_COL +" = '" + password + "'", null);
        if (cursorSQL.moveToFirst()) {
            String name = cursorSQL.getString(1);
            String pass = cursorSQL.getString(2);
            return name;
        }
        cursorSQL.close();
        return null;
    }

    public String checkUser(String userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        //sql injectable
        Cursor cursorSQL= db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "+ ID_COL2 +" = '" + userID + "'", null);
        if (cursorSQL.moveToFirst()) {
            String name = cursorSQL.getString(1);
            String pass = cursorSQL.getString(2);
            return name;
        }
        cursorSQL.close();
        return null;
    }

    public void buyGame (String userID,int gameID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERID_COL4, userID);
        values.put(GAMEID_COL, gameID);
        db.insert(TABLE_NAME4, null, values);
        db.close();
    }

    public ArrayList<ProductModal> getOwnedGames (String userID) {
        ArrayList<ProductModal> productModalArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //sql injectable
        Cursor cursorSQL= db.rawQuery("SELECT * FROM " + TABLE_NAME4 + " WHERE "+ USERID_COL4 + " = '" + userID +"'", null);
        if (cursorSQL.moveToFirst()) {
            do{
                productModalArrayList.add(getGame(cursorSQL.getInt(2)));
            } while(cursorSQL.moveToNext());
            cursorSQL.close();
            return productModalArrayList;
        }
        cursorSQL.close();
        return null;
    }

    public ProductModal getGame(int gameID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = '" + gameID + "'" , null);
        if (cursorProducts.moveToFirst()) {
            ProductModal game = new ProductModal(cursorProducts.getString(1),
                    cursorProducts.getString(3),
                    cursorProducts.getString(4),
                    cursorProducts.getString(2),
                    cursorProducts.getInt(0),
                    cursorProducts.getInt(5));
            return game;
        }
        return null;
    }

    public String getUserID(String username){
        String userID;
        SQLiteDatabase db = this.getReadableDatabase();
        //make more sql injectable
        Cursor cursorSQL= db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE "+ NAME_COL2 +" = '" + username +"'", null);
        if (cursorSQL.moveToFirst()) {
            userID = cursorSQL.getString(0);
            cursorSQL.close();
            return userID;
        }
        cursorSQL.close();
        return null;
    }

    public int getCreds(String userID){
        int credits = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorSQL= db.rawQuery("SELECT "+ CREDITS_COL +" FROM " + TABLE_NAME2 + " WHERE "+ ID_COL2 +" = '" + userID +"'", null);
        if (cursorSQL.moveToFirst()) {
            credits = cursorSQL.getInt(0);
            cursorSQL.close();
            return credits;
        }
        cursorSQL.close();
        return 0;
    }

    public void updateCredits(String userID, int credits){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursorSQL= db.rawQuery("UPDATE " + TABLE_NAME2 + " SET " + CREDITS_COL + " = '" + credits + "' WHERE " + ID_COL2 +" = '" + userID +"'",null);
        ContentValues values = new ContentValues();
        values.put(CREDITS_COL, credits);
        db.update(TABLE_NAME2, values, ID_COL2+"=?", new String[]{userID});
    }

    public ArrayList<ProductModal> readProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<ProductModal> productModalArrayList = new ArrayList<>();
        if (cursorProducts.moveToFirst()) {
            do {
                productModalArrayList.add(new ProductModal(cursorProducts.getString(1),
                        cursorProducts.getString(3),
                        cursorProducts.getString(4),
                        cursorProducts.getString(2),
                        cursorProducts.getInt(0),
                        cursorProducts.getInt(5)));
            } while (cursorProducts.moveToNext());
            cursorProducts.close();
            return productModalArrayList;
        }
        cursorProducts.close();
        return null;
    }

    public ProductModal getProduct(int productID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = '"+ productID + "'", null);
        ProductModal game;
        if (cursorProducts.moveToFirst()) {
            game = (new ProductModal(cursorProducts.getString(1),
                    cursorProducts.getString(3),
                    cursorProducts.getString(4),
                    cursorProducts.getString(2),
                    cursorProducts.getInt(0),
                    cursorProducts.getInt(5)));
            cursorProducts.close();
            return game;
        }
        cursorProducts.close();
        return null;
    }

    public ArrayList<CardModal>  getCard (String userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCards = db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE "+ USERID_COL3 +" = " + userID, null);
        ArrayList<CardModal> cardModalArrayList = new ArrayList<>();
        if (cursorCards.moveToFirst()) {
            do {
                cardModalArrayList.add(new CardModal(cursorCards.getInt(1),
                        cursorCards.getString(2),
                        cursorCards.getString(3),
                        cursorCards.getString(4),
                        cursorCards.getString(5)));
            } while (cursorCards.moveToNext());
            cursorCards.close();
            return cardModalArrayList;
        }
        cursorCards.close();
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }
}
