package com.example.schoolscheduler.LessonManagement;

import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class LMItemDetailsLookup extends ItemDetailsLookup<Long>{

    private RecyclerView recyclerView;

    public LMItemDetailsLookup(RecyclerView newRecyclerView){this.recyclerView = newRecyclerView;};

    public ItemDetailsLookup.ItemDetails<Long> getItemDetails(MotionEvent event) {
        View view = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if (view != null) {
            return ((LessonManagementRecyclerViewAdapter.EquipmentViewHolder) (recyclerView.getChildViewHolder(view))).getItemDetails();
        }
        return null;
    }
}
