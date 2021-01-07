package com.example.texting.db;


import android.content.Context;
import java.io.Serializable;

//Класс для "хранения" ячейки с mainActivity
//Хранит название, дату и содержание
public class containItem implements Serializable {
    private Context context;
    private int id;
    private String titleList, dateList, discList;

    public String getTitleList() {
        return titleList;
    }
    public String getDateList() {
        return dateList;
    }
    public String getDiscList() {
        return discList;
    }
    public int getID() { return id; }

    public containItem(int id, String titleList) {
        this.id = id;
        this.titleList = titleList;
    }

    public containItem(int id, String titleList, String dateList, String discList) {
        this.id = id;
        this.titleList = titleList;
        this.dateList = dateList;
        this.discList = discList;
    }


}
