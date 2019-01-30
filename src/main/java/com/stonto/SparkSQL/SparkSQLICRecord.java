package com.stonto.SparkSQL;

import com.stonto.DataModel.ICRecordModel;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.util.Iterator;

/**
 * 统计IC卡每种卡类型刷卡量
 * @author Isme
 * */
public class SparkSQLICRecord {
    public static void main(String[] args){
        String hdfsPath ="hdfs://nnode:9000/spark/20180301-3万条.txt";
        String  localhost = "G://hadoop工具//20180301-3万条.txt";
        String resultPath = "hdfs://nnode:9000/spark/result";
        //配置spark
        SparkConf conf = new SparkConf()
                .setAppName("ICRecord Caculate by SparkSQL")
                .setMaster("local")
                //.setMaster("yarn-client")
                //.set("spark.yarn.jar","hdfs://nnode:9000/spark/spark")
                ;

        //创建Sparkcontext对象
        JavaSparkContext sc = new JavaSparkContext(conf);

        //sparksession
        /**
         * SparkSession支持从不同的数据源加载数据，并把数据转换成DataFrame，并支持把DataFrame转换成SQLContext自身中的表
         * SparkSession 是 Spark SQL 的入口
         * 使用 Dataset 或者 Datafram 编写 Spark SQL 应用的时候，第一个要创建的对象就是 SparkSession。
         * Builder 是 SparkSession 的构造器。 通过 Builder, 可以添加各种配置。
         * Builder 的方法如下
         * getOrCreate	获取或者新建一个 sparkSession
         * enableHiveSupport	增加支持 hive Support
         * appName	设置 application 的名字
         * config	设置各种配置
         * */
        SparkSession sparkSession = SparkSession.builder().appName("SparkSession").getOrCreate();
        //读取文件

        JavaRDD<String> lines = sc.textFile(hdfsPath);

        JavaRDD<ICRecordModel> icRecordModelJavaRDD = lines.map(new Function<String, ICRecordModel>() {
            @Override
            public ICRecordModel call(String s) throws Exception {
                String [] records = s.split("\001");
                //System.out.println(records[0]);
                //System.out.println("list 长度："+records.length);
                if(records.length == 19){
                    ICRecordModel icRd = new ICRecordModel();
                    icRd.setTradeType(records[0]);
                    icRd.setTradeCnt(records[1]);
                    icRd.setLineNo(records[2]);
                    icRd.setLineID(records[3]);
                    icRd.setBusNo(records[4]);
                    icRd.setBusID(records[5]);
                    icRd.setCardID(records[6]);
                    icRd.setTradeTime(records[7]);
                    icRd.setIsUpTrade(records[8]);
                    icRd.setUpTradeTime(records[9]);
                    icRd.setDownTradeTime(records[10]);
                    icRd.setTradeStation(records[11]);
                    icRd.setUpTradeStation(records[12]);
                    icRd.setDownTradeStation(records[13]);
                    icRd.setMarkTime(records[14]);
                    icRd.setMarkStation(records[15]);
                    icRd.setTradeAttr(records[16]);
                    icRd.setTradeDate(records[17]);
                    icRd.setReceiveTime(records[18]);
                    return icRd;
                }
                return null;
            }
        });



        Dataset<Row> df = sparkSession.createDataFrame(icRecordModelJavaRDD,ICRecordModel.class);

        SQLContext sqlContext = new SQLContext(sparkSession);

        sqlContext.registerDataFrameAsTable(df,"T_ICRCORD");
        //sqlContext.sql("select TradeType,count(1) rs from T_ICRCORD group by TradeType").show();
        Dataset<Row> tradeType = sqlContext.sql("select TradeType,count(TradeType) rs from T_ICRCORD group by TradeType");

        JavaRDD<Row> resultRecord = tradeType.javaRDD().repartition(1);

        resultRecord.saveAsTextFile(resultPath);
        sc.close();

    }
}
