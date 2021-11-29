package ru.startandroid.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edLogin, edPassword;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edLogin = findViewById(R.id.edLogin);
        edPassword = findViewById(R.id.edPassword);
        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = myAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "New user", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view){
        if (!TextUtils.isEmpty(edLogin.getText().toString()) &&
                !TextUtils.isEmpty(edPassword.getText().toString())) {
            myAuth.createUserWithEmailAndPassword();
        }
    }

    public void onClickSignIn(View view){

    }
}