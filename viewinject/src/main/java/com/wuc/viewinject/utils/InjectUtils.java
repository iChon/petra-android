package com.wuc.viewinject.utils;

import android.app.Activity;
import android.view.View;

import com.wuc.viewinject.annotation.ContentView;
import com.wuc.viewinject.annotation.OnClick;
import com.wuc.viewinject.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
        injectEvent(act, clazz);
    }

    private static void injectEvent(final Activity act, Class<? extends Activity> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        if (methods == null || methods.length <= 0) {
            return;
        }
        for (final Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick == null) {
                continue;
            }

            View view = act.findViewById(onClick.value());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    method.setAccessible(true);
                    try {
                        method.invoke(act, v);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
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
                e.printStackTrace();
            }
        }
    }

}
