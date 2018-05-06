package com.example.purwin.napiwkomat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    //GUI - pierwszy wiersz

    private EditText amountEditText;
    private TextView amountTextView;

    //GUI - drugi wiersz

    private TextView percentTextView;
    private SeekBar percentSeekBar;

    //GUI - trzeci wiersz

    private TextView tipLabelTextView;
    private TextView tipTextView;

    //GUI - czwart wiersz

    private TextView totalLabelTextView;
    private TextView totalTextView;


    // Statyczne obiekty do formatowania wartosci walutowych i procentowych

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    //Kwota rachunku podana przez użytkownika
    private double billAmount = 0.0;

    //Procent napiwku
    private double tipPercent = 0.15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicjalizacja pól widoków GUI
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountTextView = (TextView) findViewById(R.id.amountTextView);

        percentTextView = (TextView) findViewById(R.id.percentTextView);
        percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);

        tipLabelTextView = (TextView) findViewById(R.id.tipLabelTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);

        totalLabelTextView = (TextView) findViewById(R.id.totalLabelTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        //Nasłuchiwanie zdarzen dla pola EditText

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    billAmount = Double.parseDouble(s.toString()) / 100.0;
                    amountTextView.setText(currencyFormat.format(billAmount));
                } catch (NumberFormatException ex) {
                    amountTextView.setText("");
                    billAmount = 0.0;
                }
                 calculateTipAndTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Nasłuchiwanie zdarzen dla polsa SeekBar
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercent = progress / 100.0;
                calculateTipAndTotalAmount();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void calculateTipAndTotalAmount() {

        double tipAmount = billAmount * tipPercent;
        double totalAmount = billAmount + tipAmount;

        percentTextView.setText(percentFormat.format(tipPercent));
        tipTextView.setText(currencyFormat.format(tipAmount));
        totalTextView.setText(currencyFormat.format(totalAmount));

    }
}
