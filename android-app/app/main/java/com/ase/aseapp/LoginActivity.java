package com.ase.aseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            openWebView();
            return;
        }

        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        )
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.loginBtn).setOnClickListener(v -> signIn());
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {

        Toast.makeText(this, "Returned from Google picker", Toast.LENGTH_SHORT).show();

        Task<GoogleSignInAccount> task =
                GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            GoogleSignInAccount account =
                    task.getResult(ApiException.class);

            Toast.makeText(this, "Google account selected", Toast.LENGTH_SHORT).show();

            if (account == null) {
                Toast.makeText(this, "Account is NULL", Toast.LENGTH_LONG).show();
                return;
            }

            String idToken = account.getIdToken();

            if (idToken == null) {
                Toast.makeText(this, "ID TOKEN IS NULL ❌", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, "ID Token received ✅", Toast.LENGTH_SHORT).show();

            TokenStore.setIdToken(idToken);

            firebaseAuthWithGoogle(idToken);

        } catch (ApiException e) {
            Toast.makeText(this, "ApiException: " + e.getStatusCode(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
        }
                                

    private void firebaseAuthWithGoogle(String idToken) {

    Toast.makeText(this, "Firebase auth started", Toast.LENGTH_SHORT).show();

    AuthCredential credential =
            GoogleAuthProvider.getCredential(idToken, null);

    firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {
                    Toast.makeText(this, "Firebase auth SUCCESS ✅", Toast.LENGTH_LONG).show();
                    openWebView();
                } else {
                    Toast.makeText(this, "Firebase auth FAILED ❌", Toast.LENGTH_LONG).show();
                    if (task.getException() != null) {
                        task.getException().printStackTrace();
                    }
                }
            });
                                                    }
                     
