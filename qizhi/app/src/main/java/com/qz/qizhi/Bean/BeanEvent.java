package com.qz.qizhi.Bean;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/3/29.
 */
public class BeanEvent {

    boolean isCheck;
    String date;
    int unReadCount;
    List<SparseArray> events;
    public BeanEvent(){
        events = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUnReadCount() {
        return unReadCount;
    }
    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public List<SparseArray> getEvents() {
        return events;
    }
    public void setEvents(List<SparseArray> events) {
        this.events = events;
    }
    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
