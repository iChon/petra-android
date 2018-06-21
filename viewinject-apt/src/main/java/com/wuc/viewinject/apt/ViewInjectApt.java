package com.wuc.viewinject.apt;

import android.app.Activity;

/**
 * @author wuc
 * @date 2018/6/20
 */
public class ViewInjectApt {

    public static <T extends Activity> void inject(T activity) {
        String className = activity.getClass().getName();
        try {
            Class<?> targetClass = Class.forName(className + "_Target");
            Target<T> target = (Target<T>) targetClass.newInstance();
            target.inject(activity);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject for " + className, e);
        }
    }

}
