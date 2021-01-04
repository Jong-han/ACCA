package halla.icsw.acca;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DriveRecord extends AppCompatActivity {

    private TextView driveRecord;
    private TextView totalRecord;
    private TextView driveDate;
    private TextView driveDistance;

    int version = 1;
    MyDatabaseOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_record);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        helper = new MyDatabaseOpenHelper(DriveRecord.this, MyDatabaseOpenHelper.tableName, null, version);
        database = helper.getReadableDatabase();

        totalRecord = findViewById(R.id.totalrecord);
        showingTotalRecord();

        driveDate = findViewById(R.id.driveDate);
        driveDate.setWidth(size.x);
        driveDistance = findViewById(R.id.driveDistance);
        driveDistance.setWidth(size.x);
        showingDrive();

    }

    void showingDrive() {
        String selectSQL = "SELECT * FROM " + helper.tableName;
        String date = "";
        String distance = "";
        Cursor cursor = database.rawQuery(selectSQL, null);
        int count = cursor.getCount();
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                cursor.moveToNext();
                if (!cursor.isLast()) {
                    date += cursor.getString(0) + "\n";
                    distance += cursor.getString(1) + "Km\n";
                } else {
                    date += cursor.getString(0);
                    distance += cursor.getString(1) + "Km";
                }

            }
            driveDate.setText(date);
            driveDistance.setText(distance);
        } else {
            driveDate.setText("");
            driveDistance.setText("");
        }
    }

    void showingTotalRecord() {
        String selectSQL = "SELECT SUM(distance) FROM " + helper.tableName;
        Cursor cursor = database.rawQuery(selectSQL, null);
        int count = cursor.getCount();
        if (count == 0) {
            totalRecord.setText("누적 주행거리 : 0Km");
        } else {
            cursor.moveToFirst();
            String temp = String.format("%.3f", cursor.getDouble(0));
            double totaldistance = Double.parseDouble(temp);
            totalRecord.setText("누적 주행거리 : " + totaldistance + "Km");
        }
    }
}
