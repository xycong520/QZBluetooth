package com.qz.qizhi.Bean;

import android.view.View;

/**
 * Created by Administrator on 2016/4/7.
 */
public class BeanNew {
    int newType;//1.存谷信息，2出谷信息，3温度，4湿度，5能耗
    String typeName;
    boolean isShow ;
    View view;
    public BeanNew(int newType, String typeName) {
        this.newType = newType;
        this.typeName = typeName;
    }

    public int getNewType() {
        return newType;
    }

    public void setNewType(int newType) {
        this.newType = newType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
