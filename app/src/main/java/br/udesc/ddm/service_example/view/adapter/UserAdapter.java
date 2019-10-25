package br.udesc.ddm.service_example.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.udesc.ddm.service_example.R;
import br.udesc.ddm.service_example.helper.DownloadImageTask;
import br.udesc.ddm.service_example.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView login;
        private ImageView avatar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.usernameTextView);
            login = itemView.findViewById(R.id.loginTextView);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        holder.login.setText(users.get(position).getLogin());
        new DownloadImageTask(holder.avatar).execute(users.get(position).getAvatar_url());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
