package com.example.projectone306.ui.login;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projectone306.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private EditText editEmail, editPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialise variables
        TextView signup = view.findViewById(R.id.linkSignUp);
        Button login = view.findViewById(R.id.login);
        editEmail = view.findViewById(R.id.email);
        editPassword = view.findViewById(R.id.password);
        progressBar = view.findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        // listener for switching to register page
        signup.setOnClickListener(v -> goToFragment(new RegisterFragment()));

        login.setOnClickListener(v -> checkUserInfo());
    }

    // check that the info the user has entered is valid
    private void checkUserInfo() {

        // get the strings user has entered
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editEmail.setError("Please enter your email");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editPassword.setError("Please enter your password");
            editPassword.requestFocus();
            return;
        }

        userLogin(email, password);
    }

    // perform the login action
    private void userLogin(String email, String password) {

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if(task.isSuccessful()) {
                goToFragment(new AccountInfoFragment());
            } else {
                editEmail.setError("Email/Password incorrect :(");
                progressBar.setVisibility(View.GONE);
                editEmail.requestFocus();
            }

        });

    }

    private void goToFragment(Fragment fragment) {
        FragmentActivity current = getActivity();
        assert current != null;
        FragmentTransaction fragmentTransaction = current.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment).commit();
    }

}