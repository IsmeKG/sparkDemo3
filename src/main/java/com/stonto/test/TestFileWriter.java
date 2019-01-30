package com.stonto.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileWriter {
    public static void main(String [] args ){
        try{
            String data = "This content will append to the end of the file";
            File file = new File("G:\\hadoop工具\\TestDemo1.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getPath(),true);
            System.out.println(data);
            fileWriter.write(data);
            //fileWriter.flush();
            fileWriter.close();
            System.out.println("Done");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
