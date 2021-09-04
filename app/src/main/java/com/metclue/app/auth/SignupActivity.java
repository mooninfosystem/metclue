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

import com.metclue.app.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    TextView tvCountryCode;
    String selectedCountryCode = "91";
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvCountryCode = findViewById(R.id.tvCountryCode);
        findViewById(R.id.ivBack).setOnClickListener(v -> onBackPressed());
        setCountryCodes();

        findViewById(R.id.btnSignup).setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

//            EditText etResidentCity = findViewById(R.id.etResidentCity);
//            String value = etResidentCity.getText().toString();
//            Log.e("value", value);
//            Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
//            try {
//                List<Address> addresses = geoCoder.getFromLocationName(
//                        value, 5);
//                if (addresses.size() > 0) {
//                    for (int i = 0;i<addresses.size();i++){
//                        Log.e("location ",addresses.get(i).getLocality().toString());
//                    }
//                }
//            } catch (IOException e) {
//                Log.e("Error ",e.getMessage());
//            }


        });








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