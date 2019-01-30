package com.stonto.sparkKafka;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/*
* spark 直连kafka消费kafka数据
* */
public class KafkaDemo {
    public static void main(String[] args){
        //配置spark
        SparkConf conf = new SparkConf()
                .setMaster("spark://nnode:7077")
                .setAppName("Spark connect Kafka Demo");
        //配置刷新时间
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));

        //配置消费


    }
}
