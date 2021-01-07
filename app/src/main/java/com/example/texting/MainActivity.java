package com.example.texting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.texting.adapter.MainAdapter;
import com.example.texting.db.dbManager;
import com.example.texting.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private dbManager dbManager;
    private EditText edTitle, edDisc, edDate;
    private RecyclerView rv;
    private MainAdapter mAdapter;
    private SharedPreferences pref;

    //    edDate.setBackgroundColor(getResources().getColor(R.color.first_bcg));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ActionBar aBar = getSupportActionBar();
        if (aBar != null) aBar.setDisplayOptions(aBar.getDisplayOptions());
    }

    private void init() {
        dbManager = new dbManager(this);
        edTitle = findViewById(R.id.edTitle);
        edDate = findViewById(R.id.edDate);
        edDisc = findViewById(R.id.edDisc);
        rv = findViewById(R.id.rcView);
        mAdapter = new MainAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);

        new ItemTouchHelper(itemTHCallback).attachToRecyclerView(rv);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        ConstraintLayout cL; //Меняем цвет у ConstraintLayout
        cL = findViewById(R.id.constL);
        FloatingActionButton abtn; //Меняем цвет floating btn
        abtn = findViewById(R.id.floatingActionButton);
        if (pref.getBoolean("def_colors", false)) {
            int color1 = pref.getInt("color_picker1", Color.GREEN);
            //    int color1 = pref.getInt("color_picker1", Color.parseColor("@color/first_bcg"));
            int color2 = pref.getInt("color_picker2", Color.WHITE);
            //   int color2 = pref.getInt("color_picker2", Color.parseColor("@color/second_bcg"));
            cL.setBackgroundColor(color1);
            abtn.setBackgroundTintList(ColorStateList.valueOf(color1));
        }
        else {
            int color = getResources().getColor(R.color.first_bcg);
            cL.setBackgroundColor(color);
            abtn.setBackgroundTintList(ColorStateList.valueOf(color));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Открываем и обновляем бд
        dbManager.openDb();
        mAdapter.updateAdapter(dbManager.readFromDb());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //добавляем "3 точки" в actionbar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            }

            case R.id.action_about: {
                int f;
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickAdd(View view) {
        Intent i = new Intent(MainActivity.this, EditActivity.class);
        startActivity(i);
    }

    //Свайп дял удаления
    final ItemTouchHelper.SimpleCallback itemTHCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.delItem(viewHolder.getAdapterPosition(), dbManager);
            //mAdapter.updateAdapter(dbManager.deleteFromDb(viewHolder.getAdapterPosition()));
            //mAdapter.updateAdapter(dbManager.deleteFromDb(dbManager.readFromDb(), viewHolder.getAdapterPosition()));
        }
    };

    public void onClickRView(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

}








