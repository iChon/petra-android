package com.wuc.viewinject.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.wuc.viewinject.annotation.ContentView;
import com.wuc.viewinject.annotation.OnClick;
import com.wuc.viewinject.annotation.ViewInject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @author wuc
 * @date 2018/6/20
 */
@AutoService(Processor.class)
public class ViewInjectProcesser extends AbstractProcessor {

    private Filer filer; //文件相关的辅助类
    private Elements elementUtils; // 元素操作相关的辅助类
    private Messager messager; // 日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
    }

    /**
     * 列出要处理的注解
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ViewInject.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        types.add(ContentView.class.getCanonicalName());
        return types;
    }

    /**
     * 指定java版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Map<TypeElement, BindingTarget> bindingMap;
        try {
            bindingMap = findTarget(env);
        } catch (Exception e) {
            // print message, compile-time can not allowed use log
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            return true;
        }

        if (bindingMap != null) {
            for (Map.Entry<TypeElement, BindingTarget> entry : bindingMap.entrySet()) {
                TypeElement typeElement = entry.getKey();
                BindingTarget target = entry.getValue();

                try {
                    JavaFile javaFile = target.generateFile();
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,
                            String.format("Unable to write binding for type %s: %s", typeElement, e.getMessage()));
                }
            }
        }
        return true;
    }

    private Map<TypeElement, BindingTarget> findTarget(RoundEnvironment env) {
        Map<TypeElement, BindingTarget> bindingMap = new LinkedHashMap<>();
        for (Element element : env.getElementsAnnotatedWith(ContentView.class)) {
            TypeElement classElement = (TypeElement) element;
            BindingTarget bindingTarget = bindingMap.get(classElement);
            if (bindingTarget == null) {
                bindingTarget = new BindingTarget(elementUtils, classElement);
                bindingMap.put(classElement, bindingTarget);
            }
        }
        for (Element element : env.getElementsAnnotatedWith(ViewInject.class)) {
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            BindingTarget bindingTarget = bindingMap.get(classElement);
            if (bindingTarget == null) {
                bindingTarget = new BindingTarget(elementUtils, classElement);
                bindingMap.put(classElement, bindingTarget);
            }
            ViewInjectInfo viewInjectInfo = new ViewInjectInfo(element);
            bindingTarget.addViewInjectInfo(viewInjectInfo);
        }
        return bindingMap;
    }


}
