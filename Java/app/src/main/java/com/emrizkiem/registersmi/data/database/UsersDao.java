package com.emrizkiem.registersmi.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.emrizkiem.registersmi.data.model.Users;

import java.util.List;

@Dao
public interface UsersDao {
    /**
     * @param users
     * This method for execute SQL (Structure Query Language)
     */
    @Insert()
    void insert(Users users);

    @Update()
    void update(Users users);

    @Delete()
    void delete(Users users);

    @Query("SELECT * FROM users where email LIKE :email")
    Users findByEmail(String email);

    @Query("SELECT * FROM users where email LIKE :email AND password LIKE :password")
    Users findByName(String email, String password);

    @Query("SELECT * FROM users ORDER BY id ASC")
    LiveData<List<Users>> getAllUsers();
}
