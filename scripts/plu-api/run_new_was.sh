NOW_TIME="$(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)"

HOST_NAME=$(cat /etc/hostname)
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "[$NOW_TIME] No WAS is connected to nginx" >> /home/ubuntu/plu-api/deploy.log
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "Kill WAS running at ${TARGET_PORT}." >> /home/ubuntu/plu-api/deploy.log
  sudo kill ${TARGET_PID}
fi

if [ ${HOST_NAME} == "plu-prod-server" ]; then
  nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=prod /home/ubuntu/plu-api/*.jar >> /home/ubuntu/plu-api/deploy.log 2>/home/ubuntu/plu-api/deploy_err.log &
  echo "Now new WAS runs at ${TARGET_PORT}." >> /home/ubuntu/plu-api/deploy.log
  exit 0
else
  nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev /home/ubuntu/plu-api/*.jar >> /home/ubuntu/plu-api/deploy.log 2>/home/ubuntu/plu-api/deploy_err.log &
  echo "Now new WAS runs at ${TARGET_PORT}." >> /home/ubuntu/plu-api/deploy.log
  exit 0
fi