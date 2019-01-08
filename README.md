# spring-rest-cassandra
This sample demonstrates a POC to read data from excel file and insert into the cassandra database.


Assumption is test files are already present on server and using REST url end-point filepath is given as an argument for processing. 
On receiving request on end-point `/processExcelFile` with `filepath` as an argument, server will parse excel file and insert all the records into cassandra DB (keyspace name `javasampleapproach`)
