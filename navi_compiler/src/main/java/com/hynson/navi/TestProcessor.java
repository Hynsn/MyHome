package com.hynson.navi;

import static com.hynson.navi.Consts.KEY_MODULE_NAME;
import static com.hynson.navi.Consts.ROOT_PAKCAGE;

import com.google.auto.service.AutoService;
import com.hynson.navi.bean.Destination1;
import com.hynson.navi.annotation.NavDestination;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.hynson.navi.annotation.NavDestination"})
@SupportedOptions(KEY_MODULE_NAME)
public class TestProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;
    // 操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementUtil;
    // type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;

    private String moduleName;
    private Map<String, List<Destination1>> destMap = new HashMap<>(); // 目前是一个

    // Map<"personal", "ARouter$$Path$$personal.class">
    private Map<String, String> mAllGroupMap = new HashMap<>();

    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            this.add(KEY_MODULE_NAME);
        }};
    }

    @Override
    public synchronized void init(ProcessingEnvironment processor) {
        super.init(processor);

        elementUtil = processor.getElementUtils();
        typeTool = processor.getTypeUtils();
        messager = processor.getMessager();
        filer = processor.getFiler();
        moduleName = processor.getOptions().get(KEY_MODULE_NAME);
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {

        Set<? extends Element> annotationElement = roundEnv.getElementsAnnotatedWith(NavDestination.class);
        if (annotationElement != null && annotationElement.size() > 0) {
            messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath:" + roundEnv.getClass());
            /*MethodSpec constructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    *//*.addStatement("$S()",Consts.ADESTINATION_GETDESTINATION)*//* // 构造中添加代码
                    .build();
            MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.ADESTINATION_GETDESTINATION)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class) //重载
                    .addParameter(String.class, "args") // 参数
                    .returns(void.class);*/

            TypeName methodReturn = ParameterizedTypeName.get(
                    ClassName.get(Map.class),         // Map
                    ClassName.get(String.class),      // Map<String,
                    ClassName.get(Destination1.class)   // Map<String, RouterBean>
            );
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Consts.ADESTINATION_GETDESTINATION)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(methodReturn);
            methodBuilder.addStatement("$T<$T, $T> $N = new $T<>()",
                    ClassName.get(Map.class),           // Map
                    ClassName.get(String.class),        // Map<String,
                    ClassName.get(Destination1.class),    // Map<String, RouterBean>
                    Consts.ADESTINATION_GETDESTINATION_VAR1,          // Map<String, RouterBean> pathMap
                    ClassName.get(HashMap.class)        // Map<String, RouterBean> pathMap = new HashMap<>();
            );
            TypeVariableName typeV = TypeVariableName.get("Destination");
            TypeVariableName typeK = TypeVariableName.get("String");
            ParameterizedTypeName mListTypeName = ParameterizedTypeName.get(ClassName.get(HashMap.class), typeK, typeV);
            FieldSpec mapField = FieldSpec.builder(mListTypeName, "mMap", Modifier.PUBLIC)
                    .initializer("new $T()", mListTypeName)
                    .build();
            //     public HashMap<String, Destination> mMap = new HashMap();
            TypeMirror activityMirror = elementUtil.getTypeElement(Consts.ACTIVITY_PACKAGE).asType();
            TypeMirror fragmentMirror = elementUtil.getTypeElement(Consts.FRAGMENT_PACKAGE).asType();

            for (Element element : annotationElement) {
                NavDestination destination = element.getAnnotation(NavDestination.class);
                //获取全类名
                String className = ((TypeElement) element).getQualifiedName().toString();
                Destination1.Type type = Destination1.Type.NONE;
                if (typeTool.isSubtype(element.asType(), activityMirror)) {
                    type = Destination1.Type.ACTIVITY;
                } else if (typeTool.isSubtype(element.asType(), fragmentMirror)) {
                    type = Destination1.Type.FRAGMENT;
                }

                if (type != Destination1.Type.NONE) {
                    int id = Math.abs(className.hashCode());
                    Destination1 destBean = new Destination1.Builder()
                            .setId(id)
                            .setElement(element)
                            .setPageUrl(destination.pageUrl())
                            .setClassName(className)
                            .setType(type)
                            .build();
                    //Type type, Element element, String className, int id, String pageUrl
                    methodBuilder.addStatement("$N.put($S, new $T($T.$L,$T.class,$S,$L,$S))",
                            Consts.ADESTINATION_GETDESTINATION_VAR1,
                            destBean.pageUrl,
                            ClassName.get(Destination1.class),
                            ClassName.get(Destination1.Type.class),
                            destBean.type,
                            ClassName.get((TypeElement) destBean.element),
                            destBean.className,
                            destBean.id,
                            destBean.pageUrl
                    );

                    if (checkRouterPath(destBean)) {
                        messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean Check Success:" + destBean.toString());
                        // 赋值 mAllPathMap 集合里面去
                        List<Destination1> routerBeans = destMap.get(destBean.group);

                        // 如果从Map中找不到key为：bean.getGroup()的数据，就新建List集合再添加进Map
                        if (Utils.isEmpty(routerBeans)) { // 仓库一 没有东西
                            routerBeans = new ArrayList<>();
                            routerBeans.add(destBean);
                            destMap.put(destBean.group, routerBeans);// 加入仓库一
                        } else {
                            routerBeans.add(destBean);
                        }
                    } else { // ERROR 编译期发生异常
                        messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
                    }

                }

                /*builder.addStatement("mMap.put($S,new Destination($L,$S,$S,$L,$L))", destination.pageUrl(), id, className, destination.pageUrl(), destination.asStarter(), destination.asStarter());
                //     mMap.put(destination.pageUrl(),new Destination());
                builder.addStatement("$T.out.println($S+$L)", System.class,className , annotationElement.size());*/
            }
            methodBuilder.addStatement("return $N", Consts.ADESTINATION_GETDESTINATION_VAR1);

            TypeElement pathType = elementUtil.getTypeElement(Consts.ADESTINATION_API);
            TypeElement groupType = elementUtil.getTypeElement(Consts.GROUP_API);
            String className = Consts.DEST_FILE_NAME + moduleName;
            TypeSpec clazz = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ClassName.get(pathType)) // 接口
                    .addMethod(methodBuilder.build())
                    /*.addMethod(builder.build())
                    .addMethod(constructor)*/
                    /*.addField(mapField)*/
                    .build();

            JavaFile jFile = JavaFile.builder(ROOT_PAKCAGE, clazz)
                    .build();
            for (Map.Entry<String, List<Destination1>> entry : destMap.entrySet()) {
                mAllGroupMap.put(entry.getKey(), className);
            }
            try {
                jFile.writeTo(filer);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                createGroupFile(groupType, pathType);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "在生成GROUP模板时，异常了 e:" + e.getMessage());
            }
        }

        return true;
    }

    private void createGroupFile(TypeElement groupType, TypeElement pathType) throws IOException {
        // 仓库二 缓存二 判断是否有需要生成的类文件
        if (mAllGroupMap == null || mAllGroupMap.isEmpty()) return;

        // 返回值 这一段 Map<String, Class<? extends ARouterPath>>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),        // Map
                ClassName.get(String.class),    // Map<String,

                // Class<? extends ARouterPath>> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        // ? extends ARouterPath
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))) // ? extends ARouterLoadPath
                // WildcardTypeName.supertypeOf() 做实验 ? super

                // 最终的：Map<String, Class<? extends ARouterPath>>
        );

        // 1.方法 public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder(Consts.GROUP_GETGROUPMAP) // 方法名
                .addAnnotation(Override.class) // 重写注解 @Override
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .returns(methodReturns); // 方法返回值

        // Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        methodBuidler.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),
                // Class<? extends ARouterPath> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))), // ? extends ARouterPath
                Consts.GROUP_GETGROUPMAP_VAR1,
                ClassName.get(HashMap.class));

        //  groupMap.put("personal", ARouter$$Path$$personal.class);
        //	groupMap.put("order", ARouter$$Path$$order.class);
        for (Map.Entry<String, String> entry : mAllGroupMap.entrySet()) {
            methodBuidler.addStatement("$N.put($S, $T.class)",
                    Consts.GROUP_GETGROUPMAP_VAR1, // groupMap.put
                    entry.getKey(), // order, personal ,app
                    ClassName.get(ROOT_PAKCAGE, entry.getValue()));
        }

        // return groupMap;
        methodBuidler.addStatement("return $N", Consts.GROUP_GETGROUPMAP_VAR1);

        // 最终生成的类文件名 ARouter$$Group$$ + personal
        String className = Consts.GROUP_FILE_NAME + moduleName;

        messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由组Group类文件：" +
                ROOT_PAKCAGE + "." + className);

        // 生成类文件：ARouter$$Group$$app
        JavaFile.builder(ROOT_PAKCAGE, // 包名
                TypeSpec.classBuilder(className) // 类名
                        .addSuperinterface(ClassName.get(groupType)) // 实现ARouterLoadGroup接口 implements ARouterGroup
                        .addModifiers(Modifier.PUBLIC) // public修饰符
                        .addMethod(methodBuidler.build()) // 方法的构建（方法参数 + 方法体）
                        .build()) // 类构建完成
                .build() // JavaFile构建完成
                .writeTo(filer); // 文件生成器开始生成类文件
    }
    private final boolean checkRouterPath(Destination1 bean) {
        String group = bean.group; //  同学们，一定要记住： "app"   "order"   "personal"
        String path = bean.pageUrl;   //  同学们，一定要记住： "/app/MainActivity"   "/order/Order_MainActivity"   "/personal/Personal_MainActivity"

        // 校验
        // @ARouter注解中的path值，必须要以 / 开头（模仿阿里Arouter规范）
        if (Utils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的path值，必须要以 / 开头");
            return false;
        }

        // 比如开发者代码为：path = "/MainActivity"，最后一个 / 符号必然在字符串第1位
        if (path.lastIndexOf("/") == 0) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
            return false;
        }

        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app,order,personal 作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        // @ARouter注解中的group有赋值情况
        if (!Utils.isEmpty(group) && !group.equals(moduleName)) {
            // 架构师定义规范，让开发者遵循
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的group值必须和子模块名一致！");
            return false;
        } else {
            bean.group = finalGroup;
        }
        return true;
    }

}
