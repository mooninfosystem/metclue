package com.metclue.app.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.metclue.app.R;

public class LoginActivity extends AppCompatActivity {

    Spinner spinnerCountries;
    TextView tvCountryCode;
    Dialog myDialog;
    String selectedCountryCode = "91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvCountryCode = findViewById(R.id.tvCountryCode);

        tvCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private void showDialog() {
        myDialog = new Dialog(this);
        myDialog.setCancelable(true);

        myDialog.setContentView(R.layout.choose_country_dialog);

        RecyclerView rvframeCategories;
        Button tv_ok;
        TextView tv_sorting_cat_heading;
        ListView listView;

        myDialog.show();


        String[] recourseList = this.getResources().getStringArray(R.array.CountryCodes);

//        final ListView listview = (ListView) mfindViewById(R.id.listView);
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