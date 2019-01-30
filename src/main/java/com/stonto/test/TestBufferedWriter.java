package com.stonto.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestBufferedWriter {
    public static void main(String [] args){

        try{
            String content = "This content is writ into file by BufferedWriter Method";
            File file = new File("G:\\hadoop工具\\TestDemo1.txt");

            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(content);
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
