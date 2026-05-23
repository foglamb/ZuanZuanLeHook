package com.zuanzuanle.hook;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_MethodHook;
import java.lang.reflect.Method;

public class MainHook implements IXposedHookLoadPackage {
    private static final String TAG = "ZZL_HOOK";
    private static final String TARGET_PKG = "com.yxrjshun.yingkeji";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam p) throws Throwable {
        if (!p.packageName.equals(TARGET_PKG)) return;
        Log.i(TAG, "瞬影科技模块加载");
        XposedBridge.log(TAG + " 加载成功");
        hookYXMineNet(p.classLoader);
        hookMineVM(p.classLoader);
        hookRedPacket(p.classLoader);
        hookOkHttp(p.classLoader);
    }

    private void hookYXMineNet(ClassLoader cl) {
        try {
            Class<?> c = cl.loadClass("com.cka.renren.v2.ui.mine.api.YXMineNet");
            hookX(c, "adTask", new XC_MethodHook() {
                @Override protected void beforeHookedMethod(MethodHookParam p) { log("adTask " + args(p.args)); }
                @Override protected void afterHookedMethod(MethodHookParam p) { log("adTask=" + p.getResult()); }
            });
            hookX(c, "adRegister", new XC_MethodHook() {
                @Override protected void beforeHookedMethod(MethodHookParam p) { log("adRegister " + args(p.args)); }
            });
            hookX(c, "getTeaGol", new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam p) { log("getTeaGol=" + p.getResult()); }
            });
            log("YXMineNet OK");
        } catch (Exception e) { log("YXMineNet FAIL: " + e.getMessage()); }
    }

    private void hookMineVM(ClassLoader cl) {
        try {
            Class<?> c = cl.loadClass("com.cka.renren.vm.MineVM");
            hookAll(c, "forecast_gold", new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam p) {
                    if (p.getResult() instanceof Number)
                        log("forecast_gold=" + ((Number)p.getResult()).intValue());
                }
            });
            log("MineVM OK");
        } catch (Exception e) { log("MineVM FAIL: " + e.getMessage()); }
    }

    private void hookRedPacket(ClassLoader cl) {
        try {
            Class<?> c = cl.loadClass("com.cka.renren.main.mine.YqKtFragment");
            hookAll(c, "isRedPacketInCooldown", new XC_MethodHook() {
                @Override protected void beforeHookedMethod(MethodHookParam p) { log("冷却=false"); p.setResult(false); }
            });
            log("红包OK");
        } catch (Exception e) { log("红包FAIL: " + e.getMessage()); }
    }

    private void hookOkHttp(ClassLoader cl) {
        try {
            Class<?> b = cl.loadClass("okhttp3.Request$Builder");
            hookAll(b, "build", new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam p) {
                    try {
                        Object req = p.getResult();
                        String url = XposedHelpers.callMethod(req, "url").toString();
                        if (url.contains("yingkeji") || url.contains("xinmanman")) {
                            log("URL: " + url);
                            log("HDRS: " + XposedHelpers.callMethod(req, "headers"));
                        }
                    } catch (Exception ignored) {}
                }
            });
            log("OkHttp OK");
        } catch (Exception e) { log("OkHttp FAIL: " + e.getMessage()); }
    }

    private void hookX(Class<?> clz, String m, XC_MethodHook h) {
        try { XposedHelpers.findAndHookMethod(clz, m, h); }
        catch (Exception e) {
            for (Method md : clz.getDeclaredMethods()) {
                if (md.getName().equals(m)) {
                    try { XposedBridge.hookMethod(md, h); return; } catch (Exception ignored) {}
                }
            }
            log(m + " NOT FOUND");
        }
    }

    private void hookAll(Class<?> clz, String m, XC_MethodHook h) {
        boolean ok = false;
        for (Method md : clz.getDeclaredMethods()) {
            if (md.getName().equals(m)) {
                try { XposedBridge.hookMethod(md, h); ok = true; } catch (Exception ignored) {}
            }
        }
        if (!ok) log(m + " NOT FOUND(all)");
    }

    private String args(Object[] a) {
        if (a == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(a[i] != null ? a[i].getClass().getSimpleName() + "=" + a[i] : "");
        }
        return sb.append("]").toString();
    }

    private void log(String s) { Log.i(TAG, s); XposedBridge.log(TAG + " " + s); }
}
