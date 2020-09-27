package com.example.schoolscheduler.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "table_equipment")
public class Equipment {

   public Equipment(int eqId,String name){this.eqId = eqId; this.name = name;}

    @PrimaryKey
    public int eqId;
    @ColumnInfo
    public String name;
}
