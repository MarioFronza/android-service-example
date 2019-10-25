package br.udesc.ddm.service_example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import android.os.Process;

import java.util.ArrayList;

import br.udesc.ddm.service_example.controller.ServiceController;
import br.udesc.ddm.service_example.model.User;
import br.udesc.ddm.service_example.retrofit.RetrofitInitializer;
import retrofit2.Call;
import retrofit2.Response;


public class BackgroundUserService extends Service {

    private ArrayList<String> users;
    private ServiceController serviceController;
    private ServiceHandler serviceHandler;
    private String username;

    private boolean isRunning;

    private final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Call<User> call;
            int i = 0;
            while (!users.isEmpty() && isRunning) {
                String name = users.remove(i);
                username = name;
                serviceController.removeUser(name);
                call = new RetrofitInitializer().getUserService().getUser(name);
                call.enqueue(new retrofit2.Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            serviceController.saveUser(user);
                            serviceController.notifyCreateUserSuccess(username);
                        } else {
                            serviceController.notifyCreateUserError(username);
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
                i++;
            }
            stopSelf(msg.arg1);
        }
    }


    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        isRunning = true;
        serviceController = ServiceController.getInstance();
        Looper serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
        super.onDestroy();
        isRunning = false;
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
