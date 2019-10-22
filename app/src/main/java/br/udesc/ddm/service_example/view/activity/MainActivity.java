package br.udesc.ddm.service_example.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.udesc.ddm.service_example.R;


public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private ListView usersList;
    private Button startServiceButton;
    private Button stopServiceButton;

    private List<String> usersNameList;
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
        startServiceButton.setVisibility(View.GONE);
        stopServiceButton.setVisibility(View.VISIBLE);
    }

    public void stopProcess(View view) {
        startServiceButton.setVisibility(View.VISIBLE);
        stopServiceButton.setVisibility(View.GONE);
    }
}
