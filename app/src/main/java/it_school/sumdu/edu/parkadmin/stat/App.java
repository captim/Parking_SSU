package it_school.sumdu.edu.parkadmin.stat;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
