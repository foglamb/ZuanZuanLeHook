package de.robv.android.xposed.callbacks;

import de.robv.android.xposed.XCallback;

public class XC_LoadPackage extends XCallback {
    public static final class LoadPackageParam {
        public String packageName;
        public String processName;
        public ClassLoader classLoader;
        public boolean isFirstApplication;
    }

    public XC_LoadPackage(int priority) { super(priority); }
    public XC_LoadPackage() { super(0); }
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {}
}
