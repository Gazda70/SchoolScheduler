package com.example.schoolscheduler;

import android.content.Context;

public class ApplicationContextHolder {

    private static Context appContext;

    public static Context getHeldApplicationContext(){
            return appContext;
    }

    public static void setHeldApplicationContext(Context newContext){
        appContext = newContext;
    }
}
