package com.stonto;

import java.io.File;

public class TestDemo {
    public static void main(String[] args){
        //用户当前工作目录
        String path1 = System.getProperty("user.dir");
        System.out.println("路径1："+path1);
        //文件路径分割符，解决跨平台问题，win下和Linux下的分割符是不一样的所以不能写死。
        String path2 = File.separator;
        System.out.println("路径2："+path2);
    }
}
