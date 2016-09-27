package com.example.mathias.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mathias.todo.database.DbHelper;
import com.example.mathias.todo.database.Schema;

public class SecondActivity extends AppCompatActivity {
    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_list);

        mDbHelper = new DbHelper(this);

        Button confirm = (Button) findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText txtDescription =
                        (EditText) findViewById(R.id.task_title);
                String task = txtDescription.getText().toString();

                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Schema.TaskTable.COL_TASK_TITLE, task);
                db.insertWithOnConflict(Schema.TaskTable.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                db.close();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}
