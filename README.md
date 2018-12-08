
By: Varun Venkitachalam, Ryan Tang, Chamath Gamalath

Getting the data
----------------

1. Go to https://console.cloud.google.com and login using a gmail account

2. On the left panel, click APIs & Services > Library

3. Create a project

4. Once you're in a project, on the left panel, click on library and search for "Youtube Data API v3" and enable this API. Then, click on credential to create an Oath Client ID, then download this file.

5. Import the maven project from into your IDE, put the client_secret.json file in the resource folder and execute the App.java

Running Mapreduce on XSEDE Bridges
----------------------------------

1. Login to bridges and load the input file into a directory named "input"(create the directory if it doesn't already exist)
	scp -P 2222 youtubedata.txt username@bridges.psc.xsede.org:~/input

2. Run another scp command to load the java files(Top5Category and VideoRating). Note: remove the package declaration in the java files 

3. Run the following command to start hadoop
	interact -N 4 -t 01:00:00
	module load hadoop
	start-hadoop.sh
	
	NOTE: If met with a Y/N prompt while start-hadoop.sh runs, please enter "N" to it. 

4. Compile the java file and create a jar with these commands
	hadoop com.sun.tools.javac.Main Top5Category.java
	jar cf tc.jar Top5Category*.class

5. Create a directory to store input files in the Hadoop file system
	hadoop fs -mkdir -p in

6. Load file to Hadoop file system
	hadoop fs -put $HOME/input/youtubedata.txt in

7. Execute the program
	hadoop jar tc.jar Top5Category in/youtubedata.txt output

8. Now you can see the output file with this command
	hadoop fs -ls output

9. To see what is in the file 
	hadoop fs -cat output/<file name>


ExecutionTime for Top5Categories:
----------------------------------
[varunv@r024 CS4650FinalProject]$ hadoop jar tf.jar Top5Categories in/youtubedata.txt output
18/12/02 18:08:10 INFO client.RMProxy: Connecting to ResourceManager at r024.opa.bridges.psc.edu/10.4.116.25:8032
18/12/02 18:08:17 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
18/12/02 18:09:02 INFO input.FileInputFormat: Total input paths to process : 1
18/12/02 18:09:59 INFO mapreduce.JobSubmitter: number of splits:15
18/12/02 18:10:23 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1543790694148_0001
18/12/02 18:10:24 INFO impl.YarnClientImpl: Submitted application application_1543790694148_0001
18/12/02 18:10:24 INFO mapreduce.Job: The url to track the job: http://r024.opa.bridges.psc.edu:8088/proxy/application_1543790694148_0001/
18/12/02 18:10:24 INFO mapreduce.Job: Running job: job_1543790694148_0001
18/12/02 18:11:57 INFO mapreduce.Job: Job job_1543790694148_0001 running in uber mode : false
18/12/02 18:11:57 INFO mapreduce.Job:  map 0% reduce 0%
18/12/02 18:12:25 INFO mapreduce.Job:  map 47% reduce 0%
18/12/02 18:12:26 INFO mapreduce.Job:  map 60% reduce 0%
18/12/02 18:12:28 INFO mapreduce.Job:  map 67% reduce 0%
18/12/02 18:12:43 INFO mapreduce.Job:  map 73% reduce 0%
18/12/02 18:12:44 INFO mapreduce.Job:  map 100% reduce 22%
18/12/02 18:12:47 INFO mapreduce.Job:  map 100% reduce 61%
18/12/02 18:12:50 INFO mapreduce.Job:  map 100% reduce 67%
18/12/02 18:12:59 INFO mapreduce.Job:  map 100% reduce 100%
18/12/02 18:15:51 INFO mapreduce.Job: Job job_1543790694148_0001 completed successfully
18/12/02 18:15:51 INFO mapreduce.Job: Counters: 50
	File System Counters
		FILE: Number of bytes read=148905867
		FILE: Number of bytes written=299747784
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=1973734607
		HDFS: Number of bytes written=308
		HDFS: Number of read operations=48
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
	Job Counters
		Killed map tasks=1
		Launched map tasks=16
		Launched reduce tasks=1
		Data-local map tasks=16
		Total time spent by all maps in occupied slots (ms)=52667220
		Total time spent by all reduces in occupied slots (ms)=7665054
		Total time spent by all map tasks (ms)=335460
		Total time spent by all reduce tasks (ms)=48822
		Total vcore-milliseconds taken by all map tasks=335460
		Total vcore-milliseconds taken by all reduce tasks=48822
		Total megabyte-milliseconds taken by all map tasks=1681325520
		Total megabyte-milliseconds taken by all reduce tasks=244695864
	Map-Reduce Framework
		Map input records=8347549
		Map output records=8347549
		Map output bytes=132210763
		Map output materialized bytes=148905951
		Input split bytes=1980
		Combine input records=0
		Combine output records=0
		Reduce input groups=15
		Reduce shuffle bytes=148905951
		Reduce input records=8347549
		Reduce output records=15
		Spilled Records=16695098
		Shuffled Maps =15
		Failed Shuffles=0
		Merged Map outputs=15
		GC time elapsed (ms)=2695
		CPU time spent (ms)=67170
		Physical memory (bytes) snapshot=18607202304
		Virtual memory (bytes) snapshot=102859317248
		Total committed heap usage (bytes)=30232018944
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=1973732627
	File Output Format Counters
		Bytes Written=308



ExecutionTime for VideoRating:
----------------------------------
[varunv@r024 CS4650FinalProject]$ hadoop jar vr.jar VideoRating in/youtubedata.txt output1
18/12/02 18:20:14 INFO client.RMProxy: Connecting to ResourceManager at r024.opa.bridges.psc.edu/10.4.116.25:8032
18/12/02 18:20:14 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
18/12/02 18:20:57 INFO input.FileInputFormat: Total input paths to process : 1
18/12/02 18:21:38 INFO mapreduce.JobSubmitter: number of splits:15
18/12/02 18:22:04 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1543790694148_0002
18/12/02 18:22:04 INFO impl.YarnClientImpl: Submitted application application_1543790694148_0002
18/12/02 18:22:04 INFO mapreduce.Job: The url to track the job: http://r024.opa.bridges.psc.edu:8088/proxy/application_1543790694148_0002/
18/12/02 18:22:04 INFO mapreduce.Job: Running job: job_1543790694148_0002
18/12/02 18:22:44 INFO mapreduce.Job: Job job_1543790694148_0002 running in uber mode : false
18/12/02 18:22:44 INFO mapreduce.Job:  map 0% reduce 0%
18/12/02 18:23:10 INFO mapreduce.Job:  map 27% reduce 0%
18/12/02 18:23:12 INFO mapreduce.Job:  map 33% reduce 0%
18/12/02 18:23:22 INFO mapreduce.Job:  map 67% reduce 0%
18/12/02 18:23:23 INFO mapreduce.Job:  map 87% reduce 0%
18/12/02 18:23:24 INFO mapreduce.Job:  map 100% reduce 0%
18/12/02 18:23:27 INFO mapreduce.Job:  map 100% reduce 51%
18/12/02 18:23:30 INFO mapreduce.Job:  map 100% reduce 67%
18/12/02 18:23:33 INFO mapreduce.Job:  map 100% reduce 74%
18/12/02 18:23:36 INFO mapreduce.Job:  map 100% reduce 100%
18/12/02 18:26:09 INFO mapreduce.Job: Job job_1543790694148_0002 completed successfully
18/12/02 18:26:09 INFO mapreduce.Job: Counters: 50
	File System Counters
		FILE: Number of bytes read=150255888
		FILE: Number of bytes written=302447826
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		HDFS: Number of bytes read=1973734607
		HDFS: Number of bytes written=85526
		HDFS: Number of read operations=48
		HDFS: Number of large read operations=0
		HDFS: Number of write operations=2
	Job Counters
		Killed map tasks=1
		Launched map tasks=15
		Launched reduce tasks=1
		Data-local map tasks=15
		Total time spent by all maps in occupied slots (ms)=50318343
		Total time spent by all reduces in occupied slots (ms)=5996772
		Total time spent by all map tasks (ms)=320499
		Total time spent by all reduce tasks (ms)=38196
		Total vcore-milliseconds taken by all map tasks=320499
		Total vcore-milliseconds taken by all reduce tasks=38196
		Total megabyte-milliseconds taken by all map tasks=1606340988
		Total megabyte-milliseconds taken by all reduce tasks=191438352
	Map-Reduce Framework
		Map input records=8347549
		Map output records=8347549
		Map output bytes=133560784
		Map output materialized bytes=150255972
		Input split bytes=1980
		Combine input records=0
		Combine output records=0
		Reduce input groups=4066
		Reduce shuffle bytes=150255972
		Reduce input records=8347549
		Reduce output records=4066
		Spilled Records=16695098
		Shuffled Maps =15
		Failed Shuffles=0
		Merged Map outputs=15
		GC time elapsed (ms)=2207
		CPU time spent (ms)=78680
		Physical memory (bytes) snapshot=19406004224
		Virtual memory (bytes) snapshot=102874152960
		Total committed heap usage (bytes)=32275169280
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters
		Bytes Read=1973732627
	File Output Format Counters
		Bytes Written=85526

Our 2GB dataset:
----------------------------------
https://drive.google.com/open?id=1u4weAUOApcZIx7T4tBMtcqJhfmYQsK6e

PowerPoint Slides:
----------------------------------
https://docs.google.com/presentation/d/1Sjz8gNBXqSMkHhwLQkDkv9DlcbtToIIohSB3kdgl4sU/edit?usp=sharing
