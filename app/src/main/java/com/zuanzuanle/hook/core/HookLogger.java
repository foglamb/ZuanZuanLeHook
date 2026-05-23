package com.zuanzuanle.hook.core;

import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import java.text.SimpleDateFormat;
import java.util.*;
import android.os.*;

public class HookLogger {
    private static final int MAX_LOG = 500;
    private static final List<String> logs = Collections.synchronizedList(new ArrayList<>());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
    
    public static void log(String tag, String msg) {
        android.util.Log.d(tag, msg);
        String time = sdf.format(new Date());
        logs.add(String.format("[%s] [%s] %s", time, tag, msg));
        if (logs.size() > MAX_LOG) {
            logs.remove(0);
        }
    }
    
    public static void log(String tag, String msg, Throwable tr) {
        android.util.Log.e(tag, msg, tr);
        String time = sdf.format(new Date());
        logs.add(String.format("[%s] [ERROR] [%s] %s: %s", time, tag, msg, tr.getMessage()));
        if (logs.size() > MAX_LOG) logs.remove(0);
    }
    
    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }
    
    public static void clear() {
        logs.clear();
    }
}
