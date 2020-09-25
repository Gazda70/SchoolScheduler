package com.example.schoolscheduler.LessonManagement;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolscheduler.R;
import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LessonManagementRecyclerViewAdapter extends RecyclerView.Adapter<LessonManagementRecyclerViewAdapter.EquipmentViewHolder> {

    private final List<Equipment> mValues;

    public LessonManagementRecyclerViewAdapter(List<Equipment> items) {
        setHasStableIds(true);
        mValues = items;
    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lesson_management, parent, false);
        return new EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EquipmentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class EquipmentViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Equipment mItem;

        public EquipmentViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}