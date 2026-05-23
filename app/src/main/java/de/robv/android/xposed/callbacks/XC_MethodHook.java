package de.robv.android.xposed.callbacks;

import de.robv.android.xposed.XCallback;
import java.lang.reflect.Method;

public class XC_MethodHook extends XCallback {
    public XC_MethodHook() { super(50); }
    public XC_MethodHook(int priority) { super(priority); }

    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {}
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {}

    public static class MethodHookParam {
        public Object thisObject;
        public Object[] args;
        private Object result;
        private Throwable throwable;
        private boolean hasReturnValue;

        public Object getResult() { return result; }
        public Object getResultOrThrowable() throws Throwable {
            if (throwable != null) throw throwable;
            return result;
        }
        public void setResult(Object result) {
            this.result = result;
            this.hasReturnValue = true;
        }
        public boolean hasReturnValue() { return hasReturnValue; }
        public Throwable getThrowable() { return throwable; }
        public void setThrowable(Throwable throwable) { this.throwable = throwable; }
    }

    public static class Unhook {
        private final Method method;
        private final XC_MethodHook callback;
        public Unhook(Method method, XC_MethodHook callback) {
            this.method = method;
            this.callback = callback;
        }
        public Method getHookedMethod() { return method; }
        public XC_MethodHook getCallback() { return callback; }
        public void unhook() {}
    }
}
