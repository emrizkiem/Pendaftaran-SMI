package com.emrizkiem.registersmi.ui.login;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.emrizkiem.registersmi.data.model.Users;
import com.emrizkiem.registersmi.repository.UsersRepository;

public class LoginViewModel extends ViewModel {
    private final UsersRepository repository;

    public LoginViewModel(Application application) {
        repository = new UsersRepository(application);
    }

    Users onLogin(String email, String password) {
        return repository.getUsersLogin(email, password);
    }
}
