package br.udesc.ddm.service_example.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import br.udesc.ddm.service_example.R;
import br.udesc.ddm.service_example.controller.ServiceController;
import br.udesc.ddm.service_example.model.User;
import br.udesc.ddm.service_example.view.adapter.UserAdapter;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceController serviceController;
    private List<User> users;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        serviceController = ServiceController.getInstance();
        users = serviceController.getAllUsers();

        userAdapter = new UserAdapter(users);
        recyclerView = findViewById(R.id.recyclerUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);

        swipeConfiguration();

    }

    public void swipeConfiguration() {
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                serviceController.deleteUser(users.get(viewHolder.getAdapterPosition()));
                userAdapter.setUsers(serviceController.getAllUsers());
                userAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }
}
