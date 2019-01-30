package com.stonto.SparkStreaming;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class MyProducer {
    public  static void mian(String[] args){
        //主要用于读取Java的配置文件
        Properties pros = new Properties();
        //用指定的键在此属性列表中搜索属性。也就是通过参数 key ，得到 key 所对应的 value。
        pros.setProperty("metadata.broker.list","localhost:9092");
        pros.setProperty("serializer.class","kafka.serializer.StringEncoder");

        pros.put("request.required,acks","1");

        ProducerConfig config = new ProducerConfig(pros);
        //创建生产对象
        kafka.javaapi.producer.Producer producer = new Producer(config);
        //生成消息
        KeyedMessage<String,String> data1 = new KeyedMessage<>("top1","test kafka");
        KeyedMessage<String,String> data2 = new KeyedMessage<>("top1","hello word");
        try{
            int i = 1;
            while (i<100) {
                producer.send(data1);
                producer.send(data2);
                i ++;
                Thread.sleep(100);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }



    }
}
