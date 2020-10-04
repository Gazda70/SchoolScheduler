package com.example.schoolscheduler.LessonManagement;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schoolscheduler.DayManagement.DayManagementFragmentRecyclerViewAdapter;
import com.example.schoolscheduler.DayManagement.DayManagementViewModel;
import com.example.schoolscheduler.R;
import com.example.schoolscheduler.database.Equipment;
import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.dummy.DummyContent.DummyItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LessonManagementRecyclerViewAdapter extends RecyclerView.Adapter<LessonManagementRecyclerViewAdapter.EquipmentViewHolder> {

    private List<Equipment> mValues;

    private SelectionTracker<Long> tracker;

    public LessonManagementRecyclerViewAdapter(List<Equipment> items) {
        setHasStableIds(false);
        mValues = items;
    }

    public void setTracker(SelectionTracker<Long> newTracker){
        tracker = newTracker;
    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lesson_management, parent, false);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)view.getLayoutParams();
        int parentMeasuredHeightDivisor = 5;
        params.height = parent.getMeasuredHeight() / parentMeasuredHeightDivisor;
        view.setLayoutParams(params);

        return new EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EquipmentViewHolder holder, int position) {
        boolean isSelected = false;
        if(tracker != null) {
            isSelected = tracker.isSelected((long)position);
        }
        holder.bind(mValues.get(position).name, isSelected);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class EquipmentViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;

        public EquipmentViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        ItemDetailsLookup.ItemDetails<Long> getItemDetails(){
            return new androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails<Long>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @NonNull
                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        private void bind(String name, boolean isActivated){
            this.mContentView.setText(name);
            itemView.setActivated(isActivated);
        }
    }
    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView){
        final int adapterPosition = viewHolder.getAdapterPosition();
        final Equipment mEq = mValues.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, "ITEM REMOVED", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mValues.add(adapterPosition, mEq);
                        notifyItemInserted(adapterPosition);
                        recyclerView.scrollToPosition(adapterPosition);
                    }
                });
        snackbar.show();
        mValues.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }
}