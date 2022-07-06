1. Set your SPARK_HOME

2. git clone

3. sbt clean

4. sbt package

5. Set your SPARK_HOME

6. Run the following shell command for Ingestion:
   SPARK_HOME/spark-submit \
   --master local \
   --deploy-mode client \
   --class IngestJob \
   /Users/zion/interview_2.12-0.1.jar /Users/zion/amenities.txt /Users/zion/properties.json

7. Run the following shell command for Reporting:
   SPARK_HOME/spark-submit \
   --master local \
   --deploy-mode client \
   --class ExportJob \
   /Users/zion/interview_2.12-0.1.jar