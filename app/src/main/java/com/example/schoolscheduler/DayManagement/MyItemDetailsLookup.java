package com.example.schoolscheduler.DayManagement;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolscheduler.DayManagement.DayManagementFragmentRecyclerViewAdapter;

class MyItemDetailsLookup extends
        ItemDetailsLookup<Long> {

    private RecyclerView recyclerView;

    public MyItemDetailsLookup(RecyclerView toAssign){recyclerView = toAssign;}

    public ItemDetails<Long> getItemDetails(MotionEvent event) {
        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if (view != null) {
            return ((DayManagementFragmentRecyclerViewAdapter.LessonsViewHolder) (recyclerView.getChildViewHolder(view))).getItemDetails();
        }
        return null;
    }
}
