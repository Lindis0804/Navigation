package com.ldh.heimerdingerslab;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.app.Fragment;
import android.view.View;

public abstract class OnClickFragments {
    public static class FragmentHolder {
        Fragment fragment;
        public FragmentHolder(Fragment fragment) {
            this.fragment = fragment;
        }
    }
    public static Fragment getTagFragment(View view) {
        for (View v = view; v != null; v = (v.getParent() instanceof View) ? (View)v.getParent() : null) {
            Object tag = v.getTag();
            if (tag != null && tag instanceof FragmentHolder) {
                return ((FragmentHolder)tag).fragment;
            }
        }
        return null;
    }
    public static String getCallingMethodName(int callsAbove) {
        Exception e = new Exception();
        e.fillInStackTrace();
        String methodName = e.getStackTrace()[callsAbove+1].getMethodName();
        return methodName;
    }
    public static void invokeFragmentButtonHandler(View v, int callsAbove) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        String methodName = getCallingMethodName(callsAbove+1);
        Fragment f = OnClickFragments.getTagFragment(v);
        Method m = f.getClass().getMethod(methodName, new Class[] { View.class });
        m.invoke(f, v);
    }
    public static void invokeFragmentButtonHandler(View v) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        invokeFragmentButtonHandler(v,1);
    }
    public static void invokeFragmentButtonHandlerNoExc(View v) {
        try {
            invokeFragmentButtonHandler(v,1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static void registerTagFragment(View rootView, Fragment fragment) {
        rootView.setTag(new FragmentHolder(fragment));
    }
}