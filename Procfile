mvn clean install
heroku ps:scale web=1
web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/ml-training-platform-0.1.jar

-Dspring.datasource.url=jdbc:mysql://us-cdbr-east-04.cleardb.com:3306/heroku_bfd19da377d3441?reconnect=true&user=b219bab2ac4594&sslmode=require&ssl=true&password=814dbb0b&useUnicode=true