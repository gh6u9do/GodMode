package com.bbb.godmode;

import static android.app.TimePickerDialog.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeScreen extends AppCompatActivity {
    SharedPreferences pref;

    private @NonNull
    com.bbb.godmode.databinding.ActivityHomeBinding binding;
    RecyclerView.Adapter adapter;

    ArrayList<Task> items;
    int hour, minute;

    RecyclerView items_view;
    Button timebtn;
    ImageButton toNotesBtn;
    TextView taskText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        binding = com.bbb.godmode.databinding.ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // для сохранения заметок
        pref = getSharedPreferences("TASKS", MODE_PRIVATE);
        loadTasks();

        timebtn = binding.timeBtn;
        toNotesBtn = binding.toNotesBtn;
        taskText = binding.taskText;



        Log.d("AABE", "A");

        // Работа с динамическим списком
        items_view = binding.rTview;
//        Log.d("AABE", "B");
        items_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        Log.d("AABE", "C");
        // Задает адаптер для списка из элементов привычек
        adapter = new ItemAdapter(DataSet.items);
//        Log.d("AABE", String.valueOf(DataSet.items.size()));
        items_view.setAdapter(adapter);
//         Log.d("AABE", "D");


    }

    // Создание поп-апа для выбора времени
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selHour, int selMinute)
            {
                hour = selHour;
                minute = selMinute;
                timebtn.setText(String.format("%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("22:00");
        timePickerDialog.show();
    }

    public void addTask(View view)
    {
        Task item = new Task(String.valueOf(taskText.getText()),hour, minute);
        //Log.d("AABE", item.toString());
        DataSet.items.add(0, item);
//

        // обнуление поля ввода
        taskText.setText("");
        adapter.notifyDataSetChanged();
    }

//    private Notification getNotification() {
//        Log.d("workk", "getNot");
//        Intent intent = new Intent(getBaseContext(), NotificationPublisher.class);
////        intent.putExtra("title", /*getString(R.string.notificationtitle)*/);
////        intent.putExtra("text", getString(/*R.string.notificationtext)*/);
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
//                .setContentTitle("Rsba")
//                .setContentText("Bebra")
//                .setContentIntent(pIntent)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_SOUND);
//        Log.d("workk", "getNot2");
//        return builder.build();
//    }
//
//    private void scheduleNotification(Notification notification, Calendar targetCal){
//        Log.d("workk", "shed");
//        Intent notificationIntent = new Intent(getBaseContext(), NotificationPublisher.class);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, notificationIntent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Log.d("workk", "shed2");
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
//        Log.d("workk", "shed3");
//    }
//
//    private void createChannelIfNeeded(NotificationManager notificationManager) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel("100", "100", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//    }


    public void toNotes(View view)
    {
        Intent intent = new Intent(this, Notes.class);

        startActivity(intent);

        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTasks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTasks();
    }

    private void saveTasks()
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putInt("TASKS_SIZE", DataSet.items.size());
        //Log.d("AAAAA", "SAVE " +String.valueOf(DataSet.items.size()) + " | " + String.valueOf(DataSet.notes.size()));
        for(int i = 0; i < DataSet.items.size(); i++) {
            editor.putString("TASKS_TEXT"+String.valueOf(i), DataSet.items.get(i).text);
            editor.putInt("TASKS_HOUR"+String.valueOf(i), DataSet.items.get(i).hour);
            editor.putInt("TASKS_MINUTE"+String.valueOf(i), DataSet.items.get(i).minute);
            editor.putBoolean("TASKS_DONE"+String.valueOf(i), DataSet.items.get(i).done);
        }
        editor.apply();
    }

    private void loadTasks()
    {
        // Подгружает данные из памяти андройда, только при иницпиализации приложения
        if(DataSet.items.size() == 0) {
            int items_size = pref.getInt("TASKS_SIZE", 0);
            //Log.d("AAAAA", "LOAD " + String.valueOf(items_size) + " | " + String.valueOf(DataSet.notes.size()));
            for (int i = 0; i < items_size; i++) {
                String text = pref.getString("TASKS_TEXT" + String.valueOf(i), "");
                int hour = pref.getInt("TASKS_HOUR"+String.valueOf(i), 0);
                int minute = pref.getInt("TASKS_MINUTE"+String.valueOf(i), 0);
                boolean done = pref.getBoolean("TASKS_DONE"+String.valueOf(i), false);
                DataSet.items.add( new Task(text, hour, minute, done));
            }
        }
    }
}