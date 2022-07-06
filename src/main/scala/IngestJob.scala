package org.interview.airdna

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{BooleanType, DateType, IntegerType, StringType, StructField, StructType}


object IngestJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("IngestJob").config("spark.master", "local").getOrCreate()
    spark.conf.set("spark.sql.sources.partitionOverwriteMode","dynamic")


    val properties_jsonSchema = StructType(List(
      StructField("property_id", IntegerType),
      StructField("active", BooleanType),
      StructField("discovered_dt", DateType),
    ))
    val properties_jsonDF = spark.read.schema(properties_jsonSchema).json(args(1))
    properties_jsonDF.write.mode(SaveMode.Overwrite).parquet("properties.parquet")


    val amenities_txtSchema = StructType(List(
      StructField("property_id", IntegerType),
      StructField("amenities", StringType)))
    val amenities_df = spark.read.schema(amenities_txtSchema).option("delimiter", " ").csv(args(0))
    amenities_df.show(5, truncate = false)
    amenities_df.write.mode(SaveMode.Overwrite).parquet("amenities.parquet")

    spark.stop()
  }
}

