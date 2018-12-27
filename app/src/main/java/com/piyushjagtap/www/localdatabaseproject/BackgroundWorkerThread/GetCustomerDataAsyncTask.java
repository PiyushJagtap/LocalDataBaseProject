package com.piyushjagtap.www.localdatabaseproject.BackgroundWorkerThread;

import android.os.AsyncTask;
import android.util.Log;

import com.piyushjagtap.www.localdatabaseproject.MainActivity;

public class GetCustomerDataAsyncTask extends AsyncTask<String, String, String> {

    private static String TAG = "GetCustomerDataAsyncTask";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");

    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "ondoInBackground: " +params[0]);
        publishProgress("Async Task");
        return "background";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecute: " + result);

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        //MainActivity.helloWorld.setText("Async Test");
        Log.d(TAG, "onProgressUpdate: " + values[0]);
    }
}
