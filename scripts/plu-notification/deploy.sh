NOW_TIME="$(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)"

HOST_NAME=$(cat /etc/hostname)
TARGET_PORT=8083

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "[$NOW_TIME] Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

if [ ${HOST_NAME} == "plu-prod-server" ]; then
  nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=prod /home/ubuntu/plu-notification/*.jar
  echo "[$NOW_TIME] Now new WAS runs at ${TARGET_PORT}."
  exit 0
else
  nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev /home/ubuntu/plu-notification/*.jar
  echo "[$NOW_TIME] Now new WAS runs at ${TARGET_PORT}."
  exit 0
fi