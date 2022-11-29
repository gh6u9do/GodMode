package com.bbb.godmode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public final class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<Note> items;
    public RecyclerView.ViewHolder holder;

    // Полностью аналогичен классу ItemAdapter

    public NotesAdapter(ArrayList<Note> items){

        this.items = items;
        Log.d("AABE", "ASDA" + String.valueOf(this.items.size()));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int index){
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_note, parent, false)
        )
        {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Note item = this.items.get(position);
        Log.d("AABE", "$position");

        this.holder = holder;

        TextView taskText = holder.itemView.findViewById(R.id.taskText);
        ImageButton delBtn = holder.itemView.findViewById(R.id.delBtn);

        taskText.setText(item.text);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                DataSet.notes.remove(position);
                Log.d("AAA", String.valueOf(DataSet.notes));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("AABE", "LOL" + String.valueOf(this.items.size()));
        return this.items.size();
    }

}
