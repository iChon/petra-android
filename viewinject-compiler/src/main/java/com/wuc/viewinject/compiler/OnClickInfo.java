package com.wuc.viewinject.compiler;

import com.wuc.viewinject.annotation.OnClick;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;


/**
 * @author wuc
 * @date 2018/6/22
 */
public class OnClickInfo {

    private final ExecutableElement element;
    private final int resId;

    public OnClickInfo(Element element) {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(String.format("@%s must apply to Method",
                    OnClick.class.getSimpleName()));
        }
        this.element = (ExecutableElement) element;
        OnClick onClick = element.getAnnotation(OnClick.class);
        resId = onClick.value();
        if (resId == -1) {
            throw new IllegalArgumentException(String.format("method %s used @%s value() invalid",
                    element.getSimpleName(), OnClick.class.getSimpleName()));
        }
    }

    public Name getMethodName() {
        return element.getSimpleName();
    }

    public int getResId() {
        return resId;
    }

}
