package com.emrizkiem.registersmi.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.emrizkiem.registersmi.data.database.UsersDao;
import com.emrizkiem.registersmi.data.database.UsersDatabase;
import com.emrizkiem.registersmi.data.model.Users;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsersRepository {
    private final UsersDao usersDao;
    private final ExecutorService executorService;

    public UsersRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        UsersDatabase database = UsersDatabase.getDatabase(application);
        usersDao = database.usersDao();
    }

    public void insert(final Users users) {
        executorService.execute(() -> usersDao.insert(users));
    }

    public void update(final Users users) {
        executorService.execute(() -> usersDao.update(users));
    }

    public void delete(final Users users) {
        executorService.execute(() -> usersDao.delete(users));
    }

    public Users getUsersByEmail(String email) {
        return usersDao.findByEmail(email);
    }

    public Users getUsersLogin(String email, String password) {
        return usersDao.findByName(email, password);
    }

    public LiveData<List<Users>> getAllUsers() {
        return usersDao.getAllUsers();
    }
}
