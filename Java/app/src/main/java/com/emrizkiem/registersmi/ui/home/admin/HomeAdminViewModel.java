package com.emrizkiem.registersmi.ui.home.admin;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emrizkiem.registersmi.data.model.Users;
import com.emrizkiem.registersmi.repository.UsersRepository;

import java.util.List;

public class HomeAdminViewModel extends ViewModel {
    private final UsersRepository repository;

    public HomeAdminViewModel(Application application) {
        repository = new UsersRepository(application);
    }

    LiveData<List<Users>> getAllMember() {
        return repository.getAllUsers();
    }
}
