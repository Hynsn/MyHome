package com.hynson.navi.bean;
import javax.lang.model.element.Element;

public class Destination {
    public enum Type {
        ACTIVITY,
        FRAGMENT,
        NONE
    }
    public Type type;
    public Element element;
    public Class<?> mClass;
    // navigation需要
    public String className;
    public int id;
    public String pageUrl;
    public String group;

    public Destination(Type type, Class<?> clazz, String className, int id, String pageUrl) {
        this.type = type;
        this.mClass = clazz;
        this.className = className;
        this.id = id;
        this.pageUrl = pageUrl;
    }

    private Destination(Builder builder){
        this.type = builder.type;
        this.className = builder.className;
        this.element = builder.element;
        this.id = builder.id;
        this.pageUrl = builder.pageUrl;
    }

    public static class Builder{
        private Type type;
        private Element element;

        private String className;
        private int id;
        private String pageUrl;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
            return this;
        }

        public Destination build(){
            if(pageUrl == null || pageUrl.length() == 0){
                throw new IllegalArgumentException("pageUrl必填，如:/app/login");
            }
            return new Destination(this);
        }
    }

    @Override
    public String toString() {
        return "Destination1{" +
                "type=" + type +
                ", element=" + element +
                ", mClass=" + mClass +
                ", className='" + className + '\'' +
                ", id=" + id +
                ", pageUrl='" + pageUrl + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
