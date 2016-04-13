package com.qz.qizhi.Bean;

/**
 * Created by xycong on 2016/3/28.
 */

public class DeviceFunction {

    String funName;
    int funIcon;
    String funValue1,funValue2;
    int actionType;//操作指令类型，1 出米，2 余米， 3 温度，4 湿度，5 复位，6 监测
    // 0=执行动作0 1=执行动作1  2=获取状态 3=获取参数 4=获取记录  5=设置参数 6=设置时间
//="HEAD0" 》
//="HEAD1" 》
//="HEAD2" 》
//="HEAD3" 》
//="HEAD537-70-1-999    "--温度(2B)-湿度(2B)-种类(1B)-出量(3B)//填充满20个字符
//="HEAD6201602031830   "--年(4B)-月(2B)-日(2B)-时(2B)-分(2B)//填充满20个字符

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public DeviceFunction() {
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public int getFunIcon() {
        return funIcon;
    }

    public void setFunIcon(int funIcon) {
        this.funIcon = funIcon;
    }

    public String getFunValue1() {
        return funValue1;
    }

    public void setFunValue1(String funValue1) {
        this.funValue1 = funValue1;
    }

    public String getFunValue2() {
        return funValue2;
    }

    public void setFunValue2(String funValue2) {
        this.funValue2 = funValue2;
    }
}
