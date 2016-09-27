package com.example.mathias.todo.database;

import android.provider.BaseColumns;


public class Schema {
    public static final String DB_NAME = "com.example.mathias.todo.database";
    public static final int DB_VERSION = 1;

    public class TaskTable implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_COMPLETE = "complete";
    }
}
