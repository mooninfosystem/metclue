package com.metclue.app.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.metclue.app.R;
import com.metclue.app.networking.ApiService;
import com.metclue.app.networking.ApiUtils;
import com.metclue.app.networking.RetrofitClient;
import com.metclue.app.utils.GlobalFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    TextView tvCountryCode;
    String selectedCountryCode = "91";
    Dialog myDialog;
    EditText etResidentCity, etPhone, etName;
    KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        kProgressHUD = new KProgressHUD(this);
        tvCountryCode = findViewById(R.id.tvCountryCode);
        findViewById(R.id.ivBack).setOnClickListener(v -> onBackPressed());
        setCountryCodes();

        etResidentCity = findViewById(R.id.etResidentCity);
        etPhone = findViewById(R.id.etPhone);
        etName = findViewById(R.id.etName);

        findViewById(R.id.btnSignup).setOnClickListener(v -> {
//            Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


//            String value = etResidentCity.getText().toString();
//            Log.e("value", value);

            validateFields();

        });
    }

    public void validateFields() {
        String residentCity = etResidentCity.getText().toString().trim();
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        String lat = "", lng = "", country = "";
        try {
            GlobalFunctions.showProgressDialog(kProgressHUD);
            List<Address> addresses = geoCoder.getFromLocationName(residentCity, 5);
            if (addresses.size() > 0) {

                for (int i = 0; i < addresses.size(); i++) {
                    country = addresses.get(i).getCountryName();
                    lat = addresses.get(i).getLatitude() + "";
                    lng = addresses.get(i).getLongitude() + "";
                    Log.e("loc : ", lat + " " + lng + country);
                }
            }
        } catch (IOException e) {
            GlobalFunctions.hideProgressDialog(kProgressHUD);
            Log.e("Error ", e.getMessage());
        }
        if (etName.getText().toString().length() == 0) {
            GlobalFunctions.shakeanimation(SignupActivity.this, etName);
            etName.setError("Please enter name.");
            GlobalFunctions.hideProgressDialog(kProgressHUD);
        } else if (etPhone.getText().toString().trim().length() < 10) {
            GlobalFunctions.shakeanimation(SignupActivity.this, etPhone);
            etPhone.setError("Please enter phone.");
            GlobalFunctions.hideProgressDialog(kProgressHUD);
        } else if (residentCity.length() == 0) {
            GlobalFunctions.shakeanimation(SignupActivity.this, etResidentCity);
            etResidentCity.setError("Please enter city.");
            GlobalFunctions.hideProgressDialog(kProgressHUD);
        } else if (lat.equalsIgnoreCase("") || lng.equalsIgnoreCase("") || country.equalsIgnoreCase("")) {
            GlobalFunctions.showAlert(SignupActivity.this, "Error", "Please enter valid city name.");
            GlobalFunctions.hideProgressDialog(kProgressHUD);
        } else {
            registerUser(lat, lng, country);
        }
    }

    public void registerUser(String lat, String lng, String country) {

        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", etName.getText().toString().trim())
                .addFormDataPart("phone", etPhone.getText().toString().trim())
                .addFormDataPart("lat", lat)
                .addFormDataPart("long", lng)
                .addFormDataPart("country", country)
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
                            Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
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

    public String getLocation(String location, int type) {
        String loc = "";
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(location, 5);
            if (addresses.size() > 0) {

                for (int i = 0; i < addresses.size(); i++) {
                    if (type == 1) {
                        loc = addresses.get(i).getCountryName();
                    } else if (type == 2) {
                        loc = addresses.get(i).getLatitude() + "";
                    } else if (type == 3) {
                        loc = addresses.get(i).getLongitude() + "";
                    }
                }
            }
        } catch (IOException e) {
            Log.e("Error ", e.getMessage());
        }
        Log.e("location: ", loc);
        return loc;
    }

    public void setCountryCodes() {
        tvCountryCode.setOnClickListener(v -> showDialog());
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;
        String country = "IN";
        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
        tvCountryCode.setText(flag + " 91");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}