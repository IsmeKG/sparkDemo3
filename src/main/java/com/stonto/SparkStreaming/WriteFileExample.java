package com.stonto.SparkStreaming;

import java.io.File;
import java.io.FileOutputStream;

public class WriteFileExample {
    public static void main(String[] args) throws Exception{
        String localPath = "G:\\hadoop工具\\数据\\7.txt";
        File file = new File(localPath);
        String context = "This content is writ into file by BufferedWriter Method";

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        if(! file.exists()){
            file.createNewFile();
        }
        byte[] contentInBytes = context.getBytes();
        fileOutputStream.write(contentInBytes);
        fileOutputStream.flush();
        fileOutputStream.close();

    }


}
