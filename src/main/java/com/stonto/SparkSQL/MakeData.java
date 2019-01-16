package com.stonto.SparkSQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MakeData {
    public static void main(String [] args){
        /*
        * if (args.length < 2) {
        *   System.out.println("file path or num is null");
        *   System.exit(1);
        * }
        * */
        //String filePath = args[0];
        String filePath = "G:\\hadoop工具\\samplePeopleInformation.txt";
        //int peopleNum = Integer.valueOf(args[1]);
        int peopleNum = 1000;
        File file = new File(filePath);
        FileWriter fw = null;
        BufferedWriter bw = null;
        Random rand = new Random();
        int height = 0;
        int se = 0;
        String sex = null;
        try{
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for(int i = 1;i<=peopleNum;i++){
                height = rand.nextInt(100)+100;
                se = rand.nextInt(2);
                if(se!=0){
                    sex = "F";
                }else{
                    sex = "M";
                }
                bw.write(i+","+sex+","+height);
                bw.newLine();
                bw.flush();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                fw.close();
                bw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
