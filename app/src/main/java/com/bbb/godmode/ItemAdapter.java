package com.bbb.godmode;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<Task> items;
    public RecyclerView.ViewHolder holder;

    public ItemAdapter(ArrayList<Task> items){

        this.items = items;
        //Log.d("AABE", "ASDA" + String.valueOf(this.items.size()));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int index){
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_item, parent, false)
        )
        {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Task item = this.items.get(position);
        //Log.d("AABE", "$position");

        this.holder = holder;

        // Задание отображения элемента
        TextView taskText = holder.itemView.findViewById(R.id.taskText);
        TextView taskTime = holder.itemView.findViewById(R.id.taskTimeText);
        ImageButton taskBtn = holder.itemView.findViewById(R.id.taskBtn);
        ImageButton delBtn = holder.itemView.findViewById(R.id.delBtn);

        taskText.setText(item.text);
        taskTime.setText(item.time);
        // Для корректной инициализации красной кнопки
        if(!item.done){
            taskBtn.setColorFilter(Color.rgb(242, 96, 255));

        }else{
            taskBtn.setColorFilter(Color.rgb(99, 46, 104));

        }

        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                // При клике обновляется булевую переменную
                DataSet.items.get(position).done = !DataSet.items.get(position).done;
                // Без этого изменения не отобразятся на экране
                notifyDataSetChanged();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                DataSet.items.remove(position);
                // При вызове этой функции контейнер заново инициализируется
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
