package com.github.bjlhx15.common.study.log;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationMain {
    private static Log logger = LogFactory.getLog(ApplicationMain.class);

    public static void main(String[] args) {
        System.out.println(logger.getClass().getName());
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
}
