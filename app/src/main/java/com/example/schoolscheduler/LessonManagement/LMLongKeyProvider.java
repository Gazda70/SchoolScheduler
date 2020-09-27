package com.example.schoolscheduler.LessonManagement;

import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import static androidx.recyclerview.selection.ItemKeyProvider.SCOPE_MAPPED;

public class LMLongKeyProvider extends ItemKeyProvider<Long> {

    private RecyclerView recyclerView;

    public LMLongKeyProvider(RecyclerView newRecyclerView
    ){
        super(SCOPE_MAPPED);
        this.recyclerView = Objects.requireNonNull(newRecyclerView);
    }

    @Override
    public Long getKey(int position){
        return Objects.requireNonNull(recyclerView.getAdapter()).getItemId(position);
    }

    @Override
    public int getPosition(Long key){
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForItemId(key);
        int position = RecyclerView.NO_POSITION;
        if(viewHolder != null) {
            position = viewHolder.getLayoutPosition();
        }
        return position;
    }
}
