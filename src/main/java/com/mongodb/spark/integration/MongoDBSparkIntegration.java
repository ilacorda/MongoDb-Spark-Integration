package com.mongodb.spark.integration;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.HashMap;
import java.util.Map;

public class MongoDBSparkIntegration {

    public static void main(String[] args) {

        String outputPath = "D:\\Dev\\MongoDb-Spark-Integration\\src\\main\\resources\\output";

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("MongoDB-Spark-Application");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        SQLContext sqlContext = new org.apache.spark.sql.SQLContext(sc);
        Map<String, String> options = new HashMap<String, String>();
        options.put("host", "localhost:27017");
        options.put("database", "mydb");
        options.put("collection", "test");

        DataFrame df = sqlContext.read().format("com.stratio.datasource.mongodb").options(options).load();
        df.registerTempTable("tmp");
        sqlContext.sql("SELECT * FROM tmp").show();
        df.write().format("com.databricks.spark.csv").save(outputPath);
        sqlContext.dropTempTable("tmp");
    }
}


