package com.dxytech.demo2;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/19.
 */
public class Logout_Manager extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static Logout_Manager instance;

    private Logout_Manager()
    {
    }
    //单例模式中获取唯一的MyApplication实例
    public static Logout_Manager getInstance()
    {
        if(null == instance)
        {
            instance = new Logout_Manager();
        }
        return instance;
    }
    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    //遍历所有Activity并finish
    public void exit()
    {
        for(Activity activity:activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }
}
