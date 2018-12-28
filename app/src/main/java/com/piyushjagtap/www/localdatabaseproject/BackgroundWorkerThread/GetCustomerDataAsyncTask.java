package com.piyushjagtap.www.localdatabaseproject.BackgroundWorkerThread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.piyushjagtap.www.localdatabaseproject.Database.DatabaseHelper;
import com.piyushjagtap.www.localdatabaseproject.MainActivity;
import com.piyushjagtap.www.localdatabaseproject.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class GetCustomerDataAsyncTask extends AsyncTask<Void, Void, ArrayList<NoteModel>> {

    private static String TAG = "GetCustomerDataAsyncTask";
    private DatabaseHelper db;
    ArrayList<NoteModel> noteModelList;
    private Context context;
    private MainActivity listener;


    public GetCustomerDataAsyncTask(Context c){
        this.context = c;
    }

    @Override
    protected ArrayList<NoteModel> doInBackground(Void... voids) {
        db = new DatabaseHelper(this.context);
        noteModelList = db.getAllNotes();
        return noteModelList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");

    }

    @Override
    protected void onPostExecute(ArrayList<NoteModel> noteModels) {
        super.onPostExecute(noteModels);
        Log.d(TAG, "onPostExecute: " + noteModelList.size());
        if (listener != null)
            listener.getCustomerList(noteModels);
    }

    public void setNoteCallBackListener(MainActivity mainActivity) {
        this.listener = mainActivity;
    }

    //    @Override
//    protected String doInBackground(String... params) {
//        Log.d(TAG, "ondoInBackground: " +params[0]);
//        //publishProgress("Async Task");
//        db = new DatabaseHelper(this.context);
//        db.getAllNotes();
//        return null;
//    }



//    @Override
//    protected void onProgressUpdate(String... values) {
//        super.onProgressUpdate(values);
//        //MainActivity.helloWorld.setText("Async Test");
//        Log.d(TAG, "onProgressUpdate: " + values[0]);
//    }
}
