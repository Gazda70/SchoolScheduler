package com.example.schoolscheduler.SequentialSchedule;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.schoolscheduler.R;
import com.example.schoolscheduler.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.DayViewHolder> {

    private final List<String> mValues;

    @Nullable
    private SelectionTracker<String> tracker;

    public void setTracker(SelectionTracker<String> newTracker){
        tracker = newTracker;
    }

    // functions used in MyItemKeyProvider
   public String getItem(int position){
       return mValues.get(position);
    }
   public int getPosition(String key){
       return mValues.indexOf(key);
   }

    public MyItemRecyclerViewAdapter(List<String> items) {mValues = items;}

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sequential_schedule, parent, false);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)view.getLayoutParams();
        params.height = parent.getMeasuredHeight() / 4;
        view.setLayoutParams(params);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DayViewHolder holder, int position) {
        boolean isSelected = false;
        String name = mValues.get(position);
        if(tracker != null) {
            isSelected = tracker.isSelected(name);
        }
       holder.bind(name, isSelected);
    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        public final TextView dayNameField;

        public DayViewHolder(View view) {
            super(view);
            dayNameField = (TextView) view.findViewById(R.id.editDayField);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dayNameField.getText() + "'";
        }


         ItemDetailsLookup.ItemDetails<String> getItemDetails(){
            return new androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails<String>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public String getSelectionKey() {
                    return dayNameField.getText().toString();
                }
            };
        }

        private void bind(String key, boolean isActivated){
            this.dayNameField.setText(key);
            itemView.setActivated(isActivated);
        }
    }
}