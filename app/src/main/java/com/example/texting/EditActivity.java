package com.example.texting;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.texting.alarmManager.AlarmReceiver;
import com.example.texting.db.MyConstants;
import com.example.texting.db.containItem;
import com.example.texting.db.dbManager;
import com.example.texting.notification.NotificationHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText edTitle, edDate, edDisc;
    private dbManager dbManager;
    private Context context;
    private boolean isEditState = true;
    private SharedPreferences pref;

   // private AlarmReceiver alarm;

    private String date_for_notification = ""; //Сохранить дату в другом формате для уведомлений

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntents();
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

        setPref();
    }

    public void setPref() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        //Размер шрифта
        String font = pref.getString("font_size", "Средний");
        switch (font) {
            case "Мелкий": {
                edDisc.setTextSize(14);
                edTitle.setTextSize(14);
                //    edDate.setTextSize(14);
                break;
            }
            case "Средний": {
                edDisc.setTextSize(18);
                edTitle.setTextSize(18);
                //    edDate.setTextSize(18);
                break;
            }
            case "Крупный": {
                edDisc.setTextSize(24);
                edTitle.setTextSize(24);
                //   edDate.setTextSize(24);
                break;
            }
        }

        //Цвет текста
        int color = pref.getInt("color_picker2", Color.BLACK);
        edDisc.setTextColor(color);
        edDate.setTextColor(color);
        edTitle.setTextColor(color);

        //Цвет элементов
        ConstraintLayout  cL; //Меняем цвет у ConstraintLayout
        cL = findViewById(R.id.constL);
        ImageButton ib1, ib2; //Меняем цвет у кнопок
        ib1 = findViewById(R.id.imageButton);
        ib2 = findViewById(R.id.Clear_btn);
        FloatingActionButton abtn; //Меняем цвет floating btn
        abtn = findViewById(R.id.floatingActionButton2);
        if (pref.getBoolean("def_colors", false)) {
            int color1 = pref.getInt("color_picker1", Color.GREEN);
            //    int color1 = pref.getInt("color_picker1", Color.parseColor("@color/first_bcg"));
            int color2 = pref.getInt("color_picker2", Color.WHITE);
            //   int color2 = pref.getInt("color_picker2", Color.parseColor("@color/second_bcg"));
            cL.setBackgroundColor(color1);
            ib2.setBackgroundColor(color1);
            ib1.setBackgroundColor(color1);
            abtn.setBackgroundTintList(ColorStateList.valueOf(color1));
        }
        else {
            color = getResources().getColor(R.color.first_bcg);
            cL.setBackgroundColor(color);
            ib2.setBackgroundColor(color);
            ib1.setBackgroundColor(color);
            abtn.setBackgroundTintList(ColorStateList.valueOf(color));
        }
    }

    //Получаем дату в другом формате для работы уведомлений
    public String dateFormat(String date) {
        String newDate = "";
        String[] dates;
        String delimeter = "\\."; // Разделитель
        if (!date.equals("")) {
            dates = date.split(delimeter); // Разделения строки str с помощью метода split()
            newDate = dates[2] + "-" + dates[1] + "-" + dates[0];
        }
        return newDate;
    }

    //принимаем интент для открытия выбранной заметки
    private void getMyIntents() {
        Intent i = getIntent();

        if (i != null) {
            containItem item = (containItem)i.getSerializableExtra(MyConstants.CONTAIN_ITEM_INTENT);
            isEditState = i.getBooleanExtra(MyConstants.EDIT_STATE, true);

            if(!isEditState) {
                edTitle.setText(item.getTitleList());
                edDate.setText(item.getDateList());
                edDisc.setText(item.getDiscList());

                date_for_notification = dateFormat(edDate.getText().toString());
            }
        }

    }

    public void onClickSave(View view) {
        //устанавливаем уведомление
        try {
            if (!(date_for_notification.equals(""))) {
                Calendar c = Calendar.getInstance();
                c.setTime(Date.valueOf(date_for_notification));
                startAlarm(c);
            }
        } catch (Exception e) {
            Log.d("MyLog", "Error - " + e.toString());
            Log.d("MyLog", "date_for_notification - " + date_for_notification);
        }

        //Вносим данные в бд
        String title = edTitle.getText().toString();
        String date = edDate.getText().toString();
        String disc = edDisc.getText().toString();
        if (disc.equals("")) {
            Toast.makeText(this, R.string.txt_empty, Toast.LENGTH_SHORT).show();
        } else {
            dbManager.insertToDb(title, date, disc);
            finish();
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
        date_for_notification = year + "-" + (month + 1) + "-" + dayOfMonth; //дата в другом формате для передачи значения
        edDate.setText(date);
    }

    public void onClickDate(View view) {
        Toast.makeText(this, R.string.click_date, Toast.LENGTH_SHORT).show();
    }

    public void onClickClear(View view) {
        edDate.setText("");
        cancelAlarm();
    }

    private void cancelAlarm() {
        AlarmManager aManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, i, 0);

        aManager.cancel(pIntent);
    }

    private void startAlarm(Calendar c) {
        AlarmManager aManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("title", edTitle.getText().toString());
        i.putExtra("text", edDisc.getText().toString());
        i.putExtra("id", NotificationHelper.count);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, NotificationHelper.count, i, PendingIntent.FLAG_UPDATE_CURRENT);

        aManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);
    }

    @Override
    protected void onDestroy() {
        Log.d("MyLog", "On destroy");
        dbManager.closeDb();
        super.onDestroy();
    }
}