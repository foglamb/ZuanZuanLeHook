package de.robv.android.xposed.callbacks;
import de.robv.android.xposed.XCallback;
public abstract class XC_LoadPackage extends XCallback {
    public static class LoadPackageParam {
        public String packageName;
        public String processName;
        public ClassLoader classLoader;
        public boolean isFirstApplication;
    }
    public XC_LoadPackage() { super(PRIORITY_DEFAULT); }
    public XC_LoadPackage(int priority) { super(priority); }
    public abstract void handleLoadPackage(LoadPackageParam lpparam) throws Throwable;
}
