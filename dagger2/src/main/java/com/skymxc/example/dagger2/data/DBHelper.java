package com.skymxc.example.dagger2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NAME = "dagger.db";
    private static final int VERSION = 1;


    @Inject
    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
        Log.e(DBHelper.class.getSimpleName(), "DBHelper: construct");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(DBHelper.class.getSimpleName(), "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
