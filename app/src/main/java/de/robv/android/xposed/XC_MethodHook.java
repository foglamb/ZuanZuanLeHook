package de.robv.android.xposed;

import java.lang.reflect.*;

public class XC_MethodHook extends XCallback {
    public XC_MethodHook() { super(50); }
    public XC_MethodHook(int priority) { super(priority); }

    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {}
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {}

    public static class MethodHookParam {
        public Object thisObject;
        public Object[] args;
        public Object getResult() { return getResultOrThrowable(); }
        public Object getResultOrThrowable() { return null; }
        public void setResult(Object result) {}
        public boolean hasReturnValue() { return false; }
        public Throwable getThrowable() { return null; }
        public void setThrowable(Throwable throwable) {}
    }

    public static class Unhook {
        private final Method method;
        private final XC_MethodHook callback;
        public Unhook(Method method, XC_MethodHook callback) { this.method = method; this.callback = callback; }
        public Method getHookedMethod() { return method; }
        public XC_MethodHook getCallback() { return callback; }
        public void unhook() {}
    }
}
