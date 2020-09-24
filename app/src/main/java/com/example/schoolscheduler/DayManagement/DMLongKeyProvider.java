package com.example.schoolscheduler.DayManagement;

import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class DMLongKeyProvider extends ItemKeyProvider<Long> {
    private RecyclerView recyclerView;

    public DMLongKeyProvider(RecyclerView newRecyclerView
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
            int position = 0;
           if(viewHolder != null) {
                position = viewHolder.getLayoutPosition();
            }else {
                position = RecyclerView.NO_POSITION;
            }

            return position;
        }
    }

