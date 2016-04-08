package com.qz.qizhi.Bean;

/**
 * Created by Administrator on 2016/4/8.
 */
public class BeanGrain {
    String name;
    String typeName;

    public BeanGrain(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
