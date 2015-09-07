package zhou.app.jfbs.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhou on 15/9/7.
 */
public class TimeKit {

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd hh:mm");

    public static String format(Date date){
        return format.format(date);
    }
}
