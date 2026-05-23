package de.robv.android.xposed.callbacks;
import de.robv.android.xposed.XCallback;
import java.lang.reflect.Member;
public abstract class XC_MethodHook extends XCallback {
    public XC_MethodHook() { super(PRIORITY_DEFAULT); }
    public XC_MethodHook(int priority) { super(priority); }
    public static class MethodHookParam {
        public Object thisObject;
        public Object[] args;
        public Object getResult() { return null; }
        public void setResult(Object result) {}
        public Throwable getThrowable() { return null; }
        public void setThrowable(Throwable throwable) {}
        public Object getObjectExtra(String key) { return null; }
        public void setObjectExtra(String key, Object o) {}
        public Member method;
    }
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {}
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {}
    public static class Unhook implements Comparable<Unhook> {
        private final Member hookMethod;
        private final XC_MethodHook callback;
        public Unhook(Member hookMethod, XC_MethodHook callback) { this.hookMethod = hookMethod; this.callback = callback; }
        public Member getHookedMethod() { return hookMethod; }
        public XC_MethodHook getCallback() { return callback; }
        public void unhook() {}
        @Override public int compareTo(Unhook other) { return 0; }
    }
}
