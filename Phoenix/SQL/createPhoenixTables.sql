# Phoenix table definitions         6/23/2018
# Copyright 2018 B. Brander/AST

use phoenixdb;

CREATE TABLE IF NOT EXISTS UIMap
(
	map_id        INT NOT NULL AUTO_INCREMENT,
    pagename      varchar (30) NOT NULL,
    fieldname     varchar (30) NOT NULL,
    bylocator	  varchar (30) NOT NULL,
    field_locator varchar (30) NOT NULL,     
    description   varchar (100),
    PRIMARY KEY (map_id)
); 

CREATE TABLE IF NOT EXISTS TestData
(
	testdata_id        	INT NOT NULL AUTO_INCREMENT,
	testset  		   	varchar (30) NOT NULL,
	testcase_descr	   	varchar (200) NOT NULL,
	action_type       	varchar (30) NOT NULL,
	project				varchar (30) NOT NULL,
	issue_type			varchar (30) NOT NULL,
	epic_name			varchar (30),
	issue_summary		varchar (50) NOT NULL,
	issue_description	varchar (500),
	priority           	varchar (30),
	labels				varchar (50),
	environment			varchar (100),
	linked_issued		varchar (50),
	issue				varchar (500) NOT NULL,
	assignee			varchar (30),
	epic_link			varchar (30),
	sprint				varchar (30),
	PRIMARY KEY	(testdata_id)
);

CREATE TABLE IF NOT EXISTS TestResults
(
	testresults_id INT NOT NULL AUTO_INCREMENT,
	username	   varchar (30) NOT NULL,
	testset  	   varchar (30) NOT NULL,
	trdatetime     DATETIME NOT NULL,
	testdata_id    INT NOT NULL,
	tserver        varchar (30) NOT NULL,
	results        varchar (30) NOT NULL,
	PRIMARY KEY (testresults_id)
);

	