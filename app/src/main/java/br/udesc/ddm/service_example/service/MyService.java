package br.udesc.ddm.service_example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class MyService extends Service {

    ArrayList<String> users;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        users = intent.getExtras().getStringArrayList("usersNameList");
        for (String user : users) {
            Toast.makeText(MyService.this, user, Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
