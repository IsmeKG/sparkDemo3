package com.stonto.SparkSQL;



import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;


import java.io.Serializable;
import java.util.Arrays;

public class SparkSQLDemo1 {
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("SparkSQL").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SparkSession sparkSession = SparkSession.builder().appName("SparkSQL").getOrCreate();
        //JavaRDD<String> input = sc.parallelize(Arrays.asList("Lucy,23","Mark,22"));
        JavaRDD<String> input = sc.textFile("G:\\hadoop工具\\samplePeopleInformation.txt");
        JavaRDD<Person> personJavaRDD = input.map(new Function<String, Person>() {
            @Override
            public Person call(String s) throws Exception {
                String[] parts =s.split(",");
                Person p = new Person(parts[1],Integer.parseInt(parts[2]));
                return  p;
            }
        });
        //System.out.println(personJavaRDD.collect());

        //将RDD变成以Row为类型的RDD。Row可以简单理解为Table的一行数据
        Dataset<Row> df = sparkSession.createDataFrame(personJavaRDD,Person.class);
        //df.show();
        //df.printSchema();

        SQLContext sqlContext = new SQLContext(sparkSession);
        sqlContext.registerDataFrameAsTable(df,"t_person");

        /*sqlContext.sql("select * from t_person where age>180").show();
        sqlContext.sql("select count(1) from t_person").show();*/
        //DataFrame  df1 = sqlContext.sql("select count(1) from t_person");
        Dataset<Row> ds = sqlContext.sql("select * from t_person where age>180");
        ds.registerTempTable("SparkTest");
        JavaRDD<Row> sdRow =ds.javaRDD().repartition(1);
        String resultPath = "G:\\hadoop工具\\result1";
        sdRow.saveAsTextFile(resultPath);
        sc.close();
    }




    public static class Person implements Serializable {
        private static final long serialVersionUID = -6259413972682177507L;
        private String name;
        private int age;

        public Person(String name,int age){
            this.name = name;
            this.age = age;
        }
        public String toString(){
            return name+":"+age;
        }
        public String getName(){
            return name;
        }
        public int getAge(){
            return age;
        }
        public void setName(String name){
            this.name = name;
        }
        public void setAge(int age){
            this.age = age;
        }

    }


}
