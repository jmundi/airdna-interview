1. **Set your SPARK_HOME.Version of Spark should be 3.2.0**
   export SPARK_HOME="/path/to/spark"

2. **Create Base Folder**
   mkdir interview
   **copy amenities.txt and properties.json to this directory**

4. **Clone Repository**
   cd interview
   git clone https://github.com/jmundi/interview-airdna

5. **SBT Clean**
   cd interview-dna
   sbt clean
   sbt assembly
   Copy jar files to base directory.

6. Run the following shell command for Ingestion:
   $SPARK_HOME/bin/spark-submit \
   --master local \
   --deploy-mode client \
   --class org.interview.airdna.IngestJob \
   interview-airdna-assembly-0.1.jar amenities.txt properties.json

7. Run the following shell command for Reporting:
   $SPARK_HOME/bin/spark-submit \
   --master local \
   --deploy-mode client \
   --class org.interview.airdna.IngestAudit \
   interview-airdna-assembly-0.1.jar

8. Run the following shell command for Reporting:
   $SPARK_HOME/bin/spark-submit \
   --master local \
   --deploy-mode client \
   --class org.interview.airdna.ExportJob \
   interview-airdna-assembly-0.1.jar