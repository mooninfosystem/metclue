package com.metclue.app.completeprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.metclue.app.R;
import com.metclue.app.dashboard.DashboardActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CompleteYourProfile extends AppCompatActivity {

    ImageView ivUserPhoto;
    MaterialCardView cvUserImage;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_your_profile);

        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        cvUserImage = findViewById(R.id.cvUserImage);

        cvUserImage.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE);
        });

        findViewById(R.id.tvSkipCompleteProfile).setOnClickListener(v -> {
            Intent intent = new Intent(CompleteYourProfile.this, DashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });

        findViewById(R.id.btnSignup).setOnClickListener(v -> {
            Intent intent = new Intent(CompleteYourProfile.this,DashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivUserPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(CompleteYourProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(CompleteYourProfile.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}