package br.udesc.ddm.service_example.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.udesc.ddm.service_example.R;
import br.udesc.ddm.service_example.controller.ServiceController;
import br.udesc.ddm.service_example.service.BackgroundUserService;
import br.udesc.ddm.service_example.view.observer.Observer;


public class MainActivity extends AppCompatActivity implements Observer {

    private static final String CHANNEL_1_ID = "channle1";
    private static final String CHANNEL_2_ID = "channle2";

    private EditText editTextName;
    private ListView usersList;
    private Button startServiceButton;
    private Button stopServiceButton;

    private ArrayList<String> users;
    private ArrayAdapter<String> dataAdapter;

    private ServiceController serviceController;

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannels();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        serviceController = ServiceController.getInstance();
        serviceController.addObserver(this);
        serviceController.startDatabse(getApplicationContext());


        editTextName = findViewById(R.id.editTextUsername);
        usersList = findViewById(R.id.usersListView);
        startServiceButton = findViewById(R.id.startServiceButton);
        stopServiceButton = findViewById(R.id.stopServiceButton);

        stopServiceButton.setVisibility(View.GONE);


        users = serviceController.getUsers();
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        usersList.setAdapter(dataAdapter);
    }

    public void addUser(View view) {
        String name = editTextName.getText().toString();
        if (!name.equals("")) {
            serviceController.addUser(name);
            editTextName.setText("");
        }
    }

    public void startService(View view) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_person_black_24dp)
                .setContentTitle("teste")
                .setContentText("teste de content text")
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);

        if (!users.isEmpty()) {
            startServiceButton.setVisibility(View.GONE);
            stopServiceButton.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, BackgroundUserService.class);
            intent.putStringArrayListExtra("users", users);
            startService(intent);
        } else {
            Toast.makeText(MainActivity.this, "Informe pelo menos um nome!", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopProcess(View view) {
        startServiceButton.setVisibility(View.VISIBLE);
        stopServiceButton.setVisibility(View.GONE);
        stopService(new Intent(this, BackgroundUserService.class));
    }

    public void viewUsers(View view) {
        startActivity(new Intent(MainActivity.this, UsersActivity.class));
    }

    @Override
    public void userListUpdate() {
        users = serviceController.getUsers();
        dataAdapter.notifyDataSetChanged();
        usersList.requestLayout();
        if (users.isEmpty()) {
            startServiceButton.setVisibility(View.VISIBLE);
            stopServiceButton.setVisibility(View.GONE);
        }
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channle 1");


            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID, "Channel 2", NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is Channle 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }

    }

}
