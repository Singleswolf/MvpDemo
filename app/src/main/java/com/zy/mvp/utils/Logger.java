package com.zy.mvp.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 日志的功能操作类 可将日志保存至SD卡
 */
public class Logger {

    /**
     * 是否显示json格式的日志
     * 1.true,json字符串打印成json格式的日志
     * 2.false,打印普通字符串
     */
    public static final boolean isShowJsonLog = false;
    public static final int JSON_INDENT = 4;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_NONE = 3;
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVEL_ASSERT = 6;
    //日志打印时间Format
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat LOG_FMT = new SimpleDateFormat("yyyy-MM-dd");
    //DEBUG级别开关
    public static int LOG_LEVEL = LEVEL_ALL;
    //是否保存至SD卡
    private static boolean SAVE_TO_SD = false;
    //保存LOG日志的目录
    private static String LOG_DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    //过滤日志
    private static String TAG = "XLogger";
    private static Object mutexPrintJson = new Object();

    /**
     * 用于打印错误级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    public static void e(String tag, String strMsg) {
        eLog(LEVEL_ERROR, tag, strMsg);
    }

    public static void e(String strMsg) {
        e(TAG, strMsg);
    }

    public static void e(String msg, Throwable e) {
        e(msg + e.getMessage());
    }

    public static void e(String tag, String msg, Throwable e) {
        e(tag, msg + e.getMessage());
    }

    public static void e(String tag, String logFormat, Object... params) {
        eLog(LEVEL_ERROR, tag, logFormat, params);
    }

    public static void e(String logFormat, Object... params) {
        e(TAG, logFormat, params);
    }

    public static void e(Object object) {
        e(toString(object));
    }

    /**
     * 用于打印描述级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    public static void d(String tag, String strMsg) {
        eLog(LEVEL_DEBUG, tag, strMsg);
    }

    public static void d(String strMsg) {
        d(TAG, strMsg);
    }

    public static void d(String msg, Throwable e) {
        d(msg + e.getMessage());
    }

    public static void d(String tag, String msg, Throwable e) {
        d(tag, msg + e.getMessage());
    }

    public static void d(String tag, String logFormat, Object... params) {
        eLog(LEVEL_DEBUG, tag, logFormat, params);
    }

    public static void d(String logFormat, Object... params) {
        d(TAG, logFormat, params);
    }

    /**
     * 用于打印info级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    public static void i(String tag, String strMsg) {
        eLog(LEVEL_INFO, tag, strMsg);
    }

    public static void i(String strMsg) {
        i(TAG, strMsg);
    }

    public static void i(String tag, String logFormat, Object... params) {
        eLog(LEVEL_INFO, tag, logFormat, params);
    }

    public static void i(String logFormat, Object... params) {
        i(TAG, logFormat, params);
    }

    /**
     * 用于打印v级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    public static void v(String tag, String strMsg) {
        eLog(LEVEL_VERBOSE, tag, strMsg);
    }

    public static void v(String strMsg) {
        v(TAG, strMsg);
    }

    public static void v(String tag, String logFormat, Object... params) {
        eLog(LEVEL_VERBOSE, tag, logFormat, params);
    }

    public static void v(String logFormat, Object... params) {
        v(TAG, logFormat, params);
    }

    /**
     * 用于打印w级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    public static void w(String tag, String strMsg) {
        eLog(LEVEL_WARN, tag, strMsg);
    }

    public static void w(String strMsg) {
        w(TAG, strMsg);
    }

    public static void w(String tag, String logFormat, Object... params) {
        eLog(LEVEL_WARN, tag, logFormat, params);
    }

    public static void w(String logFormat, Object... params) {
        w(TAG, logFormat, params);
    }

    /**
     * 将日志信息保存至SD卡
     *
     * @param tag    LOG TAG
     * @param strMsg 保存的打印信息
     */
    public static void storeLog(String tag, String strMsg) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            synchronized (LOG_FMT) {
                try {
                    String logname = LOG_FMT.format(new Date(System.currentTimeMillis())) + ".txt";
                    File parent = new File(LOG_DIR_PATH);
                    if (!(parent.exists() && parent.isDirectory())) {
                        parent.mkdirs();
                    }
                    File file = new File(parent, logname);
                    // 输出
                    FileOutputStream fos = new FileOutputStream(file, true);
                    PrintWriter out = new PrintWriter(fos);

                    out.println(fmt.format(System.currentTimeMillis()) + "  >>" + tag + "<<  " + strMsg + '\r');
                    out.flush();
                    out.close();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取DEBUG状态
     *
     * @return
     */
    public static void init(String tag, int level, boolean isSaveLog, String logDirPath) {
        TAG = tag;
        LOG_LEVEL = level;
        SAVE_TO_SD = isSaveLog;
        LOG_DIR_PATH = logDirPath;
    }

    /**
     * log太长，显示不全，拼接输出
     *
     * @param level
     * @param tag
     * @param text
     * @author qinbaowei
     */
    public static void log(int level, String tag, String text) {
        final int PART_LEN = 3000;
        do {
            int clipLen = text.length() > PART_LEN ? PART_LEN : text.length();
            String clipText = text.substring(0, clipLen);
            text = clipText.length() == text.length() ? "" : text.substring(clipLen);
            switch (level) {
                case LEVEL_INFO:
                    Log.i(tag, clipText);
                    break;
                case LEVEL_DEBUG:
                    Log.d(tag, clipText);
                    break;
                case LEVEL_WARN:
                    Log.w(tag, clipText);
                    break;
                case LEVEL_VERBOSE:
                    Log.v(tag, clipText);
                    break;
                case LEVEL_ERROR:
                    Log.e(tag, clipText);
                    break;
                case LEVEL_ASSERT:
                    Log.wtf(tag, clipText);
                    break;
                default:
                    break;
            }
        } while (text.length() > 0);
    }

    private static void eLog(int level, String tag, String logFormat, Object... logParams) {
        String msg = null;
        if (level >= LOG_LEVEL) {
            msg = (logParams != null && logParams.length > 0) ? String.format(logFormat, logParams) : logFormat;
            log(level, tag, "----" + msg);
        }
        if (SAVE_TO_SD) {
            if (msg == null) {
                msg = (logParams != null && logParams.length > 0) ? String.format(logFormat, logParams) : logFormat;
            }
            storeLog(tag, msg);
        }
    }

    public static void printJson(final String tag, final String msg, final String header) {
        if (!(LEVEL_DEBUG >= LOG_LEVEL)) {
            return;
        }

        if (!isShowJsonLog) {
            e(tag, "http--responseBody:" + msg);
            return;
        }

        new Thread() {
            @Override
            public void run() {
                synchronized (mutexPrintJson) {

                    String message;

                    if (msg != null) {
                        try {
                            if (msg.startsWith("{")) {
                                JSONObject jsonObject = new JSONObject(msg);
                                message = jsonObject.toString(JSON_INDENT);
                            } else if (msg.startsWith("[")) {
                                JSONArray jsonArray = new JSONArray(msg);
                                message = jsonArray.toString(JSON_INDENT);
                            } else {
                                message = msg;
                            }
                        } catch (JSONException e) {
                            message = msg;
                        }
                    } else {
                        message = "null";
                    }

                    line(true);
                    message = header + LINE_SEPARATOR + message;
                    String[] lines = message.split(LINE_SEPARATOR);
                    StringBuffer linesBuffer = new StringBuffer();
                    linesBuffer.append(LINE_SEPARATOR);
                    for (String line : lines) {
                        l(LEVEL_VERBOSE, "║ " + line);
                    }
                    line(false);
                }
            }
        }.start();
    }

    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    public static void line(boolean isTop) {
        if (isTop) {
            l(LEVEL_VERBOSE, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            l(LEVEL_VERBOSE, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    /**
     * @param depth 2,the method it self;3,the method who call this method
     * @return filename + method name + line number
     */
    private static String getFileNameMethodLineNumber(int depth) {
        String info = new String("");
        try {
            StackTraceElement e = Thread.currentThread().getStackTrace()[depth];
            info = String.format("[(%1$s:%2$d)#%3$s]", e.getFileName(), e.getLineNumber(), e.getMethodName());
        } catch (Exception e) {
            Log.e("log", "get stack trace element failed!!!");
        }
        return info;
    }

    public static void lv(String logFormat, Object... logParam) {
        l(LEVEL_VERBOSE, logFormat, logParam);
    }

    public static void ld(String logFormat, Object... logParam) {
        l(LEVEL_DEBUG, logFormat, logParam);
    }

    public static void li(String logFormat, Object... logParam) {
        l(LEVEL_INFO, logFormat, logParam);
    }

    public static void lw(String logFormat, Object... logParam) {
        l(LEVEL_WARN, logFormat, logParam);
    }

    public static void lw(Throwable e) {
        if (null != e) {
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message)) {
                l(LEVEL_WARN, message);
            }
        }
    }

    public static void le(String logFormat, Object... logParam) {
        l(LEVEL_ERROR, logFormat, logParam);
    }

    public static void le(Throwable e) {
        if (null != e) {
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message)) {
                l(LEVEL_ERROR, message);
            }
        }
    }

    private static void l(int level, String logFormat, Object... logParam) {
        try {
            if ((level >= LOG_LEVEL) || SAVE_TO_SD) {
                String log = String.format(logFormat, logParam);
                String[] logs = createLog(log);
                if (level >= LOG_LEVEL) {
                    log(level, logs[0], logs[1]);
                }
                if (SAVE_TO_SD) {
                    storeLog(logs[0], logs[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] createLog(String log) {
        if (null == log) {
            log = new String("");
        }
        String tag = getFileNameMethodLineNumber(6);
        if (null == tag) {
            tag = new String("");
        } else {
            tag = "[" + TAG + "]" + tag;
        }
        return new String[]{tag, log};
    }

    private static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }
}
