package com.example.schoolscheduler.SequentialSchedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class MyItemKeyProvider extends ItemKeyProvider<String> {

    private MyItemRecyclerViewAdapter adapter;
    public MyItemKeyProvider(MyItemRecyclerViewAdapter newAdapter){
        super(SCOPE_CACHED);
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
