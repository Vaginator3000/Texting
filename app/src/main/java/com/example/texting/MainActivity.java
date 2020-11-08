package com.example.texting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.texting.adapter.MainAdapter;
import com.example.texting.db.dbManager;

public class MainActivity extends AppCompatActivity {
    private dbManager dbManager;
    private EditText edTitle, edDisc, edDate;
    private RecyclerView rv;
    private MainAdapter mAdapter;

    //    edDate.setBackgroundColor(getResources().getColor(R.color.first_bcg));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Открываем и обновляем бд
        dbManager.openDb();
        mAdapter.updateAdapter(dbManager.readFromDb());
    }

    public void onClickAdd(View view) {
        Intent i = new Intent(MainActivity.this, EditActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    public void onClickRView(View view) {
    }
}