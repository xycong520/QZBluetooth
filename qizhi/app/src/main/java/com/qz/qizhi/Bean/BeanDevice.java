package com.qz.qizhi.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/3/27.
 */
@Table(name = "Device")
public class BeanDevice {
    @Column(name = "id", isId = true ,autoGen = true)
    private int id;
    @Column(name = "deviceName")
    String deviceName;
    @Column(name = "status")
    String status;
    @Column(name = "macAddress")
    String macAddress;
    @Column(name = "type")
    int type;

    List<DeviceFunction> functions;

    public BeanDevice() {
        functions = new ArrayList<>();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DeviceFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<DeviceFunction> functions) {
        this.functions = functions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DeviceFunction getFunction() {
        return new DeviceFunction();
    }
}
