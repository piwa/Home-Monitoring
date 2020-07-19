#include <Wire.h>
#include <Adafruit_BMP085.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Arduino_JSON.h>

#include "config.h"
#include "credentials.h"

Adafruit_BMP085 bmp;
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);
JSONVar temperatureMessageObject;

const char* ssid     = WIFI_SSID;
const char* password = WIFI_PASSWD;
const char* mqttHost = MQTT_HOST;
const char* mqttTempTopic = MQTT_TEMPERATURE_TOPIC;

const char* i2cTemperatureSensor = I2C_TEMPERATURE_SENSOR;
const char* i2cSensorConnection = I2C_SENSOR_CONNECTION;
const char* location = LOCATION;

const int readingInterval = READING_INTERVAL;
const int temperatureAdjustment = TEMPERATURE_ADJUSTMENT;

void setup() {
  Serial.begin(9600);

  if (!bmp.begin()) {
    Serial.println("Could not find a valid BMP085 sensor, check wiring!");
  }

  setupWifi();

  mqttClient.setServer(mqttHost, 1883);

  setupTemperatureJsonObject();
}

void loop() {

  float temperature = bmp.readTemperature();
  temperature = temperature - temperatureAdjustment;

  if (!connect()) {
    Serial.println("Could not connect");
  }
  else {
    Serial.println("Sending the temperature");

    temperatureMessageObject["time"] = String(millis());
    temperatureMessageObject["value"] = temperature;

    String jsonString = JSON.stringify(temperatureMessageObject);

    Serial.println(jsonString);
    mqttClient.publish(mqttTempTopic, jsonString.c_str());

    disconnect();
    Serial.println("wait for next round");
  }

  delay(readingInterval);
}

bool connect() {

  Serial.print("Connecting to ");
  Serial.println(mqttHost);

  String clientId = "ESP8266_" + String(location);

  while (!mqttClient.connect(clientId.c_str())) {
    Serial.print("MQTT connection failed. State: ");
    Serial.println(mqttClient.state());
    return false;
  }

  Serial.println("MQTT connected!");
  return true;
}


void disconnect() {
  Serial.println("closing connection");
  mqttClient.disconnect();

  if (wifiClient.connected()) {
    wifiClient.stop();
  }
}

void setupWifi() {
  delay(10);

  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void setupTemperatureJsonObject() {
  temperatureMessageObject["measured_entity"] = "Temperature";
  temperatureMessageObject["sensor_id"] = "0";
  temperatureMessageObject["sensor_type"] = i2cTemperatureSensor;
  temperatureMessageObject["sensor_connection"] = i2cSensorConnection;
  temperatureMessageObject["location"] = location;
  
}
