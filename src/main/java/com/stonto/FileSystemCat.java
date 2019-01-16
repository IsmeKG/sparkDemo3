package com.stonto;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class FileSystemCat {
    public static void main(String []args) throws Exception{
        String uri = args[0];
        SparkConf conf = new SparkConf().setAppName("Spark FileSystemCat written by Java");
        conf.setMaster("yarn-client");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile(uri);

        System.out.println(lines);

    }
}

