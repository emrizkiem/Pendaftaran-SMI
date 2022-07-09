package com.emrizkiem.registersmi.ui.register;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emrizkiem.registersmi.data.model.Users;
import com.emrizkiem.registersmi.repository.UsersRepository;

public class RegisterViewModel extends ViewModel {
    private final UsersRepository repository;

    public RegisterViewModel(Application application) {
        repository = new UsersRepository(application);
    }

    public void saveToDatabase(Users users) {
        repository.insert(users);
    }

    Users findByEmail(String email) {
        return repository.getUsersByEmail(email);
    }
}
