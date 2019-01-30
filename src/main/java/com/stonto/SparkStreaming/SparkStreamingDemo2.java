package com.stonto.SparkStreaming;

import org.apache.log4j.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;


/*
* 监控HDFS文件*/
public class SparkStreamingDemo2 {
    public static void main(String[] args) throws Exception{
        /*
        * 配置文件读取、保存路径
        * */
        String localPath = "G:\\hadoop工具\\数据\\";
        String hdfsPath = "hdfs://nnode:9000/sparkstream/";
        String localResult = "G:\\hadoop工具\\result\\folderMonitor";
        String hdfsResult =  "hdfs://nnode:9000/result/sparkstream/icmonitor";

        //设置日志级别，方便查看控制台输出
        org.apache.log4j.Logger.getLogger("org").setLevel(Level.ERROR);

        /*
        * 配置parkconf
        *   配置应用名称
        *   配置执行模式：local,yarn-client等
        *   配置线程数：local[2]
        * */

        SparkConf conf = new SparkConf().setMaster(/*"spark://nnode:7077"*/"local").setAppName("SparkStreaming monitoring HDFS folder");
        //配置sparkStream

        /*
        * 创建SparkStreamingContext
        *   SparkSteaming的入口，开始
        *   可以是基于SparkConf参数，也可以基于持久化的SparkStreamingContext进行状态恢复。
        *   典型场景：Driver崩溃后由于Spark Streaming具有24小时连续不间断运行，
        *             所以需要Driver重新启动后从上次运行的状态恢复过来，
        *             此时的状态需要基于曾经的CheckPoint
        * */

        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(3));


        //读取HDFS文件
        /*
        * 这路径如果hdfs的路径 你直接hadoop fs  -put 到你的监测路径就可以，
        * 如果是本地目录用file:///home/data 你不能移动文件到这个目录，必须用流的形式写入到这个目录形成文件才能被监测到。
        * */
        JavaDStream<String> lines = jssc.textFileStream(localPath);


        /*
        * 遍历每一行，将每一行的单次进行分割返回String 的 Iterator
        * */
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s)  {
                System.out.println(Arrays.asList(s.split(" ")).get(0));
                return Arrays.asList(s.split(" ")).iterator();
            }
        });


        /*
        * 将每一个单次计数，标记为1
        * */
        JavaPairDStream<String,Integer> pair = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {

                return new Tuple2<String,Integer>(s,1);
            }
        });


        /*
        * 将每一个相同的单次计数标记1相加
        * */
        JavaPairDStream<String,Integer> wordCounts = pair.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer s1, Integer s2) throws Exception {
                return s1+s2;
            }
        });


        /*
        * 此处的print方法并不会触发job执行，因为目前代码还处在sparkStreaming框架控制下，具体是否触发job取决于设置的Duration的时间间隔
        * */
        wordCounts.print();
        //保存到HDFS，文件夹后缀spark
        wordCounts.dstream().saveAsTextFiles(localResult,"Monitor");
        System.out.println("测试报错");
        /*
        * Spark Streaming 引擎开始执行，也就是Driver开始执行，
        * Driver启动时位于一个线程中，当然其内部有消息循环体，
        * 接收应用程序本身或者Executor发送过来的消息
        * */
        jssc.start();


        //通过手动终止计算，否则一直运行下去
        jssc.awaitTermination();


    }
}
