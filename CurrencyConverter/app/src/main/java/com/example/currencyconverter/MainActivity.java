package com.example.currencyconverter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner fromCurrency, toCurrency;
    EditText amount;
    TextView result;
    Button converterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void convertCurrency() {

        if (amount.getText().toString().isEmpty()) {
            result.setText(getString(R.string.result_format));
            return;
        }

        double amt = Double.parseDouble(amount.getText().toString());

        String from = fromCurrency.getSelectedItem().toString();
        String to = toCurrency.getSelectedItem().toString();

        double rate = getRate(from, to);
        double converted = amt * rate;

        result.setText(getString(R.string.result_format,converted);
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

}