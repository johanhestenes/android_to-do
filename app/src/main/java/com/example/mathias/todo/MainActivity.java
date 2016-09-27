package com.example.mathias.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mathias.todo.database.DbHelper;
import com.example.mathias.todo.database.Schema;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DbHelper mDbHelper;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DbHelper(this);
        mListView = (ListView) findViewById(R.id.list_todo);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_new_task:
                Intent intent = new Intent(this, SecondActivity.class);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> tasks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(Schema.TaskTable.TABLE, new String[]{Schema.TaskTable._ID, Schema.TaskTable.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(Schema.TaskTable.COL_TASK_TITLE);
            tasks.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list_task, tasks);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(tasks);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.list_task);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(Schema.TaskTable.TABLE, Schema.TaskTable.COL_TASK_TITLE + " = ?", new String[]{task});
        db.close();
        updateUI();
    }

    // unused in current version
    public void addCheck(View view) {
        CheckBox checkBox = (CheckBox)view;
        if (checkBox.isChecked()){
            View parent = (View) view.getParent();
            TextView taskTextView = (TextView) parent.findViewById(R.id.list_task);
            String task = String.valueOf(taskTextView.getText());

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            String strSQL = "UPDATE Tasks SET complete = 1 WHERE title = " + "'" + task + "';";

            db.execSQL(strSQL);
            db.close();

            updateUI();
        }
        else
        {
            View parent = (View) view.getParent();
            TextView taskTextView = (TextView) parent.findViewById(R.id.list_task);
            String col_id = String.valueOf(taskTextView.getText());

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            String strSQL = "UPDATE Tasks SET complete = 0 WHERE title = " + "'" + col_id + "';";

            db.execSQL(strSQL);
            db.close();

            updateUI();
        }
    }
}
