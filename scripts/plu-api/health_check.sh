CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "No WAS is connected to nginx" >> /home/ubuntu/plu-api/deploy.log
  exit 1
fi

echo "Start health check of WAS at 'http://127.0.0.1:${TARGET_PORT}' ..." >> /home/ubuntu/plu-api/deploy.log

for RETRY_COUNT in 1 2 3 4 5 6 7 8 9 10
do
  echo "#${RETRY_COUNT} trying..." >> /home/ubuntu/plu-api/deploy.log
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:${TARGET_PORT}/actuator/health)

  if [ ${RESPONSE_CODE} -eq 200 ]; then
    echo "New WAS successfully running" >> /home/ubuntu/plu-api/deploy.log
    exit 0
  elif [ ${RETRY_COUNT} -eq 10 ]; then
    echo "Health check failed." >> /home/ubuntu/plu-api/deploy.log
    exit 1
  fi
  sleep 10
done