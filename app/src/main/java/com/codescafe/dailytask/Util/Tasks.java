package com.codescafe.dailytask.Util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.codescafe.dailytask.Adapter.AdapterTask;
import com.codescafe.dailytask.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Tasks extends AppCompatActivity {

    RecyclerView rvTasks;
    AdapterTask adapterTask;
    ArrayList<TaskModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        rvTasks = findViewById(R.id.rvTasks);

        getTasks();
    }

    private void getTasks() {
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
                    adapterTask = new AdapterTask(Tasks.this, list);
                }
                rvTasks.setAdapter(adapterTask);
            }
        }catch (Exception e){

        }
    }
}