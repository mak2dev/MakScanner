package fr.maoz.barscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;





public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "products_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(Product.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_NAME);

        onCreate(db);
    }

    public void onReset() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_NAME);


        onCreate(db);
    }

    public long insertProduct(String reference, String emplacement, int stock) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_REFERENCE, reference);
        values.put(Product.COLUMN_EMPLACEMENT, emplacement);
        values.put(Product.COLUMN_STOCK, stock);


        long id = db.insertWithOnConflict(Product.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update(Product.TABLE_NAME, values, Product.COLUMN_REFERENCE + " = ?",
                    new String[]{String.valueOf(reference)});
        }

        db.close();
        return id;
    }

    public Product getProduct(String reference) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Product.TABLE_NAME,
                new String[]{Product.COLUMN_REFERENCE, Product.COLUMN_EMPLACEMENT, Product.COLUMN_STOCK},
                Product.COLUMN_REFERENCE + "=?",
                new String[]{reference}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_REFERENCE)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_EMPLACEMENT)),
                cursor.getInt(cursor.getColumnIndex(Product.COLUMN_STOCK)));

        cursor.close();

        return product;
    }

    public Cursor raw() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Product.TABLE_NAME , new String[]{});

        return res;
    }
}
