package com.piyushjagtap.www.localdatabaseproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.piyushjagtap.www.localdatabaseproject.BackgroundWorkerThread.GetCustomerDataAsyncTask;
import com.piyushjagtap.www.localdatabaseproject.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , NoteCallBackListener {

    private static String TAG = "MainActivity";
    private DatabaseHelper db;
    Button dataButton;
    Button getDataButton;
    Button updateDataButton;
    Button deleteDataButton;
    Button asyncButton;
    Button cameraPermission;
    public static TextView helloWorld;
    List<NoteModel> getNoteAL;
    private final int CAMERA_PERMISSION_INT = 24;
    private final int IMAGE_CAPTURE_REQUEST_CODE = 25;
    ImageView imageView;

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
        cameraPermission = findViewById(R.id.camera_permission);
        cameraPermission.setOnClickListener(this);

        imageView = findViewById(R.id.image_view);

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
//                GetCustomerDataAsyncTask getCustomerDataAsyncTask1 = new GetCustomerDataAsyncTask(this);
//                getCustomerDataAsyncTask1.execute();
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
                GetCustomerDataAsyncTask getCustomerDataAsyncTask = new GetCustomerDataAsyncTask(this);
                getCustomerDataAsyncTask.setNoteCallBackListener(this);
                getCustomerDataAsyncTask.execute();
                break;

            case R.id.camera_permission:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                    //Permission is not granted
                    Log.d(TAG, "Camera Permission not granted: ");
                    requestCameraPermission();
                }
                else {
                    Log.d(TAG, "Camera Permission granted: ");
                    Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoIntent,IMAGE_CAPTURE_REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    public void getCustomerList(ArrayList<NoteModel> noteModelArrayList) {
        Log.d(TAG, "getCustomerList: " + noteModelArrayList.size());
    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            AlertDialog.Builder showExplanationBuilder = new AlertDialog.Builder(this);
            showExplanationBuilder.setTitle(getString(R.string.app_name));
            showExplanationBuilder.setMessage("Camera Permission");
            showExplanationBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            showExplanationBuilder.show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_INT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_INT:
                //if user has canceled the permission the result array is empty
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoIntent,IMAGE_CAPTURE_REQUEST_CODE);
                    Toast.makeText(this,"Permission Granted.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "Permission Denied: ");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Request code: " + requestCode);
        Log.d(TAG, "Result code: "+resultCode);
        Log.d(TAG, "Data: "+data);
        switch (requestCode){
            case IMAGE_CAPTURE_REQUEST_CODE:
                switch (resultCode){
                    case RESULT_OK:
                        Log.d(TAG, "Result Ok: " + resultCode);
                        if (data != null){
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            imageView.setImageBitmap(bitmap);
                        }
                        else {
                            Log.d(TAG, "Data is null: ");
                        }
                        break;
                    case RESULT_CANCELED:
                        Log.d(TAG, "Result Cancelled : " + resultCode);
                        break;
                        default:
                            Log.d(TAG, "Final Result: "+resultCode);
                            break;
                }
                break;
        }
    }
}
