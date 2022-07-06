package org.interview.airdna
import com.amazon.deequ.{VerificationResult, VerificationSuite}
import com.amazon.deequ.checks.{Check, CheckLevel, CheckStatus}
import com.amazon.deequ.constraints.ConstraintStatus
import org.apache.spark
import org.apache.spark.sql.SparkSession

object IngestAudit {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder.appName("IngestAuditJob").config("spark.master", "local").getOrCreate()
    val data = spark.read.parquet("properties.parquet")
    val verificationResult_Properties: VerificationResult = VerificationSuite()
      .onData(data)
      .addCheck(
        Check(CheckLevel.Error, "unit testing my data")
          .hasSize(_ == 100000)
          .isComplete("property_id") // should never be NULL
          .isUnique("property_id") // should not contain duplicates
          .isComplete("property_id")) // should never be NULL
      .run()

    if (verificationResult_Properties.status == CheckStatus.Success) {
      println("The data passed the test, everything is fine!")
    } else {
      println("We found errors in the data:\n")

      val resultsForAllConstraints = verificationResult_Properties.checkResults
        .flatMap { case (_, checkResult) => checkResult.constraintResults }

      resultsForAllConstraints
        .filter { _.status != ConstraintStatus.Success }
        .foreach { result => println(s"${result.constraint}: ${result.message.get}") }
    }
  }

}
