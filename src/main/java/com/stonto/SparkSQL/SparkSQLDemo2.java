package com.stonto.SparkSQL;

import com.stonto.DataModel.ICRecordModel;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.codehaus.janino.Java;
import scala.Function1;

public class SparkSQLDemo2 {
    public static void main(String[] args){

        String hdfsPath ="hdfs://nnode:9000/spark/20180301-3万条.txt";
        String  localhost = "G://hadoop工具//20180301-UTF8.txt";
        String resultPath = "hdfs://nnode:9000/spark/result";

        SparkConf conf = new SparkConf().setMaster("local").setAppName("Test");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession sparkSession = SparkSession.builder()
                .appName("SparkSQLTest")
                .master("local")
                .getOrCreate();
        /**
         * RDD被创建好以后，在后续使用过程中一般会发生两种操作：
         * 转换（Transformation）： 基于现有的数据集创建一个新的数据集。
         * 行动（Action）：在数据集上进行运算，返回计算值。
         * */
        JavaRDD<String> lines = sc.textFile (localhost);
        //lines.first();
        /**
         * persist()的圆括号中包含的是持久化级别参数，比如，persist(MEMORY_ONLY)表示将RDD作为反序列化的对象存储于JVM中，
         * 如果内存不足，就要按照LRU原则替换缓存中的内容。persist(MEMORY_AND_DISK)表示将RDD作为反序列化的对象存储在JVM中，
         * 如果内存不足，超出的分区将会被存放在硬盘上。一般而言，使用cache()方法时，会调用persist(MEMORY_ONLY)。
         * */
        lines.cache();
        //Dataset<String> lines1 = sparkSession.read().textFile(localhost);

    }
}
