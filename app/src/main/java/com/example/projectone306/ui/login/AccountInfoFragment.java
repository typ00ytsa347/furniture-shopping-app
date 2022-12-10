package com.example.projectone306.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectone306.R;
import com.example.projectone306.activity.AccountActivity;
import com.example.projectone306.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInfoFragment extends Fragment {
    static String userID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialise variables and retrieve current user from database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        assert user != null;
        userID = user.getUid();
        Button logout = view.findViewById(R.id.logout);

        // logout button
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            AccountActivity.user.setLoggedIn(false);
            goToFragment(new LoginFragment());
        });

        // retrieve username and email from database
        final TextView usernameView = view.findViewById(R.id.username);
        final TextView emailView = view.findViewById(R.id.email);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userAccount = snapshot.getValue(User.class);
                AccountActivity.user = userAccount;

                if(userAccount != null) {
                    String username = userAccount.username;
                    String email = userAccount.emailAddress;
                    userAccount.setLoggedIn(true);

                    usernameView.setText(username);
                    emailView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error getting user info", Toast.LENGTH_LONG).show();
            }
        });

    }

    public static String getCurrentUserId(){
        return userID;
    }

    private void goToFragment(Fragment fragment) {
        FragmentActivity current = getActivity();
        assert current != null;
        FragmentTransaction fragmentTransaction = current.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment).commit();
    }

}