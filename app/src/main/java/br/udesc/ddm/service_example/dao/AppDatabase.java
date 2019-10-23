package br.udesc.ddm.service_example.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.udesc.ddm.service_example.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDao();

}