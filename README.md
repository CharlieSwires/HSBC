HSBC
----
<p>in git bash</p>
<p>git clone https://github.com/CharlieSwires/HSBC</p>

build
-----
<p>mvn package</p>

<p>produces HSBC.war in target</p>


deploy
------
<p>docker build --tag hsbc:latest .</p>
<p>docker run  --env-file ./env.list --name container5 -d -p 8888:8080 hsbc:latest</p>


browser
-------
<p>http://localhost:8888/HSBC</p>

RESTful
-------

<p>http://localhost:8888/HSBC/UserEntry/add</p>
<p>http://localhost:8888/HSBC/UserEntry/delete/{firstName}/{lastName}</p><p>http://localhost:8888/HSBC/UserEntry/delete/{id}</p>
<p>http://localhost:8888/HSBC/UserEntry/get/{firstName}/{lastName}</p>
<p>http://localhost:8888/HSBC/UserEntry/get/{lastName} -- returns list of JSON</p>
<p>http://localhost:8888/HSBC/UserEntry/getId/{id} -- returns single JSON</p>
<p>http://localhost:8888/HSBC/UserEntry/getFirst/{firstname} -- returns list of JSON</p>
<p>http://localhost:8888/HSBC/UserEntry/getAllCSV -- returns CSV file</p>
<p>http://localhost:8888/HSBC/UserEntry/getAll -- returns list of JSON</p>
<p>http://localhost:8888/HSBC/UserEntry/loadAll</p>

