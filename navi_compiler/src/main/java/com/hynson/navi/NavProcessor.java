package com.hynson.navi;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hynson.navi.annotation.ActivityDestination;
import com.hynson.navi.annotation.FragmentDestination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import static com.hynson.navi.Consts.KEY_MODULE_NAME;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.hynson.navi.annotation.ActivityDestination","com.hynson.navi.annotation.FragmentDestination"})
@SupportedOptions(KEY_MODULE_NAME)
public class NavProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            this.add(KEY_MODULE_NAME);
        }};
    }
    private Messager messager;
    private Filer filer;
    private String outFileName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //日志工具
        messager = processingEnv.getMessager();
        //文件处理工具
        filer = processingEnv.getFiler();
        //获取gradle中配置的内容作为生成文件的名字

        Map<String, String> options = processingEnv.getOptions();
        outFileName = processingEnv.getOptions().get(KEY_MODULE_NAME);
        if (!options.isEmpty()) {
            outFileName = options.get(KEY_MODULE_NAME) +"_nav.json";
        }

        messager.printMessage(Diagnostic.Kind.NOTE,"moduleName:"+processingEnv.getSourceVersion());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //拿到带有这两个注解的类的集合
        Set<? extends Element> fragmentElement = roundEnv.getElementsAnnotatedWith(FragmentDestination.class);
        Set<? extends Element> activityElement = roundEnv.getElementsAnnotatedWith(ActivityDestination.class);
        messager.printMessage(Diagnostic.Kind.NOTE,"debug:1");

        if(!fragmentElement.isEmpty()||!activityElement.isEmpty()){
            Map<String, JsonObject> destMap = new HashMap<>();
            handleDestination(fragmentElement,FragmentDestination.class,destMap);
            handleDestination(activityElement,ActivityDestination.class,destMap);
            messager.printMessage(Diagnostic.Kind.NOTE,"map:"+destMap.size());

            FileOutputStream fos = null;
            OutputStreamWriter writer = null;
            //将map转换为json文件，保存到app/src/asset中
            messager.printMessage(Diagnostic.Kind.NOTE,"debug:2");
            try {
                //filer.createResource方法用来生成源文件
                //StandardLocation.CLASS_OUTPUT java文件生成class文件的位置，/build/intermediates/javac/debug/classes/目录下
                //SOURCE_OUTPUT MyHome\setting\build\intermediates\library_assets\debug\out
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "navi", outFileName);
                String resourcePath = resource.toUri().getPath();
                messager.printMessage(Diagnostic.Kind.NOTE,"resourcePath:"+resourcePath);

                String appPath = resourcePath.substring(0,resourcePath.indexOf("build"));
                String assetPath = appPath + "src/main/assets";
                messager.printMessage(Diagnostic.Kind.NOTE,"assetPath:"+assetPath);

                File assetDir = new File(assetPath);
                if(!assetDir.exists()){
                    assetDir.mkdir();
                }
                File assetFile = new File(assetDir,outFileName);
                if(assetFile.exists()){
                    assetFile.delete();
                }
                assetFile.createNewFile();
                Gson GSON = new GsonBuilder().create();

                String content = GSON.toJson(destMap);

                fos = new FileOutputStream(assetFile);
                writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    private void handleDestination(Set<? extends Element> elements, Class<? extends Annotation> desAnnotationClazz,
                                   Map<String, JsonObject> destMap) {

        for (Element element : elements) {
            //TypeElement代表类或者接口，因为定义的注解是写在类上面的，所以可以直接转换成TypeElement
            TypeElement typeElement = (TypeElement) element;
            //获取全类名
            String className = typeElement.getQualifiedName().toString();
            int id = Math.abs(className.hashCode());
            String pageUrl = null;
            boolean needLogin = false;
            boolean asStarter = false;
            boolean isFragment = true;
//            messager.printMessage(Diagnostic.Kind.NOTE,"className:"+className);
            Annotation annotation = element.getAnnotation(desAnnotationClazz);
            //根据不同的注解获取注解的参数
            if(annotation instanceof FragmentDestination){
                FragmentDestination destination =  (FragmentDestination) annotation;
                pageUrl = destination.pageUrl();
                needLogin = destination.needLogin();
                asStarter = destination.asStarter();
                isFragment = true;
            }else if(annotation instanceof ActivityDestination){
                ActivityDestination destination =  (ActivityDestination) annotation;
                pageUrl = destination.pageUrl();
                needLogin = destination.needLogin();
                asStarter = destination.asStarter();
                isFragment = false;
            }
            //将参数封装成JsonObject后放到map中保存
            if(destMap.containsKey(pageUrl)){
                messager.printMessage(Diagnostic.Kind.ERROR,"不允许使用相同的pagUrl:"+className);
            }else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id",id);
                jsonObject.addProperty("className",className);
                jsonObject.addProperty("pageUrl",pageUrl);
                jsonObject.addProperty("needLogin",needLogin);
                jsonObject.addProperty("asStarter",asStarter);
                jsonObject.addProperty("isFragment",isFragment);
                destMap.put(pageUrl,jsonObject);
            }
        }
    }
}