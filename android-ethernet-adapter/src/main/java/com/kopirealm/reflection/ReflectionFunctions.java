package com.kopirealm.reflection;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionFunctions {

    public static Process ExecRuntimeCommand(String command) {
        try {
            return Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            Log.e("execRuntimeCommand", e.getMessage());
        }
        return null;
    }

    public static Process ExecRuntimeSUCommand(String command) {
        try {
            return Runtime.getRuntime().exec(new String[]{"su", "-c", command});
        } catch (IOException e) {
            Log.e("execRuntimeCommand", e.getMessage());
        }
        return null;
    }

    private Context context;

    public static Boolean TRUE = Boolean.TRUE;
    public static Boolean FALSE = Boolean.FALSE;
    public static Class<?> BOOLEAN = Boolean.TYPE;
    public static Class<?> CHAR = char.class;
    public static Class<?> INT = int.class;
    public static Class<?> SHORT = short.class;
    public static Class<?> LONG = long.class;
    public static Class<?> FLOAT = float.class;
    public static Class<?> DOUBLE = double.class;
    public static Class<?> BYTE = byte.class;
    public static Class<?> STRING = String.class;

    public ReflectionFunctions(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    protected Process execRuntimeCommand(String command) {
        return ReflectionFunctions.ExecRuntimeCommand(command);
    }

    protected Process execRuntimeSUCommand(String command) {
        return ReflectionFunctions.ExecRuntimeSUCommand(command);
    }

    protected Class<?> getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    protected Object getNewInstance(Class<?> classType) throws IllegalAccessException, InstantiationException {
        return classType.newInstance();
    }

    protected Object getSystemServiceInstance(String name) {
        return getContext().getSystemService(name);
    }

    protected Method getMethod(String className, String methodName, Class<?>... params) throws ClassNotFoundException, NoSuchMethodException {
        return getMethod(getClass(className), methodName, params);
    }

    protected Method getMethod(Class<?> classType, String methodName, Class<?>... params) throws NoSuchMethodException {
        return classType.getMethod(methodName, params);
    }

    protected Object invokeMethod(Method method, Object classInstance, Object... methodParams) throws InvocationTargetException, IllegalAccessException {
        try {
            return method.invoke(classInstance, methodParams);
        } catch (SecurityException e) {
            new ReflectionException("", e).printStackTrace();
            method.setAccessible(true);
            return invokeMethod(method, classInstance, methodParams);
        }
    }

    private static class ReflectionException extends RuntimeException {
        private ReflectionException(String message, Exception exception) {
            super(message, exception);
        }
    }

}