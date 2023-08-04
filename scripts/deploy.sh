#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fla java | grep hayan | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 환경변수 설정"

export REDIS_HOST="localhost"
export REDIS_PORT="6379"
export API_KEY="qQpswERaNSg1ifEA7rbze6oNJrej4JJW"
export SPRING_PROFILES_ACTIVE="prod"
export RDS_DRIVER="com.mysql.cj.jdbc.Driver"
export RDS_URL="jdbc:mysql://172.31.0.142:3306/dfdb"
export RDS_USERNAME="appuser"
export RDS_PASSWORD="tjrgus97"
export SECRET_KEY="SECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRETSECRET"

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*SNAPSHOT.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME >> $REPOSITORY/nohup.out 2>&1 &