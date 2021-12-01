package ru.startandroid.firebaseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText edName, edSecName, edEmail;
    private DatabaseReference myDataBase;
    private String USER_KEY = "User";
    private ImageView imImage;
    private StorageReference mStorageRef;
    private Uri uploadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = findViewById(R.id.edName);
        edSecName = findViewById(R.id.edSecondName);
        edEmail = findViewById(R.id.edEmail);
        myDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        imImage = findViewById(R.id.imImage);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDB");
    }

    public void onClickRead(View view) {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }

    public void onClickChooseImage(View view) {
        getImage();
    }

    private void getImage() {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {
                Log.d("MyLog", "Image URI : " + data.getData());
                imImage.setImageURI(data.getData());
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        Bitmap bitmap = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child(System.currentTimeMillis() + "my_image");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
            }
        });
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