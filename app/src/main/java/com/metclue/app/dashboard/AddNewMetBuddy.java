package com.metclue.app.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.metclue.app.R;
import com.metclue.app.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class AddNewMetBuddy extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    public static final int PICK_BUDDY_IMAGE = 1;
    public static final int PICK_SELFIE_IMAGE = 2;
    EditText etAboutBuddy,etCategory;
    ImageView ivSpeak,ivBuddyPhoto,ivSelfiePhoto;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_met_buddy);

        getViews();
    }

    public void getViews() {
        ivSpeak = findViewById(R.id.ivSpeak);
        etAboutBuddy = findViewById(R.id.etAboutBuddy);
        etCategory = findViewById(R.id.etCategory);
        ivSelfiePhoto = findViewById(R.id.ivSelfiePhoto);
        ivBuddyPhoto = findViewById(R.id.ivBuddyPhoto);

        ivSpeak.setOnClickListener(v -> displaySpeechRecognizer());
        etCategory.setOnClickListener(v -> {});
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            etAboutBuddy.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void showDialog() {

        myDialog = new Dialog(this);
        myDialog.setCancelable(true);
        myDialog.setContentView(R.layout.choose_buddy_category);






        myDialog.show();
    }


}