package halla.icsw.acca;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MaintenanceActivity extends AppCompatActivity {

    TextView engineOilAlertMessage;
    TextView autoOilAlertMessage;
    TextView powerOilAlertMessage;
    TextView brakeOilAlertMessage;
    TextView brakePadAlertMessage;
    TextView timingBeltAlertMessage;

    Button engineOilRefresh;
    Button autoOilRefresh;
    Button powerOilRefresh;
    Button brakeOilRefresh;
    Button brakePadeRefresh;
    Button timingBeltRefresh;

    double distance;

    // DB 관련 변수
    int version = 1;
    MyDatabaseOpenHelper helper;
    SQLiteDatabase database;

    //sharedPreferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        helper = new MyDatabaseOpenHelper(MaintenanceActivity.this, MyDatabaseOpenHelper.tableName, null, version);
        database = helper.getReadableDatabase();
        sharedPreferences = getSharedPreferences("cyclefile",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        distance = getDistance();
        double engineOilDistance = getDistance() - Double.parseDouble(sharedPreferences.getString("engineOil","0"));
        double autoOilDistance = getDistance() - Double.parseDouble(sharedPreferences.getString("autoOil","0"));
        double powerOilDistance = getDistance() - Double.parseDouble(sharedPreferences.getString("powerOil","0"));
        double brakeOilDistance = getDistance() - Double.parseDouble(sharedPreferences.getString("brakeOil","0"));
        double brakePadDistance = getDistance() - Double.parseDouble(sharedPreferences.getString("brakePad","0"));
        double timingBeltDistance = getDistance() - Double.parseDouble(sharedPreferences.getString("timingBelt","0"));

        final Cycle cycle = new Cycle();

        engineOilRefresh = findViewById(R.id.button1);
        autoOilRefresh = findViewById(R.id.button2);
        powerOilRefresh = findViewById(R.id.button3);
        brakeOilRefresh = findViewById(R.id.button4);
        brakePadeRefresh = findViewById(R.id.button5);
        timingBeltRefresh = findViewById(R.id.button6);

        engineOilRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("engineOil",String.valueOf(getDistance()));
                editor.commit();
                engineOilAlertMessage.setText(cycle.engineoilCycle(distance - Double.parseDouble(sharedPreferences.getString("engineOil","0"))) + "Km 후 교체 필요");
                changeTextColor(engineOilAlertMessage,cycle.engineoilState(distance - Double.parseDouble(sharedPreferences.getString("engineOil","0"))));
            }
        });

        autoOilRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("autoOil",String.valueOf(getDistance()));
                editor.commit();
                autoOilAlertMessage.setText(cycle.autooilCycle(distance - Double.parseDouble(sharedPreferences.getString("autoOil","0"))) + "Km 후 교체 필요");
                changeTextColor(autoOilAlertMessage,cycle.autooilState(distance - Double.parseDouble(sharedPreferences.getString("autoOil","0"))));
            }
        });

        powerOilRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("powerOil",String.valueOf(getDistance()));
                editor.commit();
                powerOilAlertMessage.setText(cycle.poweroilCycle(distance - Double.parseDouble(sharedPreferences.getString("powerOil","0"))) + "Km 후 교체 필요");
                changeTextColor(powerOilAlertMessage,cycle.poweroilState(distance - Double.parseDouble(sharedPreferences.getString("powerOil","0"))));
            }
        });

        brakeOilRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("brakeOil",String.valueOf(getDistance()));
                editor.commit();
                brakeOilAlertMessage.setText(cycle.brakeoilCycle(distance - Double.parseDouble(sharedPreferences.getString("brakeOil","0"))) + "Km 후 교체 필요");
                changeTextColor(brakeOilAlertMessage,cycle.brakeoilState(distance - Double.parseDouble(sharedPreferences.getString("brakeOil","0"))));
            }
        });

        brakePadeRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("brakePad",String.valueOf(getDistance()));
                editor.commit();
                brakePadAlertMessage.setText(cycle.brakepadeCycle(distance - Double.parseDouble(sharedPreferences.getString("brakePad","0"))) + "Km 후 교체 필요");
                changeTextColor(brakePadAlertMessage,cycle.brakepadeState(distance - Double.parseDouble(sharedPreferences.getString("brakePad","0"))));
            }
        });

        timingBeltRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("timingBelt",String.valueOf(getDistance()));
                editor.commit();
                timingBeltAlertMessage.setText(cycle.timingbeltCycle(distance - Double.parseDouble(sharedPreferences.getString("timingBelt","0"))) + "Km 후 교체 필요");
                changeTextColor(timingBeltAlertMessage,cycle.timingbeltState(distance - Double.parseDouble(sharedPreferences.getString("timingBelt","0"))));
            }
        });

        engineOilAlertMessage = findViewById(R.id.alertMessage1);
        String engineOil = cycle.engineoilCycle(engineOilDistance);
        autoOilAlertMessage = findViewById(R.id.alertMessage2);
        String autoOil = cycle.autooilCycle(autoOilDistance);
        powerOilAlertMessage = findViewById(R.id.alertMessage3);
        String powerOil = cycle.poweroilCycle(powerOilDistance);
        brakeOilAlertMessage = findViewById(R.id.alertMessage4);
        String brakeOil = cycle.brakeoilCycle(brakeOilDistance);
        brakePadAlertMessage = findViewById(R.id.alertMessage5);
        String brakePade = cycle.brakepadeCycle(brakePadDistance);
        timingBeltAlertMessage = findViewById(R.id.alertMessage6);
        String timingBelt = cycle.timingbeltCycle(timingBeltDistance);

        engineOilAlertMessage.setText(engineOil + "Km 후 교체 필요");
        changeTextColor(engineOilAlertMessage, cycle.engineoilState(distance));
        autoOilAlertMessage.setText(autoOil + "Km 후 교체 필요");
        changeTextColor(autoOilAlertMessage,cycle.autooilState(distance));
        powerOilAlertMessage.setText(powerOil + "Km 후 교체 필요");
        changeTextColor(powerOilAlertMessage,cycle.poweroilState(distance));
        brakeOilAlertMessage.setText(brakeOil + "Km 후 교체 필요");
        changeTextColor(brakeOilAlertMessage,cycle.brakeoilState(distance));
        brakePadAlertMessage.setText(brakePade + "Km 후 교체 필요");
        changeTextColor(brakePadAlertMessage,cycle.brakepadeState(distance));
        timingBeltAlertMessage.setText(timingBelt + "Km 후 교체 필요");
        changeTextColor(timingBeltAlertMessage,cycle.timingbeltState(distance));

    }

    void changeTextColor(TextView textView, int state) {
        if (state == 0)
            textView.setTextColor(Color.RED);
        else if (state == 1)
            textView.setTextColor(Color.parseColor("#F0C420"));
        else
            textView.setTextColor(Color.BLUE);
    }

    double getDistance(){
        String selectSQL = "SELECT SUM(distance) FROM " + helper.tableName;
        Cursor cursor = database.rawQuery(selectSQL, null);
        int count = cursor.getCount();
        if (count == 0) {
            distance = 0;
        } else {
            cursor.moveToFirst();
            distance = cursor.getDouble(0);
        }
        return distance;
    }
}
