package com.piyushjagtap.www.localdatabaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.piyushjagtap.www.localdatabaseproject.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private DatabaseHelper db;
    Button dataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        db.getWritableDatabase();
//        db.close();
        dataButton = findViewById(R.id.data_button);
        dataButton.setOnClickListener(this);
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
        }
    }
}
