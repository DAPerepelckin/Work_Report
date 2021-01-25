package com.dapstd.workreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }

        FragmentContainerView fragment = findViewById(R.id.fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (!preferences.getString("email", "").isEmpty() || !preferences.getString("password", "").isEmpty()) {
            mAuth.signInWithEmailAndPassword(preferences.getString("email", ""), preferences.getString("password", ""))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, WelcomeActivity.class));
                            finish();
                        } else {
                            fragmentManager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .add(R.id.fragment, BlankFragment.class, null)
                                    .commit();
                        }
                    });
        } else {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment, BlankFragment.class, null)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack("main", 0);
    }
}