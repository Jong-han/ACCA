package halla.icsw.acca;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    Intent intent1;

    @Override
    public void onReceive(Context context, Intent intent) {

        intent1 = new Intent(context, OilActivity.class);
        String oilStation = "";
        String price = "";

        MyDatabaseOpenHelper helper = new MyDatabaseOpenHelper(context, MyDatabaseOpenHelper.tableName2, null, 1);
        SQLiteDatabase database = helper.getWritableDatabase();

        Bundle bundle = intent.getExtras();
        String str = ""; // 출력할 문자열 저장
        if (bundle != null) { // 수신된 내용이 있으면
            // 실제 메세지는 Object타입의 배열에 PDU 형식으로 저장됨
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += msgs[i].getMessageBody().toString();
            }


            if (str.contains("주유소")) {
                String[] array = str.split("\\s");
                for (int i = 0; i < array.length; i++) {
                    if (array[i].contains("주유소")) {
                        oilStation = array[i];
                        break;
                    }
                }
                for (int i = 0; i < array.length; i++) {
                    if (array[i].contains(",")) {
                        price = array[i];
                        break;
                    }
                }
            }

            helper.insertOil(database, oilStation, price);

        }
    }
}


