package com.codescafe.dailytask.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.codescafe.dailytask.Adapter.AdapterTask;
import com.codescafe.dailytask.Auth.SignInActivity;
import com.codescafe.dailytask.R;
import com.codescafe.dailytask.Util.TaskModel;
import com.codescafe.dailytask.Util.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNote;
    Activity activity;
    RecyclerView rvNotes,rvline2;
    CardView open1,open2,open3,open4,open5,open6;
    ImageView search,logout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getpermission();

        search = findViewById(R.id.search);
        open1 = findViewById(R.id.open1);
        open2 = findViewById(R.id.open2);
        open3 = findViewById(R.id.open3);
        open4 = findViewById(R.id.open4);
        open5 = findViewById(R.id.open5);
        open6 = findViewById(R.id.open6);
        logout = findViewById(R.id.logout);

        open1.setOnClickListener(view -> {
            Intent intent = new  Intent(getApplicationContext(),CreateTask.class);
            startActivity(intent);
        });
        open2.setOnClickListener(view -> {
            Intent intent = new  Intent(getApplicationContext(),CreateTask2.class);
            intent.putExtra("val","2");
            startActivity(intent);
        });
        open3.setOnClickListener(view -> {
            Intent intent = new  Intent(getApplicationContext(),CreateTask3.class);
            intent.putExtra("val","3");
            startActivity(intent);
        });
        open4.setOnClickListener(view -> {
            Intent intent = new  Intent(getApplicationContext(),CreateTask4.class);
            intent.putExtra("val","4");
            startActivity(intent);
        });
        open5.setOnClickListener(view -> {
            Intent intent = new  Intent(getApplicationContext(),CreateTask5.class);
            intent.putExtra("val","5");
            startActivity(intent);
        });
        open6.setOnClickListener(view -> {
            Intent intent = new  Intent(getApplicationContext(),CreateTask6.class);
            intent.putExtra("val","6");
            startActivity(intent);
        });

        //logout

        logout.setOnClickListener(view -> {
            auth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        });

        addNote = findViewById(R.id.addNote);
        //addNote.setOnClickListener(view -> startActivity(new Intent(activity,CreateTask.class)));
        fetchVal();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Tasks.class));

            }
        });
    }
    ArrayList<TaskModel> list = new ArrayList<>();
    AdapterTask adapterTask;
    private void fetchVal() {
        SharedPreferences prefs ;
        //= PreferenceManager.getDefaultSharedPreferences(StepActivity.this);

        prefs = getSharedPreferences("tasks",MODE_PRIVATE);
        Gson gson1 = new Gson();
        String json1 = prefs.getString("task", "");
        if (!(json1.equals(""))){
            list.clear();
            list = gson1.fromJson(json1, new TypeToken<ArrayList<TaskModel>>() {}.getType());
        }
        Log.e("i'm here","false"+list.size());
        try {
            if (list.size()>0){
                Log.e("i'm here","true"+list.size());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    adapterTask = new AdapterTask(MainActivity.this, list);
                }
              //  rvNotes.setAdapter(adapterTask);
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchVal();
    }
    public void getpermission(){

        String exread = Manifest.permission.READ_EXTERNAL_STORAGE;
        String exwrite = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String call_phone = Manifest.permission.CALL_PHONE;
        String record = Manifest.permission.RECORD_AUDIO;
        String camera = Manifest.permission.CAMERA;

        if (ContextCompat.checkSelfPermission(this, exread) !=
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                exwrite) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, record) !=
                        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                call_phone) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{camera, exread, exwrite, record, call_phone}, 200);
            }
        }

    }


}