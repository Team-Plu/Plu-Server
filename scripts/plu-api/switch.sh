NOW_TIME="$(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)"

CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "[$NOW_TIME] Nginx currently proxies to ${CURRENT_PORT}." >> /home/ubuntu/plu-api/deploy.log

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "[$NOW_TIME] No WAS is connected to nginx" >> /home/ubuntu/plu-api/deploy.log
  exit 1
fi

echo "set \$service_url http://127.0.0.1:${TARGET_PORT};" >> /home/ubuntu/plu-api/deploy.log | sudo tee /etc/nginx/conf.d/service-url.inc

echo "[$NOW_TIME] Now Nginx proxies to ${TARGET_PORT}." >> /home/ubuntu/plu-api/deploy.log
sudo service nginx reload

echo "[$NOW_TIME] Nginx reloaded." >> /home/ubuntu/plu-api/deploy.log