package com.example.texting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.texting.db.dbManager;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText edTitle, edDate, edDisc;
    private dbManager dbManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    private void init() {
        edTitle = findViewById(R.id.edTitle);
        edDate = findViewById(R.id.edDate);
        edDisc = findViewById(R.id.edDisc);
        dbManager = new dbManager(this);
    }

    public void onClickSave(View view) {
        String title = edTitle.getText().toString();
        String date = edDate.getText().toString();
        String disc = edDisc.getText().toString();
        if (disc.equals("")) {
            Toast.makeText(this, R.string.txt_empty, Toast.LENGTH_SHORT).show();
        } else {
            dbManager.insertToDb(title, date, disc);
            finish();
            dbManager.closeDb();
        }
    }

    public void onClickCal(View view) {
        showDatePickerDialog();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "." + (month + 1) + "." + year;
        edDate.setText(date);
    }

    public void onClickDate(View view) {
        Toast.makeText(this, R.string.click_date, Toast.LENGTH_SHORT).show();
    }
}