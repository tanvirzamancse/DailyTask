package com.codescafe.dailytask.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codescafe.dailytask.R;
import com.codescafe.dailytask.Util.TaskModel;
import com.codescafe.dailytask.Util.TaskView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.ViewHolder> {
    Context context;
    ArrayList<TaskModel> list;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public AdapterTask(Context context, ArrayList<TaskModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdapterTask.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterTask.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaskModel taskModel = list.get(position);
        if (list.size()>0){
            holder.date.setText(""+taskModel.getDate());
            holder.LocationName.setText(""+taskModel.getTitle());
            Glide.with(context).load(taskModel.getPicture()).into(holder.img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TaskView.class);
                    intent.putExtra("model",taskModel);
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure you wanna delete...?");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Intent intent = new Intent(context, EditRequest.class);
                            //intent.putExtra("model",requestModel);
                            //context.startActivity(intent);
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @SuppressLint("CommitPrefEdits")
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            list.remove(position);
                            notifyItemRemoved(position);
                            Gson gson = new Gson();
                            context.getSharedPreferences("tasks",Context.MODE_PRIVATE).edit().putString("task",gson.toJson(list)).apply();
                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView LocationName,date,status;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            LocationName = itemView.findViewById(R.id.LocationName);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
        }
    }
}
