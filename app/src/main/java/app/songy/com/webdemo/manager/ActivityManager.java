package app.songy.com.webdemo.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Created by song on 2018/8/16.
 * email：bjay20080613@qq.com
 */
public class ActivityManager {
    private static final String TAG = "ActivityManager";
    private static final List<Activity> ACTIVITIES = Collections.synchronizedList(new ArrayList<Activity>());

    public static void create(Activity activity) {
        ACTIVITIES.add(activity);
    }

    public static void destroy(Activity activity) {
        ACTIVITIES.remove(activity);
    }

    public static void resume(Activity activity) {

    }

    public static void pause(Activity activity) {

    }

    public static void finishAll() {
        for (Activity activity : ACTIVITIES) {
            activity.finish();
        }
    }
    public static void finishOther(Activity currentActivity) {
        for (Activity activity : ACTIVITIES) {
            if (activity != currentActivity) {
                activity.finish();
            }
        }
    }

    public static Activity last() {
        int size = ACTIVITIES.size();
        if (size >= 1) {
            return ACTIVITIES.get(size - 1);
        }
        return null;
    }

    public static int size() {
        return ACTIVITIES.size();
    }

    public static List<Activity> getActivities(){
        return ACTIVITIES;
    }
}
