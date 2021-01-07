package com.example.texting.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.texting.R;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class SettingsActivity extends AppCompatActivity implements ColorPickerDialogListener {
    private ActionBar aBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getSupportActionBar()) {
            aBar = getSupportActionBar();
            aBar.setDisplayHomeAsUpEnabled(true);
            aBar.setDisplayShowHomeEnabled(true);
            aBar.setTitle(getString(R.string.settings));
        }

        //Используем Preference Screen
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
     //   findViewById(android.R.id.list).setBackgroundColor(Color.BLUE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    public void onColorSelected(int dialogId, int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

}


















