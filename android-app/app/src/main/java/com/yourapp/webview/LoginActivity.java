package com.ase.aseapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.*;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    GoogleSignInClient googleClient;
    int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso =
            new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.btnGoogle).setOnClickListener(v ->
            startActivityForResult(
                googleClient.getSignInIntent(), RC_SIGN_IN
            )
        );
    }

    @Override
    protected void onActivityResult(int r, int c, Intent d) {
        super.onActivityResult(r, c, d);
        if (r == RC_SIGN_IN) {
            try {
                GoogleSignInAccount acct =
                    GoogleSignIn.getSignedInAccountFromIntent(d)
                        .getResult(ApiException.class);

                AuthCredential cred =
                    GoogleAuthProvider.getCredential(
                        acct.getIdToken(), null);

                mAuth.signInWithCredential(cred)
                    .addOnSuccessListener(res -> {

                        FirebaseUser user =
                            FirebaseAuth.getInstance().getCurrentUser();

                        user.getIdToken(true)
                            .addOnSuccessListener(t -> {
                                Intent i =
                                    new Intent(this,
                                        WebViewActivity.class);
                                i.putExtra("TOKEN", t.getToken());
                                startActivity(i);
                                finish();
                            });
                    });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
