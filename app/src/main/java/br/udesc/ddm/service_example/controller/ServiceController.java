package br.udesc.ddm.service_example.controller;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import br.udesc.ddm.service_example.dao.AppDatabase;
import br.udesc.ddm.service_example.model.User;
import br.udesc.ddm.service_example.view.observer.Observer;

public class ServiceController {

    private static ServiceController instance;

    private ArrayList<String> users;
    private List<Observer> observers;
    private AppDatabase db;

    public static ServiceController getInstance() {
        if (instance == null) {
            instance = new ServiceController();
        }

        return instance;
    }

    private ServiceController() {
        this.users = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void startDatabse(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "users").allowMainThreadQueries().build();
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void addUserName(String name) {
        this.users.add(name);
        notifyUserListUpdate();
    }

    public void removeUserName(String name) {
        this.users.remove(name);
    }

    public void saveUser(User user) {
        db.userDao().insertUser(user);
    }

    public void deleteUser(User user) {
        db.userDao().deleteUser(user);
    }

    public List<User> getAllUsers() {
        return db.userDao().getAllUsers();
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void notifyUserListUpdate() {
        for (Observer observer : observers) {
            observer.userListUpdate();
        }
    }

    public void notifyCreateUserSuccess(String username) {
        for (Observer observer : observers) {
            observer.showSuccessNotification(username);
        }
    }

    public void notifyCreateUserError(String username) {
        for (Observer observer : observers) {
            observer.showErrorNotification(username);
        }
    }


}
