package com.example.schoolscheduler.SequentialSchedule;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDetailsLookup extends
        androidx.recyclerview.selection.ItemDetailsLookup<String> {

    private RecyclerView myRecyclerView;

    public ItemDetailsLookup(RecyclerView toAssign){myRecyclerView = toAssign;}

    @Override
   public @Nullable
    ItemDetails<String> getItemDetails(@NonNull MotionEvent event){
        View view = myRecyclerView.findChildViewUnder(event.getX(),event.getY());
        if(view != null){
             RecyclerView.ViewHolder viewHolder = myRecyclerView.getChildViewHolder(view);
             if(viewHolder instanceof MyItemRecyclerViewAdapter.DayViewHolder)
             {
                 return ((MyItemRecyclerViewAdapter.DayViewHolder)viewHolder).getItemDetails();
             }
        }
        return null;
    }
}
