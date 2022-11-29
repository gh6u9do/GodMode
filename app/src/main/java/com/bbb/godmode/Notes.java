package com.bbb.godmode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bbb.godmode.databinding.ActivityNotesBinding;


public class Notes extends AppCompatActivity {

    SharedPreferences pref;

    ImageButton toTask;
    RecyclerView items_view;
    RecyclerView.Adapter adapter;
    TextView taskText;

    // Полностью аналогичен классу HomeScreen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotesBinding binding;
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setTheme(R.style.AppTheme);
        setContentView(binding.getRoot());

        pref = getSharedPreferences("NOTES", MODE_PRIVATE);
        toTask = binding.toHomeBtn;
        taskText = binding.taskText;
        Log.d("AABE", "A");
        items_view = binding.rNview;
//        Log.d("AABE", "B");
        items_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Log.d("AABE", "C");
        adapter = new NotesAdapter(DataSet.notes);
        Log.d("AABE", String.valueOf(DataSet.notes.size()));
        items_view.setAdapter(adapter);
        Log.d("AABE", "D");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    public void addNote(View view)
    {
        Note item = new Note(String.valueOf(taskText.getText()));
        Log.d("AABE", item.toString());
        DataSet.notes.add(0, item);
        taskText.setText("");
        adapter.notifyDataSetChanged();
    };

    public void toTask(View view)
    {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNotes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveNotes();
    }

    private void saveNotes()
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putInt("NOTES_SIZE", DataSet.notes.size());
        Log.d("AAAAA", "SAVE " +String.valueOf(DataSet.notes.size()) + " | " + String.valueOf(DataSet.notes.size()));
        for(int i = 0; i < DataSet.notes.size(); i++) {
            editor.putString("NOTE_TEXT"+String.valueOf(i), DataSet.notes.get(i).text);
        }
        editor.apply();
    }

    private void loadNotes()
    {

        if(DataSet.notes.size() == 0) {
            int notes_size = pref.getInt("NOTES_SIZE", 0);
            Log.d("AAAAA", "LOAD " + String.valueOf(notes_size) + " | " + String.valueOf(DataSet.notes.size()));
            for (int i = 0; i < notes_size; i++) {
                String note = pref.getString("NOTE_TEXT" + String.valueOf(i), "");
                DataSet.notes.add(new Note(note));
            }
        }
    }
}