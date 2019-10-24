package br.udesc.ddm.service_example.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import br.udesc.ddm.service_example.service.MyService;
import br.udesc.ddm.service_example.view.observer.Observer;


public class MainActivity extends AppCompatActivity implements Observer {

    private EditText editTextName;
    private ListView usersList;
    private Button startServiceButton;
    private Button stopServiceButton;

    private ArrayList<String> users;
    private ArrayAdapter<String> dataAdapter;

    private ServiceController serviceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceController = ServiceController.getInstance();
        serviceController.addObserver(this);
        serviceController.setContext(getApplicationContext());


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
        if (!users.isEmpty()) {
            startServiceButton.setVisibility(View.GONE);
            stopServiceButton.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, MyService.class);
            intent.putStringArrayListExtra("users", users);
            startService(intent);
        } else {
            Toast.makeText(MainActivity.this, "Informe pelo menos um nome!", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopProcess(View view) {
        startServiceButton.setVisibility(View.VISIBLE);
        stopServiceButton.setVisibility(View.GONE);
        stopService(new Intent(this, MyService.class));
    }

    public void viewUsers(View view) {
        startActivity(new Intent(MainActivity.this, UsersActivity.class));
    }

    @Override
    public void userListUpdate() {
        users = serviceController.getUsers();
        dataAdapter.notifyDataSetChanged();
        usersList.requestLayout();
    }

}
