FROM tomcat:9.0
ADD target/HSBC.war /usr/local/tomcat/webapps
ADD hsbc.csv /usr/local/tomcat
CMD ["npm i dotenv", "run"]
CMD ["catalina.sh", "run"]
