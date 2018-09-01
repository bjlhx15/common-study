package com.lhx.cloud;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Test01CompilerAPIFile {
    private static String JAVA_SOURCE_FILE = "DynamicObject.java";
    private static String JAVA_CLASS_FILE = "DynamicObject.class";
    private static String JAVA_CLASS_NAME = "DynamicObject";
    private static String PROJECT_DIR_ROOT = "/jdk-new-feature";
    private static String PROJECT_DIR = "/newfeature-jdk6";

    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        generateJavaClass();
        try {
            //将产生的类文件拷贝到程序的ClassPath下面,下面这一行代码是特定于Windows+IntelliJ IDEA 6.0项目,不具有移植性
//            Runtime.getRuntime().exec("cmd /c copy " + JAVA_CLASS_FILE + " classes//prodUCtion//JDK6Features");

//          Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","cp //Users//lihongxu6//IdeaProjects//common-base//" + JAVA_CLASS_FILE
//                    + " //Users//lihongxu6//IdeaProjects//common-base//jdk-new-feature//newfeature-jdk6//target//classes"});
            String[] a=new String[]{"/bin/sh", "-c", "cp "
                    + (System.getProperty("user.dir") + "/").replace("/", "//") + JAVA_CLASS_FILE
                    + " "
                    + (System.getProperty("user.dir") + PROJECT_DIR_ROOT + PROJECT_DIR + "/target/classes").replace("/", "//")};
                    Runtime.getRuntime().exec(a);
            Iterable<? extends JavaFileObject> sourcefiles = fileManager.getJavaFileObjects(JAVA_SOURCE_FILE);
            compiler.getTask(null, fileManager, null, null, null, sourcefiles).call();
            fileManager.close();
            Class.forName(JAVA_CLASS_NAME).newInstance();//创建动态编译得到的DynamicObject类的实例
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void generateJavaClass() {
        try {
            FileWriter fw = new FileWriter(JAVA_SOURCE_FILE);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("public class " + JAVA_CLASS_NAME + "{");
            bw.newLine();
            bw.write("public " + JAVA_CLASS_NAME + "(){System.out.println(\"In the constructor of DynamicObject\");}}");
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
