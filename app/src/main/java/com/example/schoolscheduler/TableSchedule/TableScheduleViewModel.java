package com.example.schoolscheduler.TableSchedule;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolscheduler.R;

public class TableScheduleViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final int columnsNumber = 6;

    private final int textColor = Color.GREEN;

    private final CharSequence placeholder = "---";

    private int rowsNumber = 0;
    private void incrementRowsNumber(){
        rowsNumber++;
    }

    private MutableLiveData<Boolean> onRowAdded;
    public MutableLiveData<Boolean> getOnRowAdded(){
        if(onRowAdded == null){
            onRowAdded = new MutableLiveData<Boolean>();
        }
        return onRowAdded;
    }
    public void setOnRowAdded(Boolean newValue){
        getOnRowAdded().setValue(newValue);
    }

    public TableRow addRowToSchedule(TableLayout body, Context context){
        TableRow row = new TableRow(context);
        for (int i = 0; i < columnsNumber; i++){
            row.addView(createColumnPlaceholderText(rowsNumber + columnsNumber, context));
        }
        body.addView(row, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT
        ));
        incrementRowsNumber();
        return row;
    }



    private TextView createColumnPlaceholderText(int id, Context context){
        TextView emptyField = new TextView(context);
        emptyField.setId(id);
        emptyField.setText(placeholder);
        emptyField.setTextColor(textColor);
        emptyField.setClickable(true);
        emptyField.setFocusable(true);
        emptyField.setBackgroundResource(R.drawable.cell_shape);
        return emptyField;
    }


}