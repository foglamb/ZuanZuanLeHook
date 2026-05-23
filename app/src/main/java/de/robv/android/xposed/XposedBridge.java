package de.robv.android.xposed;

import de.robv.android.xposed.callbacks.XC_MethodHook;
import java.lang.reflect.Member;

public class XposedBridge {
    public static final String TAG = "Xposed";
    
    public static XC_MethodHook.Unhook hookMethod(java.lang.reflect.Method hookMethod, XC_MethodHook callback) {
        return new XC_MethodHook.Unhook(hookMethod, callback);
    }
    
    public static void log(String text) {
        android.util.Log.i(TAG, text);
    }
}
