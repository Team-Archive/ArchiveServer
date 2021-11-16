#!/bin/sh

REPOSITORY=/home/ec2-user/app
cd $REPOSITORY

APP_NAME=archive
JAR_NAME=$(ls $REPOSITORY/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME
echo $JAR_NAME

CURRENT_PID=$(pgrep -f $JAR_NAME)
echo $CURRENT_PID

if [ -z "$CURRENT_PID" ]; then
  echo "> kill nothing"
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH deploy"
nohup java -jar -Dspring.profiles.active=prd $JAR_PATH >~/log/archive.log 2>&1 &