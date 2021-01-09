package com.example.texting.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.texting.MainActivity;
import com.example.texting.R;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class SettingsActivity extends AppCompatActivity implements ColorPickerDialogListener {
    private ActionBar aBar;
    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aBar = getSupportActionBar();
        if (null != aBar) {
            aBar.setDisplayHomeAsUpEnabled(true);
            aBar.setDisplayShowHomeEnabled(true);
            aBar.setTitle(getString(R.string.settings));
        }

        setColors();
        //Используем Preference Screen
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
     //   findViewById(android.R.id.list).setBackgroundColor(Color.BLUE);
    }

    public void setColors() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        //Цвет actionBar
        if (pref.getBoolean("def_colors", false)) {
            int color1 = pref.getInt("color_picker1", Color.GREEN);
            int color2 = pref.getInt("color_picker2", Color.WHITE);

            if (aBar != null) aBar.setBackgroundDrawable(new ColorDrawable(color1));
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
     //   if (aBar != null) aBar.setBackgroundDrawable(new ColorDrawable(color));
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

    @Override
    protected void onDestroy() {
        try {
        //    super.recreate();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } catch (Exception e) {
            Log.d("MyLog", e.toString());
        }
        super.onDestroy();
    }
}


















