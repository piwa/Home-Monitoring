# Home-Monitoring Server

# Telegraf, InfluxDB, MQTT, Grafana
Configured with restart policy always

## Configuration 
telegraf.conf
Configure path to telegraf.conf ind docker-compose.yml

## Install
docker-compose up -d


# Home-Monitoring Service

## Configuration 
home-monitoring.service

## Install 
sudo cp home-monitoring.service /etc/systemd/system/home-monitoring.service


# InfluxDB usage
docker exec -it home_monitoring_influxdb_1 influx