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

    public ArrayList<String> getUsers() {
        return users;
    }

    public void addUser(String name) {
        this.users.add(name);
        notifyUserListUpdate();
    }

    public void removeUser(String name) {
        this.users.remove(name);
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void notifyUserListUpdate() {
        for (Observer observer : observers) {
            observer.userListUpdate();
        }
    }




}
