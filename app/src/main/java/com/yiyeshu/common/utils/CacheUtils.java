package com.yiyeshu.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/23.
 */
public class CacheUtils {
    //缓存文件名称
    private static final String spName="sp_file";


    //缓存boolean值
    public static boolean getBoolean(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);

    }

    //取出boolean值
    public static void putBoolean(Context context, String key, boolean b) {
        SharedPreferences sp=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,b).commit();
    }


    /**
     * 缓存文本数据
     * @param context
     * @param key
     * @param result
     */
    public static void putString(Context context, String key, String result) {
        SharedPreferences sp=context.getSharedPreferences(spName,Context.MODE_PRIVATE);
        sp.edit().putString(key,result).commit();
    }


    /**
     * 取出缓存文本
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        String result = "";
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        result= sp.getString(key, "");
        return result;
    }

    /**
     * 获取sp对象
     * @param context
     * @return
     */
    public static SharedPreferences getSp(Context context){
        return context.getSharedPreferences(spName,Context.MODE_PRIVATE);
    }
}
