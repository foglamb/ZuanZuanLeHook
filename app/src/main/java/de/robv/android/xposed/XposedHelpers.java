package de.robv.android.xposed;

import java.lang.reflect.*;

public class XposedHelpers {
    public static Class<?> findClass(String className, ClassLoader classLoader) {
        try { return Class.forName(className, false, classLoader); }
        catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }

    public static XC_MethodHook.Unhook findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
        try {
            Class<?> clazz = findClass(className, classLoader);
            return findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static XC_MethodHook.Unhook findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        try {
            Class<?>[] parameterTypes = new Class<?>[parameterTypesAndCallback.length - 1];
            for (int i = 0; i < parameterTypesAndCallback.length - 1; i++) {
                if (parameterTypesAndCallback[i] instanceof Class) {
                    parameterTypes[i] = (Class<?>) parameterTypesAndCallback[i];
                } else if (parameterTypesAndCallback[i] instanceof String) {
                    String typeName = (String) parameterTypesAndCallback[i];
                    if ("boolean".equals(typeName)) parameterTypes[i] = boolean.class;
                    else if ("int".equals(typeName)) parameterTypes[i] = int.class;
                    else if ("long".equals(typeName)) parameterTypes[i] = long.class;
                    else if ("double".equals(typeName)) parameterTypes[i] = double.class;
                    else if ("float".equals(typeName)) parameterTypes[i] = float.class;
                    else if ("void".equals(typeName)) parameterTypes[i] = void.class;
                    else parameterTypes[i] = Class.forName(typeName);
                }
            }
            Method m = clazz.getDeclaredMethod(methodName, parameterTypes);
            XC_MethodHook callback = (XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
            return new XC_MethodHook.Unhook(m, callback);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static Object callMethod(Object obj, String methodName, Object... args) {
        try {
            Class<?>[] paramTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) paramTypes[i] = args[i].getClass();
            Method m = obj.getClass().getMethod(methodName, paramTypes);
            return m.invoke(obj, args);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static Object getObjectField(Object obj, String fieldName) {
        try {
            Field f = findField(obj.getClass(), fieldName);
            return f.get(obj);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static Field findField(Class<?> clazz, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) return findField(clazz.getSuperclass(), fieldName);
            throw new RuntimeException(e);
        }
    }

    public static void setObjectField(Object obj, String fieldName, Object value) {
        try {
            Field f = findField(obj.getClass(), fieldName);
            f.set(obj, value);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static Class<?>[] getClassesAsArray(Class<?>... clazzes) { return clazzes; }
}
