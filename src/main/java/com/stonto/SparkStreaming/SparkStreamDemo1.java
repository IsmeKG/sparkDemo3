package com.stonto.SparkStreaming;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
/**
 * 监控端口*/
public class SparkStreamDemo1 {
    public static void main(String[] args)throws Exception {
        SparkConf sparkConf = new SparkConf().setMaster("spark://nnode:7077").setAppName("Test SparkStreaming WordCount");
        //每三秒执行
        JavaStreamingContext jsc = new JavaStreamingContext(sparkConf, Durations.seconds(3));

        //从端口监听数据
        JavaReceiverInputDStream<String> lines = jsc.socketTextStream("nnode",9999);

        //处理监听到的数据
        //1.拆分词
        JavaDStream<String> words= lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
        
        //2.统计
        JavaPairDStream<String,Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String,Integer>(s,1);
            }
        });
        System.out.println(pairs);

        JavaPairDStream<String,Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) throws Exception {
                return i1+i2;
            }
        });

        wordCounts.print();
        wordCounts.dstream().saveAsTextFiles("hdfs://nnode:9000/sparkstream/result","spark");
        //开启任务
        jsc.start();
        jsc.awaitTermination();

    }



}
