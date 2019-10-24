package br.udesc.ddm.service_example.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.udesc.ddm.service_example.model.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

}
