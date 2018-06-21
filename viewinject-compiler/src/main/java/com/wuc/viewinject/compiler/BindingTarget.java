package com.wuc.viewinject.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.wuc.viewinject.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * @author wuc
 * @date 2018/6/20
 */
public class BindingTarget {

    private final Element element;
    private final Elements elementUtils;
    private final List<ViewInjectInfo> viewInjectInfo;

    public BindingTarget(Elements elementUtils, Element element) {
        this.element = element;
        this.elementUtils = elementUtils;
        viewInjectInfo = new ArrayList<>();
    }

    public void addViewInjectInfo(ViewInjectInfo i) {
        viewInjectInfo.add(i);
    }

    public JavaFile generateFile() {
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(element.asType()), "activity", Modifier.FINAL);

        ContentView contentView = element.getAnnotation(ContentView.class);
        if (contentView != null && contentView.value() != -1) {
            // setContentView()
            injectMethodBuilder.addStatement("activity.setContentView($L)", contentView.value());
        }

        // findViewById()
        for (ViewInjectInfo info : viewInjectInfo) {
            injectMethodBuilder.addStatement("activity.$N = ($T)(activity.findViewById($L))",
                    info.getFieldName(), ClassName.get(info.getFieldType()), info.getResId());
        }

        TypeSpec targetClass = TypeSpec.classBuilder(element.getSimpleName() + "_Target")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get("com.wuc.viewinject.apt", "Target"),
                        TypeName.get(element.asType())
                ))
                .addMethod(injectMethodBuilder.build())
                .build();

        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();

        return JavaFile.builder(packageName, targetClass).build();
    }

}
