mvn clean install
heroku ps:scale web=1
web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/ml-training-platform-0.1.jar