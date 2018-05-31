package com.example.n009654.aidldemoserver;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

public class LeakApplication extends Application {
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher= setupLeakCanary();
    }
    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(getApplicationContext())) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
    private static List<AppCompatActivity> lists = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity) {
        lists.add(activity);
    }

    public static void clearActivity() {
        if (lists != null) {
            for (AppCompatActivity activity : lists) {
                activity.finish();
            }

            lists.clear();
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        LeakApplication leakApplication = (LeakApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

}
