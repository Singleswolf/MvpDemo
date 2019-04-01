package com.zy.mvp.manager;


import com.zy.mvp.utils.Logger;

/**
 * 常量定义
 */
public abstract class SettingManager {
    public static final String TAG = "Logger";//Logger打印Tag
    public static final int LOG_LEVEL = Logger.LEVEL_ALL;//Logger打印调试开关
    public static final String SD_LOG_DIR = "";//Logger保存位置
    public static final boolean SAVE_TO_SD = false;//是否保存log
}

