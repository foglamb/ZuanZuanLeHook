package de.robv.android.xposed;
import java.lang.reflect.*;
public class XposedHelpers {
    public static Class<?> findClass(String className, ClassLoader classLoader) { return null; }
    public static void findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {}
    public static void findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {}
    public static Object callMethod(Object obj, String methodName, Object... args) { return null; }
    public static Object getObjectField(Object obj, String fieldName) { return null; }
    public static void setObjectField(Object obj, String fieldName, Object value) {}
    public static Field findField(Class<?> clazz, String fieldName) { return null; }
    public static Field findField(Class<?> clazz, Field field) { return null; }
    public static Object getStaticObjectField(Class<?> clazz, String fieldName) { return null; }
    public static void setStaticObjectField(Class<?> clazz, String fieldName, Object value) {}
    public static Class<?> findClassIfExists(String className, ClassLoader classLoader) { return null; }
}
