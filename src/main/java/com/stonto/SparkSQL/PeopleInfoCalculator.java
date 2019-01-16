package com.stonto.SparkSQL;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.codehaus.janino.Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PeopleInfoCalculator {
    public static void main(String[] args){
        SparkConf sparkConf = new SparkConf().setAppName("PeopleInfoCalculator").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> dataFile = sc.textFile("G:\\hadoop工具\\samplePeopleInformation.txt");

        //过滤性别M
        JavaRDD<String> maleFilterData = dataFile.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("M");
            }
        });
        //过滤性别F
        JavaRDD<String> femalFilterData = dataFile.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("F");
            }
        });
        //得到性别为M的身高数据
        JavaRDD<String> maleHeightData = maleFilterData.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(",")[2]).iterator();
            }
        });
        //得到性别为F 的身高数据
        JavaRDD<String> femaleHeightData = femalFilterData.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(",")[2]).iterator();
            }
        });
        //将字符串格式转化为整型
        JavaRDD<Integer>  maleHeightDataInt = maleHeightData.map(new Function<String, Integer>() {
            @Override
            public Integer call(String s) throws Exception {
                return Integer.parseInt(String.valueOf(s));
            }
        });
        JavaRDD<Integer> femaleHeightDataInt = femaleHeightData.map(new Function<String, Integer>() {
            @Override
            public Integer call(String s) throws Exception {
                return Integer.parseInt(String.valueOf(s));
            }
        });

        //排序
        //sortBy(<T>,ascending,numPartitions) 解释：
        //第一个参数是一个函数，该函数也有一个带泛型T的参数，返回类型和RDD中元素的类型是一致的；
        //第二个参数是ascending，这个参数决定RDD中元素排序后是升序还是降序，默认是true，也就是升序；
        //第三个参数是numPartitions，该参数决定排序后RDD的分区个数，默认排序后的分区个数和排序之前相等，即为 this.partitions.size
        // 升序
        JavaRDD<Integer> maleHeightLowDataSort = maleHeightDataInt.sortBy(new Function<Integer, Integer>() {
            public Integer call(Integer s)throws Exception{
                return s;
            }
        },true,3);
        JavaRDD<Integer> femaleHeigthLowDataSort = femaleHeightDataInt.sortBy(new Function<Integer, Integer>() {
            public Integer call(Integer s){
                return s;
            }
        },true,3);
        //降序
        JavaRDD<Integer> maleHeightHightDataSort = maleHeightDataInt.sortBy(new Function<Integer, Integer>() {
            public Integer call(Integer s){
                return s;
            }
        }, false, 3);
        JavaRDD<Integer> femaleHeightHightDataSort = femaleHeightDataInt.sortBy(new Function<Integer, Integer>() {
            public Integer call(Integer s){
                return s;
            }
        }, false, 3);

        //最矮身高
        Integer lowestMale = maleHeightLowDataSort.first();
        Integer lowestFemale = femaleHeightHightDataSort.first();
        //最高身高
        Integer highestMale = maleHeightHightDataSort.first();
        Integer highestFemale = femaleHeightHightDataSort.first();

        System.out.println("Number of Female:"+femalFilterData.count());
        System.out.println("Number of male:"+maleFilterData.count());
        System.out.println("Lowest Female:"+lowestFemale);
        System.out.println("Hightest Female:"+highestFemale);
        System.out.println("Lowest Male:"+lowestMale);
        System.out.println("Highest Male:"+highestMale);
    }
}
