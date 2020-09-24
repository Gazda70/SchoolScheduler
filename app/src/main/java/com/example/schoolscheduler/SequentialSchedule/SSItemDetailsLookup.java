package com.example.schoolscheduler.SequentialSchedule;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class SSItemDetailsLookup extends
        androidx.recyclerview.selection.ItemDetailsLookup<String> {

    private RecyclerView myRecyclerView;

    public SSItemDetailsLookup(RecyclerView toAssign){myRecyclerView = toAssign;}

    @Override
   public @Nullable
    ItemDetails<String> getItemDetails(@NonNull MotionEvent event){
        View view = myRecyclerView.findChildViewUnder(event.getX(),event.getY());
        if(view != null){
             RecyclerView.ViewHolder viewHolder = myRecyclerView.getChildViewHolder(view);
             if(viewHolder instanceof SequentialScheduleRecyclerViewAdapter.DayViewHolder)
             {
                 return ((SequentialScheduleRecyclerViewAdapter.DayViewHolder)viewHolder).getItemDetails();
             }
        }
        return null;
    }
}
