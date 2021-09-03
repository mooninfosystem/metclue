package com.metclue.app.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.metclue.app.R;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class OTPActivity extends AppCompatActivity {

    private OtpView otpView;
    TextView tvResendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);


        otpView = findViewById(R.id.otp_view);
        tvResendCode = findViewById(R.id.tvResendCode);
        String redString = getResources().getString(R.string.resend_code_txt);
        tvResendCode.setText(Html.fromHtml(redString));

        otpView.setOtpCompletionListener(otp ->
                Toast.makeText(OTPActivity.this, ""+otp, Toast.LENGTH_SHORT).show());

    }
}