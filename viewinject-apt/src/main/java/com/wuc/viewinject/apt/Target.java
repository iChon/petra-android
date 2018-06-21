package com.wuc.viewinject.apt;

import android.app.Activity;

/**
 * @author wuc
 * @date 2018/6/20
 */
public interface Target<T extends Activity> {

    void inject(final T activity);

}
