package com.github.bjlhx15.common.study.log;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.*;

public class SelfJavaLogMain {
    //生成单例日志输出handler实例
    private static SelfHandle sf = new SelfHandle();

    /**
     * 根据class获取logger
     *
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getSimpleName());
    }

    /**
     * 根据loggerName获取logger
     *
     * @param loggerName
     * @return
     */
    public static Logger getLogger(String loggerName) {
        Logger logger = Logger.getLogger(loggerName);
        logger.addHandler(sf);
        return logger;
    }

    /**
     * 自定义日志格式formatter
     */
    public static class SelfFormater extends Formatter {

        public static SelfFormater selfFormater = new SelfFormater();

        @Override
        public String format(LogRecord record) {
            return String.format("[%s] %s %s.%s: %s\r\n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                    record.getLevel().getName(), record.getSourceClassName(), record.getSourceMethodName(), record.getMessage());
        }

        public static SelfFormater getInstance() {
            return selfFormater;
        }
    }

    /**
     * 自定义日志level
     */
    public static class SelfLevel extends Level {

        public static SelfLevel ERROR = new SelfLevel("ERROR", 910);

        /**
         * @param name  自定义级别名称
         * @param value 自定义级别的权重值
         */
        protected SelfLevel(String name, int value) {
            super(name, value);
        }
    }

    /**
     * 自定义日志输出handler
     */
    public static class SelfHandle extends Handler {

        /**
         * 日志写入的位置
         *
         * @param record
         */
        @Override
        public void publish(LogRecord record) {
            try {
                //每一个不同的loggerName分别记在不同的日志文件中
                File file = new File(String.format("%s_%s.log", record.getLoggerName(), DateFormatUtils.format(new Date(), "yyyy-MM-dd")));
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file, true);
                PrintWriter pw = new PrintWriter(fw);
                String log = String.format("[%s] %s %s.%s: %s\r\n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        record.getLevel().getName(), record.getSourceClassName(), record.getSourceMethodName(), record.getMessage());
                pw.write(log);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * 刷新缓存区，保存数据
         */
        @Override
        public void flush() {
        }

        /**
         * 关闭
         *
         * @throws SecurityException
         */
        @Override
        public void close() throws SecurityException {
        }
    }

    public static void main(String[] args) throws Exception {
        //获取自定义的logger
        Logger logger = getLogger(SelfJavaLogMain.class);
        logger.info("info log");
        logger.severe("severe log");
        //使用自定义的logger level
        logger.log(SelfLevel.ERROR, "error log");
    }
}
