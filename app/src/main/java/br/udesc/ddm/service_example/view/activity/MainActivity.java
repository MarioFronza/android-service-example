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
import java.util.List;

import br.udesc.ddm.service_example.R;
import br.udesc.ddm.service_example.service.MyService;


public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private ListView usersList;
    private Button startServiceButton;
    private Button stopServiceButton;

    private ArrayList<String> usersNameList;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextUsername);
        usersList = findViewById(R.id.usersListView);
        startServiceButton = findViewById(R.id.startServiceButton);
        stopServiceButton = findViewById(R.id.stopServiceButton);

        stopServiceButton.setVisibility(View.GONE);

        usersNameList = new ArrayList<>();
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersNameList);

        usersList.setAdapter(dataAdapter);
    }

    public void addUser(View view) {
        String name = editTextName.getText().toString();
        if (!name.equals("")) {
            usersNameList.add(name);
            dataAdapter.notifyDataSetChanged();
            editTextName.setText("");
        }

    }

    public void startService(View view) {
        if (!usersNameList.isEmpty()) {
            Toast.makeText(MainActivity.this, "Executando service", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyService.class);
            intent.putStringArrayListExtra("usersNameList", usersNameList);
            startService(intent);

            startServiceButton.setVisibility(View.GONE);
            stopServiceButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(MainActivity.this, "Informe pelo menos um nome!", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopProcess(View view) {
        Toast.makeText(MainActivity.this, "Encerrando service", Toast.LENGTH_SHORT).show();
        stopService(new Intent(this, MyService.class));
        startServiceButton.setVisibility(View.VISIBLE);
        stopServiceButton.setVisibility(View.GONE);
    }
}
