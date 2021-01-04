package halla.icsw.acca;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class OilActivity extends AppCompatActivity {

    private TextView totalPrice;
    private TextView oilStation;
    private TextView oilPrice;

    MyDatabaseOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        totalPrice = findViewById(R.id.tetete);
        oilStation = findViewById(R.id.oilStation);
        oilStation.setWidth(size.x);
        oilPrice = findViewById(R.id.oilPrise);
        oilPrice.setWidth(size.x);

        helper = new MyDatabaseOpenHelper(OilActivity.this, MyDatabaseOpenHelper.tableName2, null, 1);
        database = helper.getReadableDatabase();

        showingOil();
        showTotalOilPrice();

    }

    void showingOil() {
        String selectSQL = "SELECT * FROM " + helper.tableName2;
        String oilStation = "";
        String oilPrice = "";
        Cursor cursor = database.rawQuery(selectSQL, null);
        int count = cursor.getCount();
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                cursor.moveToNext();
                if (!cursor.isLast()) {
                    oilStation += cursor.getString(0) + "\n";
                    oilPrice += cursor.getString(1) + "\n";
                } else {
                    oilStation += cursor.getString(0);
                    oilPrice += cursor.getString(1);
                }
            }
            this.oilStation.setText(oilStation);
            this.oilPrice.setText(oilPrice);
        } else {
            this.oilStation.setText("");
            this.oilPrice.setText("");
        }
    }

    void showTotalOilPrice() {
        String selectSQL = "SELECT Price FROM " + helper.tableName2;
        DecimalFormat format = new DecimalFormat("###,###");
        int temp = 0;
        String temp2 = "";
        String temp3 = "";
        Cursor cursor = database.rawQuery(selectSQL, null);
        int count = cursor.getCount();
        if (count == 0) {
            totalPrice.setText("누적 주유 금액 : 0원");
        } else {
            for (int i = 0; i < count; i++) {
                cursor.moveToNext();
                String cursorString = cursor.getString(0);
                temp3 = cursorString.replace("원", "");
                temp += Integer.parseInt(temp3.replace(",", ""));
            }
            temp2 = format.format(temp);
            totalPrice.setText("누적 주유 금액 : " + temp2 + "원");
        }
    }
}
