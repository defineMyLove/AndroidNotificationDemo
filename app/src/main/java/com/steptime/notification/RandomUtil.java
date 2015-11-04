package com.steptime.notification;

import java.util.Random;

/**
 * Created by zxl on 2015/3/30.
 */
public class RandomUtil {
    /**
     * 获取六位随机数
     * @return
     */
    public static String getString6FromRandom(){
        Random r = new Random();
        int xx = r.nextInt(1000000);
        while(xx<100000){
            xx = r.nextInt(1000000);
        }
        return String.valueOf(xx);
    }
    /**
     * 获取六位随机数
     * @return
     */
    public static Integer getNumber6FromRandom(){
        Random r = new Random();
        int xx = r.nextInt(1000000);
        while(xx<100000){
            xx = r.nextInt(1000000);
        }
        return xx;
    }
}
