package com.example.projectone306.ui.login;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone306.R;
import com.example.projectone306.activity.AccountActivity;
import com.example.projectone306.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FirebaseAuth auth;
    private EditText editUsername, editPassword, editReEnterPassword, editEmail;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialise variables
        TextView login = view.findViewById(R.id.linkLogin);
        Button register = view.findViewById(R.id.register);
        editUsername = view.findViewById(R.id.username);
        editPassword = view.findViewById(R.id.password);
        editReEnterPassword = view.findViewById(R.id.re_enter_password);
        editEmail = view.findViewById(R.id.email);
        progressBar = view.findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        // switch to login page
        login.setOnClickListener(v -> goToFragment(new LoginFragment()));

        register.setOnClickListener(v -> checkUserInfo());
    }

    // check that the information the user has entered is valid
    private void checkUserInfo() {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String reEnterPassword = editReEnterPassword.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if(username.isEmpty()) {
            editUsername.setError("Please enter your username");
            editUsername.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editPassword.setError("Please enter your password");
            editPassword.requestFocus();
            return;
        }

        if(reEnterPassword.isEmpty()) {
            editReEnterPassword.setError("Please re-enter your password");
            editReEnterPassword.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            editEmail.setError("Please enter your email");
            editEmail.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editPassword.setError("Password need to be at least 6 characters long");
            editPassword.requestFocus();
            return;
        }

        if(!password.equals(reEnterPassword)) {
            editReEnterPassword.setError("Passwords do not match");
            editReEnterPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        registerUser(username, password, email);
    }

    // create a new user with firebase
    private void registerUser(String username, String password, String email) {

        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        User user = new User(username, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                                    if(task1.isSuccessful()) {
                                        Toast.makeText(getActivity(), "User successfully registered", Toast.LENGTH_LONG).show();

                                        AccountActivity.user.setLoggedIn(true);
                                        goToFragment(new AccountInfoFragment());
                                    } else {
                                        Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
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