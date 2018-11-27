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