package com.codescafe.dailytask.UI;

import static com.codescafe.dailytask.Util.Constants.DEFAULT;
import static com.codescafe.dailytask.Util.Constants.DEFAULT_SLIDER_VALUE;
import static com.codescafe.dailytask.Util.Constants.FONT_SIZE;
import static com.codescafe.dailytask.Util.Constants.TEXT_DIRECTION_CENTER;
import static com.codescafe.dailytask.Util.Constants.TEXT_DIRECTION_LEFT;
import static com.codescafe.dailytask.Util.Constants.TEXT_DIRECTION_RIGHT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codescafe.dailytask.Adapter.ColorsAdapter;
import com.codescafe.dailytask.Adapter.FontsAdapter;
import com.codescafe.dailytask.Model.TaskModelData2;
import com.codescafe.dailytask.Model.TaskModelData5;
import com.codescafe.dailytask.Model.TaskModelData6;
import com.codescafe.dailytask.Util.Constants;
import com.codescafe.dailytask.Model.ColorSet;
import com.codescafe.dailytask.R;
import com.codescafe.dailytask.Util.TaskModel;
import com.divyanshu.draw.activity.DrawingActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CreateTask6 extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1001;
    EditText dateSet, etTitle, subject,etStory;
    TextView subject1,dateSet1;
    ImageView doneNow,ivCancelTextStyling,ivRightAlign,ivLeftAlign,ivCenterAlign;
    TaskModel taskModel;
    RelativeLayout layout1, layout2, layout3, layout4, layout5, layout6;
    String x = "";
    CardView imgCard;
    Uri pathof;
    String pathset,setVoicePath;
    ImageView img;
    MediaRecorder mediaRecorder;
    Activity activity;
    Chronometer timer;
    HorizontalScrollView footer;
    File Voice_file;
    CardView voice_send;
    RelativeLayout voice_layout;
    ImageView voice_cancel, attachFiles, voice_img,voiceSend,voiceStart;
    Boolean isRecording = false;
    ArrayList<String> infoList = new ArrayList<>();
    ImageView ivChooseImage,ivDraw,ivTextStyles;
    int fontId = DEFAULT;
    int colorId = DEFAULT;
    int textSize = DEFAULT_SLIDER_VALUE;
    int textDirection = TEXT_DIRECTION_LEFT;
    RecyclerView rvFonts,rvColors;
    Slider sizeSlider;
    ConstraintLayout textStyleLayout;
    String Voicepath="";

    private DatabaseReference database;
    private TaskModelData6 model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task6);
        activity = this;
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        taskModel = new TaskModel("", "", "", "", "", "", "");
        x = getIntent().getStringExtra("val");

        database = FirebaseDatabase.getInstance().getReference().child("Daily Task List Six");
        model = new TaskModelData6();

        ivCenterAlign = findViewById(R.id.ivCenterAlign);
        ivRightAlign = findViewById(R.id.ivRightAlign);
        ivLeftAlign = findViewById(R.id.ivLeftAlign);
        mediaRecorder = new MediaRecorder();
        ivCancelTextStyling = findViewById(R.id.ivCancelTextStyling);
        textStyleLayout = findViewById(R.id.textStyleLayout);
        footer = findViewById(R.id.footer);
        rvColors = findViewById(R.id.rvColors);
        rvFonts = findViewById(R.id.rvFonts);

        sizeSlider = findViewById(R.id.sizeSlider);
        layout1 = findViewById(R.id.layout5);
        ivDraw = findViewById(R.id.ivDraw);
        ivChooseImage = findViewById(R.id.ivChooseImage);
        ivTextStyles = findViewById(R.id.ivTextStyles);

        dateSet = findViewById(R.id.dateSet);
        doneNow = findViewById(R.id.doneNow);
        etTitle = findViewById(R.id.etTitle6);
        subject = findViewById(R.id.subject);
        etStory = findViewById(R.id.etStory6);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TaskModelData6 taskModelData = snapshot.getValue(TaskModelData6.class);

                if (taskModelData != null) {
                    etTitle.setText(taskModelData.getLecture());
                    etStory.setText(taskModelData.getDescription());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cur_calender = Calendar.getInstance();
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                long date_ship_millis = calendar.getTimeInMillis();
                                dateSet.setText(getFormattedDateSimple(date_ship_millis));
                            }
                        },
                        cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH)
                );
                //set dark light
                datePicker.setThemeDark(false);
                datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePicker.setMinDate(cur_calender);
                datePicker.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        doneNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Lecture = etTitle.getText().toString();
                String Description = etStory.getText().toString();

                if (TextUtils.isEmpty(Lecture) && TextUtils.isEmpty(Description)) {
                    Toast.makeText(CreateTask6.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(Lecture, Description);
                }



                if (etTitle.getText().toString().trim().equals("Title") && etTitle.getText().toString().trim().equals("")) {
                    Toast.makeText(activity, "Enter Title", Toast.LENGTH_SHORT).show();
                } else {
                    String key = "" + System.currentTimeMillis();
                    taskModel.setId(key);
                    taskModel.setTitle(etTitle.getText().toString().trim());
                    taskModel.setDate(dateSet.getText().toString().trim());
                    taskModel.setSubject(subject.getText().toString().trim());
                    taskModel.setDescription(etStory.getText().toString().trim());
                    taskModel.setPicture(""+pathset);
                    taskModel.setVoice(""+Voicepath);
                    taskModel.setTheme(""+x);
                    taskModel.setFont(""+fontId);
                    taskModel.setColor(""+colorId);

                    ArrayList<TaskModel> list = new ArrayList<>();
                    SharedPreferences pref = getSharedPreferences("tasks", MODE_PRIVATE);
                    Gson gson = new Gson();
                    String val = pref.getString("task", "");
                    if (!val.equals("")) {
                        list = gson.fromJson(val, new TypeToken<ArrayList<TaskModel>>() {
                        }.getType());
                    }
                    list.add(taskModel);
                    getSharedPreferences("tasks", MODE_PRIVATE).edit().putString("task", gson.toJson(list)).apply();
                    Log.e("i'm here", "true" + list.size());
                    //Toast.makeText(CreateTask.this, ""+val, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();

                }

            }
        });
        ivChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
                View v = getLayoutInflater().inflate(R.layout.medialayout,null);
                bottomSheetDialog.setContentView(v);
                img = v.findViewById(R.id.img);
                imgCard = v.findViewById(R.id.imgCard);
                voiceStart = v.findViewById(R.id.voiceStart);
                voiceSend = v.findViewById(R.id.voiceSend);
                voice_cancel = v.findViewById(R.id.voice_cancel);
                voice_layout = v.findViewById(R.id.voice_layout);
                timer = v.findViewById(R.id.timer);
                imgCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openLayout();
                    }
                });
                voiceStart.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO)
                                    == PackageManager.PERMISSION_DENIED) {
                                String[] permissions = {Manifest.permission.RECORD_AUDIO};
                                requestPermissions(permissions, PERMISSION_CODE);
                            } else {
                                if (validateMicAvailability()) {
                                    record_voice();
                                } else {
                                    Toast.makeText(activity, "Mic Already using", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            if (validateMicAvailability()) {
                                record_voice();
                            } else {
                                Toast.makeText(activity, "Mic Already using", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                voice_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaRecorder != null) {
                            try {
                                stopRecording();
                                Voice_file.delete();
                                voiceSend.setVisibility(View.GONE);
                                voiceStart.setVisibility(View.VISIBLE);
                                //voice_btm.setImageResource(R.drawable.ic_voice1);
                                voice_layout.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }
                        }
                    }
                });
                voiceSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaRecorder != null) {
                            try {
                                stopRecording();
                                // Toast.makeText(activity, "here sending...", Toast.LENGTH_SHORT).show();
                                // voice_btm.setImageResource(R.drawable.ic_voice1);
                                voice_layout.setVisibility(View.GONE);
                                voiceSend.setVisibility(View.GONE);
                                voiceStart.setVisibility(View.VISIBLE);
                                Uri data = Uri.fromFile(Voice_file);
                                //sendFile1(data, infoList);
                                setVoicePath = getPath(data);
                                String path = data.getPath(); // "/mnt/sdcard/FileName.mp3"
                                File file = new File(new URI(path));
                                Log.e("voice", ""+file);
                                Log.e("voiceFunction", ""+getRealPathAudioFromURI(data));
                                //Toast.makeText(activity, ""+getPath(data), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                            }

                            //  Toast.makeText(getApplicationContext(),data.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
                bottomSheetDialog.show();

            }
        });
        ivDraw.setOnClickListener(view -> {
            closeKeyboard();
            Intent intent = new Intent(this, DrawingActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE_DRAW);
        });
        ivTextStyles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                textStyleLayout.setVisibility(View.VISIBLE);
                footer.setVisibility(View.GONE);
            }
        });
        ivCancelTextStyling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textStyleLayout.setVisibility(View.GONE);
                footer.setVisibility(View.VISIBLE);
            }
        });

        setAlignmentListeners();
        setColors();
        setSizeSlider();
        setFonts();

    }

    private void addDatatoFirebase(String lecture, String description) {

        model.setLecture(lecture);
        model.setDescription(description);

        database.setValue(model);

        etTitle.setText("");
        etStory.setText("");


    }

    private void openLayout() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View v = getLayoutInflater().inflate(R.layout.layout_route, null);
        bottomSheetDialog.setContentView(v);

        LinearLayout galleryMe, cameraMe;

        galleryMe = v.findViewById(R.id.galleryMe);
        cameraMe = v.findViewById(R.id.cameraMe);
        galleryMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(100);
                bottomSheetDialog.dismiss();
            }
        });
        cameraMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(400);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return newFormat.format(new Date(dateTime));
    }
    private void openGallery(int i) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, i);
    }
    private void openCamera(int i) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewImage");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");
        pathof = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, pathof);
        startActivityForResult(camera_intent, i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 100 && data != null) {
            pathof = Objects.requireNonNull(data).getData();
            //String filePath = uriFilePath.getPath();
            //file = new File(pathof.getPath());
            //Toast.makeText(this, ""+getPath(pathof), Toast.LENGTH_SHORT).show();
            pathset = getPath(pathof);
            img.setImageURI(pathof);
        }else if (resultCode == RESULT_OK && requestCode == 400) {
            //pathof = Objects.requireNonNull(data).getData();
            // file = new File(pathof.getPath());
            pathset = getPath(pathof);
            img.setImageURI(pathof);
            //Toast.makeText(this, ""+getPath(pathof), Toast.LENGTH_SHORT).show();
            //findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            //Snackbar.make(main, "Please wait, Sending...", Snackbar.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        Log.e("ImagePath:" ,""+path);
        return path;
    }
    public String getVoicePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Audio.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        cursor.close();
        Log.e("ImageVoice:" ,""+path);

        return path;
    }
    private void record_voice() {
        mediaRecorder = null;
        //Toast.makeText(activity, "before", Toast.LENGTH_SHORT).show();
        // Toast.makeText(activity, "before / after", Toast.LENGTH_SHORT).show();
        voiceStart.setVisibility(View.GONE);
        voiceSend.setVisibility(View.VISIBLE);
        voice_layout.setVisibility(View.VISIBLE);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/CodesCafe/Voice");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Voice_file = new File(dir.toString(), "" + System.currentTimeMillis() + ".mp3");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaRecorder.setOutputFile(Voice_file);
            Voicepath = ""+Voice_file;
            Log.e("setPath",""+Voicepath);
        }
        mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                // Toast.makeText(getApplicationContext(),""+what+""+extra,Toast.LENGTH_SHORT).show();
            }
        });
        mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                //Toast.makeText(getApplicationContext(),""+what+""+extra,Toast.LENGTH_SHORT).show();
            }
        });
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean validateMicAvailability() {
        Boolean available = true;
        @SuppressLint("MissingPermission") AudioRecord recorder =
                new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_DEFAULT, 44100);
        try {
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                available = false;

            }

            recorder.startRecording();
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop();
                available = false;

            }
            recorder.stop();
        } finally {
            recorder.release();
            recorder = null;
        }

        return available;
    }
    private void stopRecording() {

        if (isRecording) {
            mediaRecorder.stop();
        }
        mediaRecorder.reset();   // You can reuse the object by going back to setAudioSource() step
        mediaRecorder.release();// Now the object cannot be reused
        mediaRecorder = null;
        isRecording = false;

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isRecording) {
            stopRecording();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRecording) {
            stopRecording();
        }
    }
    @Override
    public void onBackPressed() {
        if (isRecording) {
            stopRecording();
        }
        super.onBackPressed();
        finish();
    }
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void setFonts() {
        List<String> fonts = DataSource.getAllFonts(this);
        FontsAdapter fontsAdapter = new FontsAdapter(fonts, (adapterView, view, i, l) -> {
            fontId = i;
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fonts.get(i));
            etTitle.setTypeface(typeface);
            etStory.setTypeface(typeface);
            dateSet.setTypeface(typeface);
            subject.setTypeface(typeface);
        });
        rvFonts.setLayoutManager(new GridLayoutManager(this, 3));
        rvFonts.setAdapter(fontsAdapter);
    }
    private void setSizeSlider() {
        sizeSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

                textSize = (int) slider.getValue();
                etStory.setTextSize((int)FONT_SIZE + slider.getValue());
                etTitle.setTextSize((int)FONT_SIZE + slider.getValue());
                subject.setTextSize((int)FONT_SIZE + slider.getValue());
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                etStory.setTextSize((int)FONT_SIZE + slider.getValue());
                etTitle.setTextSize((int)FONT_SIZE + slider.getValue());
                subject.setTextSize((int)FONT_SIZE + slider.getValue());
            }
        });
    }
    private void setColors() {
        List<ColorSet> colorsList = DataSource.getAllColors();
        ColorsAdapter colorsAdapter = new ColorsAdapter(colorsList, (adapterView, view, i, l) -> {
            colorId = i;
            dateSet.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            dateSet.setHintTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            etTitle.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            etTitle.setHintTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            etStory.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            etStory.setHintTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            subject.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            subject.setHintTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
        });
        rvColors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvColors.setAdapter(colorsAdapter);
    }
    private void setAlignmentListeners() {
        ivRightAlign.setOnClickListener(view -> {
            textDirection = TEXT_DIRECTION_RIGHT;
            etTitle.setGravity(Gravity.END);
            etStory.setGravity(Gravity.END);
            dateSet.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        });

        ivLeftAlign.setOnClickListener(view -> {
            textDirection = TEXT_DIRECTION_LEFT;
            etTitle.setGravity(Gravity.START);
            etStory.setGravity(Gravity.START);
            dateSet.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        });

        ivCenterAlign.setOnClickListener(view -> {
            textDirection = TEXT_DIRECTION_CENTER;
            etTitle.setGravity(Gravity.CENTER);
            etStory.setGravity(Gravity.CENTER);
            dateSet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        });
    }
    public String getRealPathAudioFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}