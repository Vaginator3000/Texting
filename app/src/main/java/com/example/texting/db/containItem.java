package com.example.texting.db;


import android.content.Context;

//Класс для "хранения" ячейки с mainActivity
//Хранит текст названия и дату
public class containItem {
    private Context context;
    private String titleList, dateList;

    public String getTitleList() {
        return titleList;
    }

    public String getDateList() {
        return dateList;
    }

    public containItem(String titleList) {
        this.titleList = titleList;
    }

    public containItem(String titleList, String dateList) {
        this.titleList = titleList;
        this.dateList = dateList;
    }
}
