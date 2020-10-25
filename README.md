# Project name

TidesServer

# Description

This web application serves as a backend for the "Tides in Magadan" mobile Android app (link to the repository). 
It performs the following functions:
1) parses from the web sites for schedule information about:
  * current weather in Magadan (every 30 minutes);
  * the tides in the Bay of Nagaev (each next month);
2) stores information downloaded from websites in a database;
3) provides the REST API to the client application. Returns the following data in JSON format:
  * current weather in Magadan;
  * table of tides in the Bay of Nagaev.

# Technologies in the project
Java, Spring, JSOUP, PostgreSQL, REST

# Authors
Mikhail Ageenko

e-mail: ageenkomihael@mail.ru
