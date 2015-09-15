package com.zhou.appinterface.context;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by zhou on 15/9/15.
 */
public class ActivityStack {

    private Stack<Activity> stack;

    private static ActivityStack activityStack = null;

    public static ActivityStack getInstance() {
        if (activityStack == null) {
            activityStack = new ActivityStack();
        }
        return activityStack;
    }

    private ActivityStack() {
        stack = new Stack<>();
    }

    public void add(Activity activity) {
        stack.push(activity);
    }

    public void remove(Activity activity) {
        stack.remove(activity);
    }

    public Activity pop() {
        return stack.pop();
    }

    public void closeAll() {
        while (!stack.isEmpty()) {
            stack.pop().finish();
        }
    }

    public void closeOthers() {
        if (!stack.isEmpty()) {
            Activity activity = stack.pop();
            closeAll();
            stack.push(activity);
        }
    }

    public Stack<Activity> getActivityStack() {
        return stack;
    }
}
