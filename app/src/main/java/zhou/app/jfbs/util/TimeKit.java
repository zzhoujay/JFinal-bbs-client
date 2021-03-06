package zhou.app.jfbs.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhou on 15/9/7.
 */
public class TimeKit {

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd hh:mm");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy年MM月dd");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat FORMAT=new SimpleDateFormat("yyyy-MM-dd");

    public static final long oneMinute = 60 * 1000;
    public static final long oneHour = oneMinute * 60;
    public static final long oneDay = 24 * oneHour;

    public static String format(Date date) {
        return format.format(date);
    }

    public static String formatSimple(Date date) {
        Date now = new Date();
        long offset = now.getTime() - date.getTime();
        if (offset < oneMinute) {
            return "刚刚";
        } else if (offset < oneHour) {
            int num = (int) (offset / oneMinute);
            return String.format("%d分钟之前", num);
        } else if (offset < oneDay) {
            int num = (int) (offset / oneHour);
            return String.format("%d小时之前", num);
        } else if (offset < 2 * oneDay) {
            return "昨天";
        } else if (offset < 3 * oneDay) {
            return "前天";
        } else {
            return simpleFormat.format(date);
        }
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar da = Calendar.getInstance();
        da.setTime(date);
        return now.get(Calendar.YEAR) == da.get(Calendar.YEAR) && now.get(Calendar.MONTH) == da.get(Calendar.MONTH) &&
                now.get(Calendar.DAY_OF_MONTH) == da.get(Calendar.DAY_OF_MONTH);
    }
}
