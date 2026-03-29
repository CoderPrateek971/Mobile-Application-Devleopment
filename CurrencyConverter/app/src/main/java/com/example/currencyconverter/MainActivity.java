package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner fromCurrency, toCurrency;
    EditText amount;
    TextView result;
    Button converterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("DarkMode", false);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        amount = findViewById(R.id.amount);
        result = findViewById(R.id.result);
        converterBtn = findViewById(R.id.convertBtn);

        // Use string-array from XML
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.currencies,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        converterBtn.setOnClickListener(v -> convertCurrency());
    }

    private void convertCurrency() {

        if (amount.getText().toString().isEmpty()) {
            amount.setError("Enter amount");
            result.setText("");
            return;

        }

        double amt = Double.parseDouble(amount.getText().toString());

        String from = fromCurrency.getSelectedItem().toString();
        String to = toCurrency.getSelectedItem().toString();

        double rate = getRate(from, to);
        double converted = amt * rate;

        result.setText(getString(R.string.result_format,converted));
    }
    private double getRate(String from, String to) {

        double inr;

        switch (from) {
            case "USD": inr = 94.86; break;
            case "EUR": inr = 109.01; break;
            case "JPY": inr = 0.59; break;
            default: inr = 1;
        }

        double target;

        switch (to) {
            case "USD": target = 1 / 94.86; break;
            case "EUR": target = 1 / 109.01; break;
            case "JPY": target = 1 / 0.59; break;
            default: target = 1;
        }

        return inr * target;
    }

    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

}