package com.wuc.viewinject.compiler;

import com.wuc.viewinject.annotation.ViewInject;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author wuc
 * @date 2018/6/20
 */
public class ViewInjectInfo {

    private final VariableElement element;
    private final int resId;

    public ViewInjectInfo(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("@%s must apply to Field",
                    ViewInject.class.getSimpleName()));
        }
        this.element = (VariableElement) element;
        ViewInject viewInject = element.getAnnotation(ViewInject.class);
        resId = viewInject.value();
        if (resId == -1) {
            throw new IllegalArgumentException(String.format("field %s used @%s value() invalid",
                    element.getSimpleName(), ViewInject.class.getSimpleName()));
        }
    }

    public int getResId() {
        return resId;
    }

    public Name getFieldName() {
        return element.getSimpleName();
    }

    public TypeMirror getFieldType() {
        return element.asType();
    }

}
