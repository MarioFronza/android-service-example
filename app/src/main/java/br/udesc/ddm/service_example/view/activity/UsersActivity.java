package br.udesc.ddm.service_example.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import br.udesc.ddm.service_example.R;
import br.udesc.ddm.service_example.controller.ServiceController;
import br.udesc.ddm.service_example.view.adapter.UserAdapter;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceController serviceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        serviceController = ServiceController.getInstance();

        recyclerView = findViewById(R.id.recyclerUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(serviceController.getAllUsers()));

    }
}
