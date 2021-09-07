package com.metclue.app.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.metclue.app.R;
import com.metclue.app.networking.ApiService;
import com.metclue.app.networking.ApiUtils;
import com.metclue.app.utils.GlobalFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tvCountryCode, tvSignUp;
    Dialog myDialog;
    String selectedCountryCode = "91";
    KProgressHUD kProgressHUD;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvCountryCode = findViewById(R.id.tvCountryCode);
        tvSignUp = findViewById(R.id.tvSignUp);
        etPhone = findViewById(R.id.etPhone);

        String redString = getResources().getString(R.string.signup_text);
        tvSignUp.setText(Html.fromHtml(redString));
        kProgressHUD = new KProgressHUD(LoginActivity.this);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                String phone = etPhone.getText().toString().trim();
//                if (phone.length() == 0 || phone.length() < 10) {
//                    GlobalFunctions.shakeanimation(LoginActivity.this, etPhone);
//                    etPhone.setError("Please enter valid phone number");
//                } else {
//                    loginuser();
//                }
            }
        });

        tvCountryCode.setOnClickListener(v -> showDialog());

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;
        String country = "IN";
        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
        tvCountryCode.setText(flag + " 91");

    }

    public void loginuser() {
        GlobalFunctions.showProgressDialog(kProgressHUD);
        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone", etPhone.getText().toString().trim())
                .addFormDataPart("deviceToken", GlobalFunctions.getDeviceID(this))
                .addFormDataPart("deviceType", 2 + "")
                .build();

        ApiService apiService = ApiUtils.getAPIService();
        apiService.registerUser(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                if (response.code() == 200) {
                    try {
                        String res = response.body().string();
                        Log.e("register success ", res);
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }

                    } catch (IOException | JSONException e) {
                        Log.e("register error ", e.getMessage());
                    }

                } else {
                    try {
                        Log.e("register error 1 ", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Log.e("register error 2 ", t.getMessage());
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private void showDialog() {

        myDialog = new Dialog(this);
        myDialog.setCancelable(true);
        myDialog.setContentView(R.layout.choose_country_dialog);

        ListView listView;

        myDialog.show();

        String[] recourseList = this.getResources().getStringArray(R.array.CountryCodes);

        listView = myDialog.findViewById(R.id.listView);
        listView.setAdapter(new CountriesListAdapter(this, recourseList));
    }


    public class CountriesListAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public CountriesListAdapter(Context context, String[] values) {
            super(context, R.layout.country_list_item, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.country_list_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.txtViewCountryName);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imgViewFlag);

            String[] g = values[position].split(",");
            int flagOffset = 0x1F1E6;
            int asciiOffset = 0x41;
            String country = g[1].trim();
            int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
            int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;
            String flag = new String(Character.toChars(firstChar))
                    + new String(Character.toChars(secondChar));
            textView.setText(flag + " " + GetCountryZipCode(g[1]).trim());

            textView.setOnClickListener(v -> {
                tvCountryCode.setText(flag + " " + g[0].trim());
                selectedCountryCode = g[0].trim();
                myDialog.dismiss();
            });

            return rowView;
        }
    }

    private String GetCountryZipCode(String ssid) {
        Locale loc = new Locale("", ssid);
        return loc.getDisplayCountry().trim();
    }


}