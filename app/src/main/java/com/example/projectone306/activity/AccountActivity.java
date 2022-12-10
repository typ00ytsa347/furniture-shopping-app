package com.example.projectone306.activity;
import android.accounts.Account;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectone306.data.model.User;
import com.example.projectone306.ui.login.AccountInfoFragment;
import com.example.projectone306.ui.login.LoginFragment;
import com.example.projectone306.ui.login.RegisterFragment;


import com.example.projectone306.R;

public class AccountActivity extends AppCompatActivity {

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // check user login/register state, and display different fragments accordingly
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (user == null) {
            fragmentTransaction.add(R.id.container, new RegisterFragment());
        } else if (!user.isLoggedIn()) {
            fragmentTransaction.add(R.id.container, new LoginFragment());
        } else {
            fragmentTransaction.add(R.id.container, new AccountInfoFragment());
        }

        fragmentTransaction.commit();

    }

    @NonNull
    @Override
    public String toString() {
        return "AccountActivity";
    }
}
