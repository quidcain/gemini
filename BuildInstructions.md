##SRS Build Instructions##

File Structure:

	admin-portal
	database
	enum-codes-generation
	portal
	srs-common


#####Backend - Java REST Service###

To Build the back-end [admin-portal or portal] modules:

* Install Maven 
* Install oracle jar library using maven
* Go to `~/project-gemini/database`
* Execute `./installOracleDriver.sh`
* Go to `~/project-gemini/` 
* Execute `mvn clean`
* Execute `mvn compile -P [dev,pmaxio, tmaxio]`
* Execute `mvn package -P [dev,pmaxio, tmaxio]`
* Go to `~/project-gemini/admin-portal/rest/target` 
* A war file named `[#MavenProfileName]-admin-api###version.war` is produced


		
#####Frontend - webapp#####

To run the front-end [admin-portal or portal]: 

* Install Yarn Package Manager 
* Execute the command `yarn`
* To Start execute `yarn start`
* Start to develop

To Build the front-end:

* Locale a script at `~/project-gemini/admin-portal/webapp/build.sh`
* Execute it for the following enviroments: [DEV, TMAX, PMAX]
* This script will produce a tomcat ready war file 

	