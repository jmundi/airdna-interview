package org.interview.airdna

import org.apache.spark.sql.{SparkSession}

object ExportJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("ExportJob").config("spark.master", "local").getOrCreate()
    spark.read.parquet("amenities.parquet").createOrReplaceTempView("vw_amenities")
    spark.read.parquet("properties.parquet").createOrReplaceTempView("vw_properties")

    val active_properties_report = spark.sql("select p.property_id, a.amenities from vw_amenities a " +
      "inner join vw_properties p on a.property_id = p.property_id " +
      "where p.active = true;")
    active_properties_report.show(100, false)
    active_properties_report.write.mode("overwrite").json("active_properties_report.json")


    val properties_year_month_agg_report = spark.sql("select year(discovered_dt) as year, month(discovered_dt) as month, count( distinct property_id) as count from vw_properties " +
      "group by year(discovered_dt), month(discovered_dt) " +
      "order by 1 asc, 2 asc;")
    properties_year_month_agg_report.show(100, false)
    properties_year_month_agg_report.write.option("header",true).mode("overwrite").csv("properties_year_month_agg_report.csv")





    spark.stop()
  }
}

