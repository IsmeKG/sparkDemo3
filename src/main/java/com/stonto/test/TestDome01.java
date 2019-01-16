package com.stonto.test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.Iterator;

public class TestDome01 {
    public static void main(String [] args){
        //配置spark  采用本地模式
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("G://hadoop工具//samplePeopleInformation.txt");
        JavaRDD<String> content = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(",")).iterator();
            }
        });
        System.out.println(content.count());

    }
}
