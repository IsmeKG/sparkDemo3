package com.stonto.test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;

/**
 * 统计记录数
 * */
public class CountRecord {
    public static void main(String[] args){
        /**
         * 1、创建spark的配置对象SparkConf
         *    setMaster:1、运行在本地可是使用local参数
         *              2、如果要运行在集群中，以Standalone模式运行的话，需要使用spark://HOST:PORT
         *                  的形式指定master的IP和端口号，默认是7077
         *    setAppName用来设置应用程序的名称，在程序运行的监控界面可以看到该名称
         * */
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark CountRecord by Java");
        /*
        * 2、创建SparkContext 对象
        * SparkContext是Spark程序所有功能的唯一入口
        * SparkContext核心作用： 初始化Spark应用程序运行所需要的核心组件，包括DAGScheduler、TaskScheduler、SchedulerBackend
        * 同时还会负责Spark程序往Master注册程序
        * 通过传入SparkConf实例来定制Spark运行的具体参数和配置信息
        * */
        JavaSparkContext sc = new JavaSparkContext(conf);

        /*
        * 3、sc中提供了textFile方法是SparkContext中定义的，如下：
         * def textFile(path: String): JavaRDD[String] = sc.textFile(path)
         * 用来读取HDFS上的文本文件、集群中节点的本地文本文件或任何支持Hadoop的文件系统上的文本文件，它的返回值是JavaRDD[String]，是文本文件每一行
         *
         * 第3步：根据具体的数据来源(如HDFS、HBase、Local FS、DB、S3等)通过JavaSparkContext来创建JavaRDD
         * JavaRDD的创建基本有三种方式：根据外部的数据来源(例如HDFS)、根据Scala集合、由其它的RDD操作
         * 数据会被RDD划分成一系列的Partitions，分配到每个Partition的数据属于一个Task的处理范畴
        *
        * */
        JavaRDD<String> lines = sc.textFile("G://hadoop工具//part-00000");
        /*
        * 4、统计数据条数
        * */
        System.out.println(lines.count());
        /**
         * 第6步：关闭SparkContext容器，结束本次作业
         */

        sc.close();
    }
}
