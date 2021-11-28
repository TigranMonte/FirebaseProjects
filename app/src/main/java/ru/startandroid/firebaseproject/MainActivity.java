package ru.startandroid.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edName, edSecName, edEmail;
    private DatabaseReference myDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = findViewById(R.id.edName);
        edSecName = findViewById(R.id.edSecondName);
        edEmail = findViewById(R.id.edEmail);
        myDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickRead(View view) {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }

    public void onClickSave(View view) {
        String id = myDataBase.getKey();
        String name = edName.getText().toString();
        String secName = edSecName.getText().toString();
        String email = edEmail.getText().toString();
        User newUser = new User(id, name, secName, email);

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(secName) && !TextUtils.isEmpty(email)){
            myDataBase.push().setValue(newUser);
            Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}