package halla.icsw.acca;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private Location mLastLocation = null;

    private TextView distance_view;
    private TextView totaldistance_view;

    private Button startButton;
    private Button endButton;
    private Button goMaintenance;
    private Button goDriveButton;
    private Button goOil;

    private double curDistance = 0;
    private double totaldistance = 0;

    // DB 관련 변수
    int version = 1;
    MyDatabaseOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startCheckSpeed);
        endButton = findViewById(R.id.endCheckSpeed);
        distance_view = findViewById(R.id.tempDistance);
        totaldistance_view = findViewById(R.id.totalDistance);
        goMaintenance = findViewById(R.id.goMaintenance);
        goDriveButton = findViewById(R.id.goDrive);
        goOil = findViewById(R.id.goOiling);

        helper = new MyDatabaseOpenHelper(MainActivity.this, MyDatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        showTotalDistance();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        //주행 시작 버튼
        startButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "주행을 시작합니다.", Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //주행 종료 버튼
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "주행을 종료합니다.", Toast.LENGTH_SHORT).show();
                distance_view.setText("최근 주행거리 : " + curDistance + "Km");
                locationManager.removeUpdates(locationListener);
                long timeNow = System.currentTimeMillis();
                Date date = new Date(timeNow);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String now = sdf.format(date);

                helper.insertDrive(database, now, curDistance);
                showTotalDistance();
                curDistance = 0;
            }
        });

        final Intent intent = new Intent(this, MaintenanceActivity.class);

        //차량 정비 버튼
        goMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        final Intent intent2 = new Intent(this,OilActivity.class);

        //주유 기록 버튼
        goOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        goOil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                helper.clearTable2(database);
                Toast.makeText(MainActivity.this, "dd", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        final Intent intent1 = new Intent(this, DriveRecord.class);
        //주행이력 버튼
        goDriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(intent1);
            }
        });
    }

    final LocationListener locationListener = new LocationListener() {

        //gps 위치가 바뀔 때마다 curDistance에 지속적으로 더해짐
        @Override
        public void onLocationChanged(Location location) {
            if (mLastLocation != null) {
                String temp = String.format("%.3f",(double)mLastLocation.distanceTo(location) / 1000d);
                curDistance += Double.parseDouble(temp);
            }
            mLastLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    //총 주행거리를 출력하는 변수
    void showTotalDistance() {
        String selectSQL = "SELECT SUM(distance) FROM " + helper.tableName;
        Cursor cursor = database.rawQuery(selectSQL, null);
        int count = cursor.getCount();
        if (count == 0) {
            totaldistance_view.setText("주행하지 않았습니다.");
        } else {
            cursor.moveToFirst();
            String temp = String.format("%.3f",cursor.getDouble(0));
            totaldistance = Double.parseDouble(temp);
            totaldistance_view.setText("누적 주행거리 : " + totaldistance + "Km");
        }
    }
}
