package com.codescafe.dailytask.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codescafe.dailytask.Model.ColorSet;
import com.codescafe.dailytask.R;
import com.codescafe.dailytask.UI.DataSource;

import java.io.IOException;
import java.util.List;

public class TaskView extends AppCompatActivity {

    private final Handler handler=new Handler();
    Boolean isPlaying=false;
    MediaPlayer mediaPlayer=null;
    int duration=0;
    TaskModel taskModel;

    ImageView pausePlay;
    SeekBar progressShow;
    ImageView img;
    TextView etTitle,subject,etStory;
    TextView dateSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        List<ColorSet> colorsList = DataSource.getAllColors();
        List<String> fonts = DataSource.getAllFonts(this);
        taskModel = (TaskModel) getIntent().getSerializableExtra("model");

        subject = findViewById(R.id.subject);
        etStory = findViewById(R.id.etStory);
        etTitle = findViewById(R.id.etTitle);
        dateSet = findViewById(R.id.dateSet);
        img = findViewById(R.id.img);
        pausePlay = findViewById(R.id.pausePlay);
        progressShow = findViewById(R.id.progressShow);

        etStory.setText(taskModel.getDescription());
        dateSet.setText(taskModel.getDate());
        subject.setText(taskModel.getSubject());
        etTitle.setText(taskModel.getTitle());
        Toast.makeText(this, ""+taskModel.getColor(), Toast.LENGTH_SHORT).show();
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/" +fonts.get(Integer.parseInt(taskModel.getColor())));
            etTitle.setTypeface(typeface);
            etStory.setTypeface(typeface);
            dateSet.setTypeface(typeface);
            subject.setTypeface(typeface);
            etStory.setTextColor(getResources().getColor(colorsList.get(Integer.parseInt(taskModel.getColor())).getColorResId()));
            etTitle.setTextColor(getResources().getColor(colorsList.get(Integer.parseInt(taskModel.getColor())).getColorResId()));
            subject.setTextColor(getResources().getColor(colorsList.get(Integer.parseInt(taskModel.getColor())).getColorResId()));
            dateSet.setTextColor(getResources().getColor(colorsList.get(Integer.parseInt(taskModel.getColor())).getColorResId()));

        }catch (Exception e){

        }

        Glide.with(TaskView.this).load(taskModel.getPicture()).into(img);
        //Toast.makeText(this, ""+taskModel.getVoice(), Toast.LENGTH_SHORT).show();
        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer==null){
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(taskModel.getVoice());
                        mediaPlayer.prepare();
                        duration = mediaPlayer.getDuration();
                        // Toast.makeText(context, "du"+duration, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (!mediaPlayer.isPlaying()){
                    //Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                    pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                }
                else if (mediaPlayer.isPlaying()){
                    //Toast.makeText(context, "Pause", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                    pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
                updateSeekbar(progressShow);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                       pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                       progressShow.setProgress(0);
                    }
                });
            }
        });
    }
    private void updateSeekbar(SeekBar seekbar) {
        if (mediaPlayer!=null){
            if (mediaPlayer.isPlaying()){
                seekbar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/duration)*100));
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        updateSeekbar(seekbar);
                    }
                };
                handler.postDelayed(runnable,500);
            }
        }
    }
}