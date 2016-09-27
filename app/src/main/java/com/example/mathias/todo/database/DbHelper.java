package com.example.mathias.todo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Schema.DB_NAME, null, Schema.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Schema.TaskTable.TABLE + " ( " +
                Schema.TaskTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Schema.TaskTable.COL_TASK_TITLE + " TEXT, " +
                Schema.TaskTable.COL_COMPLETE + " INTEGER DEFAULT 0);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Schema.TaskTable.TABLE);
        onCreate(db);
    }
}
