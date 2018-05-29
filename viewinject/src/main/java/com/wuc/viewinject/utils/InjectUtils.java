package com.wuc.viewinject.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.wuc.viewinject.annotation.ContentView;
import com.wuc.viewinject.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wuc
 * @date 2018/5/29
 */
public class InjectUtils {

    public static void inject(Activity act) {
        Class<? extends Activity> clazz = act.getClass();

        injectContentView(act, clazz);
        injectViews(act, clazz);
    }

    private static void injectViews(Activity act, Class<? extends Activity> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length <= 0) {
            return;
        }
        for (Field field : fields) {
            ViewInject inject = field.getAnnotation(ViewInject.class);
            if (inject == null) {
                continue;
            }

            View view = act.findViewById(inject.value());
            field.setAccessible(true);
            try {
                field.set(act, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectContentView(Activity act, Class<? extends Activity> clazz) {
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            try {
                Method setContentViewMethod = clazz.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(act, contentView.value());
            } catch (Throwable e) {
                Log.d(InjectUtils.class.getName(), e.getMessage());
            }
        }
    }

}
