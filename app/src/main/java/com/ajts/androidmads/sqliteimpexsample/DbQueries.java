package com.ajts.androidmads.sqliteimpexsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter;

import java.util.ArrayList;

class DbQueries {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteImporterExporter dbHelper;

    DbQueries(Context context) {
        this.context = context;
    }

    public DbQueries open() throws SQLException {
        dbHelper = new SQLiteImporterExporter(context, MainActivity.db);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    void close() {
        dbHelper.close();
    }

    long insertDetail(String stud_name) {
        ContentValues values = new ContentValues();
        values.put("student_name", stud_name);
        return database.insert("student_details", null, values);
    }

    ArrayList<String> getDetail() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Cursor cursor;
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery("SELECT * FROM student_details", null);
            list.clear();
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(cursor.getString(cursor.getColumnIndex("student_name")));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v("Exception", e.toString());
        }
        return list;
    }
}
