package com.winterchen.quartz.util;

/**
 * Created by zy on 2020/2/11.
 */
public class DateUtil {

    /**
     * 得到当前时间戳
     * @return
     */
    public static Long getCurrentTimeStamp() {
        long timeMillis = System.currentTimeMillis();
        return timeMillis;
    }

}
