


# Phoenix

This is a prototype project to test several architectural concepts. It is not a polished production-grade system. Briefly the key concepts are:

* Data-driven with test data stored in a table in a database
* Test results are stored in a table in a database, not in HTML files
* Intended to simulate users using a system, not just automating manual test cases
* New use for inheritance: Superclass provides low-level funtionality while Subclasses provide high-level functionality (built from low-level superclass methods)
* Uses just Java and Selenium (no Maven, grunt, TestNG, etc), and I tried to keep the Java fairly basic (for example there are no object factories)

There are still a few more ideas I plan on trying, such as adding an Oracle (as in delphi, not as in database) to determine success or failure of test cases.

This ReadMe is also a bit crude and I should probably make it a little more detailed, especially the notes on how to install and use the project. So much code, so little time.

## Getting Started

1. Compile the Java code
2. Configure the config.properties file for your test system
3. Create a database called “phoenixdb” in mySQL, and a user “phoenix”. Enter the password for this user in your config.properties file.
4. Run the SQL scripts that create and populate the tables in the phoenixdb database

### Prerequisites

Recommend setting up MySQL and Jira on a Server and a development environment on a separate computer. I use a Windows 10 laptop for my development environment and a Fedora 28 VM for my Server machine.

On the server:

1. MySQL Community Server database 5.7.23
2. MySQL Connector Java 5.1.56
3. Java 8+
4. Atlassian Jira Software 7.9.2

On the development machine:

1. Firefox browser 62+ 
2. Gecko driver v0.19.1-win64
3. Java 8 JDK
4. Selenium 3.10.0
5. An IDE (I use Eclipse)
6. (optional) Database browser

### Installing

Development environment installation example:

1. Install Eclipse IDE
2. Install Java JDK
3. Install Selenium 
4. Download Phoenix code and create new Java project in Eclipse
5. Add Selenium driver to Eclipse library
6. Install gecko driver and add path to config.properties

Server environment installation example:

1. Install MySQL
2. Log into MySQL command line and perform the following tasks:

* Create a database for Jira: create database jiradb character set utf8;

* Grant access rights to the database: grant all on jiradb.* to ‘jirauser’@’%’ identified by ‘**password**’;

* run the following statement to set up correct collation for Jira:

ALTER DATABASE jiradb DEFAULT CHARACTER SET utf8 COLLATE utf8_bin

* Create a database for Phoenix: create database phoenixdb;
* Grant access to the database: grant all privileges on phoenixdb.* to ‘phoenixdbuser’@’%’ identified by ‘**password**’;
* Run the SQL scripts “CreatePhoenixTables.sql” and “LoadPhoenixTestData1.sql” to create the tables and load test data.

3. Make sure usernames and passwords set in the database match the config.properties file.
4. Install Jira (see note below or Jira website for details on installing Jira)
5. Log in to Jira as administrator and create 2 users “Bob” and “Alex”.


NOTE: See “Jira 7 Essentials” Fourth Edition by Patrick Li for detailed instructions on setting up and configuring MySQL and Jira. Jira is a commercial product. There is a demo version available, but licenses are $10 per year.

## Running the tests

Some of the classes have some basic tests in the “Main” function (specifically the classes Config and Database). To run these tests, simply run the class on the command line (eg java Config *path_to_config_file*). Note that there is no error checking on the config file path. See the source code for details on the testing.

To run a test against Jira: 

1. Make sure your config file is set up properly. The testset name should be set to “testset1” to use the data loaded into the database from the SQL scripts described above.

2. java User *path_to_config_file*

### Break down into end to end tests

Since this is a prototype, there is currently very limited tests of the software itself. There are no unit tests. 

## Bugs and Improvements

There are a number of known issues:

* Frame numbers are hard-coded in Defects.java. The frame number  should be added to test data rows in the database.

* Throw exceptions

* Add ability to update and delete defects

* Expand to include other issues such as epics and stories

* Change service class to an abstract class?

* Timing issues (including hard-coded waits in a few places)!

* Better verification (like verify we are logged out)

* Make all low-level functions capable of handling any locator not just "id"

* Add support for different browsers and browser capabilities


## Contributing

Since this is a prototype, I may limit contributions, but you can always email me at the email address below.

## Authors

* **Bob Brander** - *Initial work* redriver7@att.net

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation version 3. The full license is available here: https://opensource.org/licenses/GPL-3.0 

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

## Acknowledgments

* Very special thanks to my wife who patiently put up with my late night coding after working all day at my day job
* Thanks to all my co-workers at my day job for teaching me so much about test automation


