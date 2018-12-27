package com.piyushjagtap.www.localdatabaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.piyushjagtap.www.localdatabaseproject.BackgroundWorkerThread.GetCustomerDataAsyncTask;
import com.piyushjagtap.www.localdatabaseproject.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private DatabaseHelper db;
    Button dataButton;
    Button getDataButton;
    Button updateDataButton;
    Button deleteDataButton;
    Button asyncButton;
    public static TextView helloWorld;
    List<NoteModel> getNoteAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorld = findViewById(R.id.hello_world);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();
//        db.close();
        dataButton = findViewById(R.id.data_button);
        dataButton.setOnClickListener(this);
        getDataButton = findViewById(R.id.get_data_button);
        getDataButton.setOnClickListener(this);
        updateDataButton = findViewById(R.id.update_data_button);
        updateDataButton.setOnClickListener(this);
        deleteDataButton = findViewById(R.id.delete_data_button);
        deleteDataButton.setOnClickListener(this);
        asyncButton = findViewById(R.id.async_button);
        asyncButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_button:
                String note = "My First Note";
                if (db == null)
                    db = new DatabaseHelper(this);

                long noteid = db.insertNote(note);
                Log.d(TAG, "Note id : " + noteid);
                break;

            case R.id.get_data_button:
                if (db == null)
                    db = new DatabaseHelper(this);
                getNoteAL = db.getAllNotes();
                Log.d(TAG, "ArrayList Size : " + getNoteAL.size());
                break;

            //Case of button to update from table
            case R.id.update_data_button:
                if (db == null)
                    db = new DatabaseHelper(this);
                NoteModel noteModel = getNoteAL.get(2);
                long update = db.updateNote(noteModel);
                Log.d(TAG, "Data Updated: " + update);
                break;

                //Case of button to delete from table
            case R.id.delete_data_button:
                if (db == null)
                    db = new DatabaseHelper(this);
                NoteModel noteModel1 = getNoteAL.get(2);
                long delete = db.deleteNote(noteModel1);
                Log.d(TAG, "Data Delete : " + delete);
                break;

                //Case of button for Async Task
            case R.id.async_button:
                GetCustomerDataAsyncTask getCustomerDataAsyncTask = new GetCustomerDataAsyncTask();
                getCustomerDataAsyncTask.execute("My First Async Task.");
                break;
        }
    }
}
