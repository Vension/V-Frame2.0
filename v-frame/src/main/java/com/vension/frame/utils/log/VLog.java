package com.vension.frame.utils.log;

import java.util.List;
import java.util.Map;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:14
 * 描  述：实现与logger类似效果的日志打印工具
 * ========================================================
 */

public final class VLog {

    private static final Printer printer = new LoggerPrinter();

    private VLog() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static VLogConfig init() {
        return printer.init();
    }

    public static String getFormatLog() {
        return printer.getFormatLog();
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void e(String message, Object... args) {
        printer.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    public static void json(String json) {
        printer.json(json);
    }

    public static void xml(String xml) {
        printer.xml(xml);
    }

    public static void map(Map map) {
        printer.map(map);
    }

    public static void list(List list) {
        printer.list(list);
    }

}