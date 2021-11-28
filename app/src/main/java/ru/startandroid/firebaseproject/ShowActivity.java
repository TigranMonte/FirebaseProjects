package ru.startandroid.firebaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    private TextView tvName, tvSecName, tvEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);

        tvName = findViewById(R.id.tvName);
        tvSecName = findViewById(R.id.tvSecName);
        tvEmail = findViewById(R.id.tvEmail);
        getIntentMain();
    }

    private void getIntentMain() {
        Intent intent = getIntent();
        if (intent != null) {
            tvName.setText(intent.getStringExtra("user_name"));
            tvSecName.setText(intent.getStringExtra("user_sec_name"));
            tvEmail.setText(intent.getStringExtra("user_email"));
        }
    }
}
