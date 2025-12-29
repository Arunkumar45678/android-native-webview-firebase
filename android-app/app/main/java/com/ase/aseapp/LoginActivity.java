package com.ase.aseapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            openWebView();
            return;
        }

        setContentView(R.layout.activity_login);

        findViewById(R.id.loginBtn).setOnClickListener(v -> {
            FirebaseAuth.getInstance()
                    .signInAnonymously()
                    .addOnSuccessListener(authResult -> openWebView());
        });
    }

    private void openWebView() {
        startActivity(new Intent(this, WebViewActivity.class));
        finish();
    }
}
