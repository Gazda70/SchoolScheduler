package com.example.schoolscheduler.DayManagement;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolscheduler.R;
import com.example.schoolscheduler.database.Lesson;
import com.example.schoolscheduler.dummy.DummyContent.DummyItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DayManagementFragmentRecyclerViewAdapter extends RecyclerView.Adapter<DayManagementFragmentRecyclerViewAdapter.LessonsViewHolder> {

    private final List<Lesson> mValues;

    private final int parentMeasuredHeightDivisor = 5;

    private SelectionTracker<Long> tracker;

    private Lesson recentlyDeletedItem;

    private int recentlyDeletedItemPosition;

    public DayManagementFragmentRecyclerViewAdapter(List<Lesson> toAssign) {
        mValues = toAssign;
    }

    @Override
    public long getItemId(int position){
        return (long)position;
    }

    public void setTracker(SelectionTracker<Long> newTracker){
        tracker = newTracker;
    }

    @Override
    public LessonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_fragment_item, parent, false);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)view.getLayoutParams();
        params.height = parent.getMeasuredHeight() / parentMeasuredHeightDivisor;
        view.setLayoutParams(params);

        return new LessonsViewHolder(view);
    }

    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView){
        final int adapterPosition = viewHolder.getAdapterPosition();
        final Lesson mLesson = mValues.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, "PHOTO REMOVED", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //int mAdapterPosition = viewHolder.getAdapterPosition();
                        Log.i("ADAPTER POSITION", String.valueOf((char)(adapterPosition)));
                        mValues.add(adapterPosition, mLesson);
                        notifyItemInserted(adapterPosition);
                        recyclerView.scrollToPosition(adapterPosition);
                    }
                });
        snackbar.show();
        mValues.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    @Override
    public void onBindViewHolder(final LessonsViewHolder holder, int position) {
        boolean isSelected = false;
        String lessonName = mValues.get(position).activityName;
        String lessonDuration = mValues.get(position).activityDuration;
        if(tracker != null) {
            isSelected = tracker.isSelected((long)position);
        }
        holder.bind(lessonName, lessonDuration, isSelected);
    }

    /*
    public void deleteItem(int position) {
        recentlyDeletedItem = mValues.get(position);
        recentlyDeletedItemPosition = position;
        mValues.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = activity.findViewById(R.id.show_lesson);
        Snackbar snackbar = Snackbar.make(view, R.string.deleted_lesson,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValues.add(recentlyDeletedItemPosition,
                        recentlyDeletedItem);
                notifyItemInserted(recentlyDeletedItemPosition);
                Log.i("SNACKBAR", "UNDO DELETE");
            }
        });
        snackbar.show();
    }*/

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class LessonsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final LinearLayout innerLayout;
        public final TextView nameView;
        public final TextView durationView;

        public LessonsViewHolder(View view) {
            super(view);
            mView = view;
            innerLayout = (LinearLayout) view.findViewById(R.id.lesson_inner_layout);
            nameView = (TextView) view.findViewById(R.id.lesson_name);
            durationView = (TextView) view.findViewById(R.id.lesson_duration);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'" + durationView.getText();
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

        private void bind(String lessonName, String lessonDuration, boolean isActivated){
            this.durationView.setText(lessonDuration);
            this.nameView.setText(lessonName);
            itemView.setActivated(isActivated);
        }

    }
}