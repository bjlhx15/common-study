package com.github.bjlhx15.common.study.log;


import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationMain {
    private static Logger logger = Logger.getLogger(ApplicationMain.class.getName());

    public static void main(String[] args) {
        // 记录debug级别的信息
        logger.log(Level.INFO,"This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.warning("This is warning message.");
    }
}
