package com.stonto;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.FileWriter;

//spark-submit --class com.stonto.CountRecordMaster  /home/sparkD2.jar --master spark://nnode:7077   执行方法
public class CountRecordMaster {
    public static void main(String [] args){
        //如果你的windows用户名和集群上用户名不一样，这里就应该配置一下。
        // 比如我windows用户名为Frank,而装有hadoop的集群username为hadoopuser,这里我就这样设置
        System.setProperty("HADOOP_USER_NAME","hadoopuser");
        //1.sparkconf
        SparkConf conf = new SparkConf().setAppName("Spark CountRecord written by Java");
        //这里配置以yarn-client方式
        conf.setMaster("local");

        //2.sc
        JavaSparkContext sc = new JavaSparkContext(conf);

        //3. rdd
        JavaRDD<String> lines = sc.textFile("hdfs://nnode:9000/spark/20180301-3万条.txt");

        //lines.count() 是Long型的，转换成String
        String cr = Long.toString(lines.count());
        //4.SAVE

        System.out.println(lines.count());
        //saveAsTextFile要求保存的目录之前是没有的，否则会报错。所以，最好程序中保存前先判断一下目录是否存在
        lines.saveAsTextFile("hdfs://nnode:9000/spark/result/recordCount.txt");


        sc.close();


    }
}
