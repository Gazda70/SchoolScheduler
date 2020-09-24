package com.example.schoolscheduler.SequentialSchedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class SSItemKeyProvider extends ItemKeyProvider<String> {

    private SequentialScheduleRecyclerViewAdapter adapter;
    public SSItemKeyProvider(SequentialScheduleRecyclerViewAdapter newAdapter){
        super(SCOPE_MAPPED);
        adapter = newAdapter;
    };

    @Nullable
    @Override
    public String getKey(int position) {
        return adapter.getItem(position);
    }

    @Override
    public int getPosition(@NonNull String key) {
        return adapter.getPosition(key);
    }
}
