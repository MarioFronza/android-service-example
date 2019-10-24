package br.udesc.ddm.service_example.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import android.os.Process;


import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import br.udesc.ddm.service_example.controller.ServiceController;
import br.udesc.ddm.service_example.dao.AppDatabase;
import br.udesc.ddm.service_example.model.User;
import br.udesc.ddm.service_example.retrofit.RetrofitInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyService extends Service {

    private ArrayList<String> users;
    private ServiceController serviceController;

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Call<User> call;
            for (String name : users) {
                serviceController.removeUser(name);
                call = new RetrofitInitializer().getUserService().getUser(name);
                call.enqueue(new retrofit2.Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            saveUser(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha na requisição", Toast.LENGTH_SHORT).show();
                        }
                        serviceController.notifyUserListUpdate();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erro na requisição", Toast.LENGTH_SHORT).show();
                    }

                });

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            stopSelf(msg.arg1);
        }
    }

    public void saveUser(User user) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        db.userDao().insert(user);
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceController = ServiceController.getInstance();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        users = intent.getExtras().getStringArrayList("users");
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
