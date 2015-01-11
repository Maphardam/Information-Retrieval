Google++
=======

This repository contains the source code developed in the course "Information Retrieval".

Compiling & execution
==================================================================================================
To create this project with maven excute the command: mvn package .
With java -jar "target/GooglePP-2.jar" you can execute this application.
(The number after GooglePP- should indicate the assignment)


Usage
===================================================================================================
There are several commands, which can be used in the console-like environment of this application.
Commands:

help
	The help command provides you with a brief description of all the commands and explains their parameters.

create
	To create a new index type create INDEXPATH XMLPATH, where INDEXPATH is the path, where the
	index should be saved to. URL is the url, where the crawling should start and DEPTH is the recursion depth of the crawling.
	
load
	With load INDEXPATH you can load an already existing index, which you have created with lucene.

search
	To use this command, you can type search QEURY. This command is optional, it says the
	application explicitely, that everything after it should be treated as a search query,
	to look for documents in the loaded index. You don't have to use this command, if your query 
	is not interfering with any of the commands, which this application uses.
	If this is the case, typing in the QUERY is enough to perform a search.
	Using this, the best ten ranked documnts are printed onto the screen.
	
exit
	This command is used to close the application.
