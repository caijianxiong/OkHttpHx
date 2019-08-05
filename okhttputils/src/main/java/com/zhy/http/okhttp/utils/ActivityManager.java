package com.zhy.http.okhttp.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * @author caicai
 * @create 2019/7/26
 * @Describe
 */
public class ActivityManager {
    private static volatile ActivityManager instance;
    private Stack<Activity> activities = new Stack<>();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {//确保线程同步，上一个线程创建了，，就没有必要再创建一个实例
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }


    public void addActivity(Activity activity) {
        activities.push(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }


    public void finshActivity(Class<?>... aClass) {//无限参数
        for (Class<?> aClas : aClass) {
            int activitySize = activities.size();
            for (int K = 0; K < activitySize; K++) {
                Activity activity = activities.get(K);
                if (activity.getClass().equals(aClas)) {
                    activity.finish();
                    activities.remove(activity);
                }
            }
        }
    }


    public void killProcess() {
        int nCount = activities.size();
        for (int i = nCount - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            activity.finish();
        }
        activities.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
