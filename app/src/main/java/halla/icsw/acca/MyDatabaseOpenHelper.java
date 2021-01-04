package halla.icsw.acca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String tableName = "Drive";
    public static final String tableName2 = "oil";

    public MyDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(Date TEXT, Distance REAL)";
        String sql2 = "CREATE TABLE IF NOT EXISTS " + tableName2 + "(OilStation TEXT, Price TEXT)";
        try {
            db.execSQL(sql);
            db.execSQL(sql2);
        } catch (Exception e) {
        }
    }

    public void insertOil(SQLiteDatabase db, String oilStaion, String price){
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tableName2 + "(OilStation,Price)" + " values('" + oilStaion + "','" + price + "')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("error!!", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void insertDrive(SQLiteDatabase db, String date, double distance) {
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tableName + "(Date,Distance)" + " values('" + date + "'," + distance + ")";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("error!!", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void clearTable(SQLiteDatabase db) {
            db.beginTransaction();
            try {
                String sql = "DELETE FROM " + tableName;
                db.execSQL(sql);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.d("ssival", e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
        public void clearTable2(SQLiteDatabase db) {
            db.beginTransaction();
            try {
                String sql = "DELETE FROM " + tableName2;
                db.execSQL(sql);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.d("ssival", e.getMessage());
            } finally {
                db.endTransaction();
            }
    }
}
