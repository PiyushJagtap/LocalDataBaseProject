package com.piyushjagtap.www.localdatabaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.piyushjagtap.www.localdatabaseproject.BackgroundWorkerThread.GetCustomerDataAsyncTask;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, NoteCallBackListener {

    private static String TAG = "Main2Activity";
    Button activityTwoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activityTwoButton = findViewById(R.id.activity2_button);
        activityTwoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        GetCustomerDataAsyncTask getCustomerDataAsyncTask = new GetCustomerDataAsyncTask(this);
        getCustomerDataAsyncTask.setNoteCallBackListener(this);
        getCustomerDataAsyncTask.execute();
    }

    @Override
    public void getCustomerList(ArrayList<NoteModel> noteModelArrayList) {
        Log.d(TAG, "getCustomerList: " + noteModelArrayList.size());
    }
}
