#!/bin/bash
cd /usr/local/codedeploy/fi-testing-automation/
cp * /opt/fi-testing-automation
cd /opt/fi-testing-automation
if [ -n "$var" ]
then
      echo java PID is not Null
      kill -9 $var
else
      echo java PID is Null
fi
java -Xms2048m -Xmx2048m -Dserver.port=8080 -jar /opt/ficoreapi/coreapi-1.0-SNAPSHOT-jar-with-dependencies.jar > /dev/null 2> /dev/null < /dev/null &
sleep 10
pgrep -f java
if [ $? -eq 0 ]; then
   echo OK
else
   echo FAIL
   echo Restarting jar
   java -Xms2048m -Xmx2048m -Dserver.port=8080 -jar /opt/ficoreapi/coreapi-1.0-SNAPSHOT-jar-with-dependencies.jar > /dev/null 2> /dev/null < /dev/null &
fi