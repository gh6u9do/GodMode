package com.bbb.godmode;

import android.view.View;

public class Task {
    String text;
    String time = "00:00";
    int hour = 0;
    int minute = 0;
    boolean done = false;

    Task(String text, int hour, int minute){
        this.text = text;
        this.hour = hour;
        this.minute = minute;
        time = String.format("%02d:%02d", hour, minute);
    }
    Task(String text, int hour, int minute, boolean done){
        this.text = text;
        this.hour = hour;
        this.minute = minute;
        this.done = done;
        time = String.format("%02d:%02d", hour, minute);
    }

    @Override
    public String toString() {
        return "Task{" +
                "text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                ", done=" + done +
                '}';
    }
}
