import paho.mqtt.client as mqtt
from kafka import KafkaProducer
import json
from pprint import pprint

bootstrap_servers = 'localhost:9092'
topic = 'rovothome_sql_temp'

producer = KafkaProducer(bootstrap_servers=bootstrap_servers)
def on_connect(client, userdata, flags, rc):
  if rc == 0:
    print("connected OK")
  else:
    print("Bad connection Returned code=", rc)

def on_disconnect(client, userdata, flags, rc=0):
  print(str(rc))

def on_subscribe(client, userdata, mid, granted_qos):
  print("subscribed: " + str(mid) + " " + str(granted_qos))

def on_message(client, userdata, msg):
  MQTT_cloud_message = str(msg.payload.decode("utf-8"))
  MQTT_json_message = json.loads(MQTT_cloud_message)
  iot_json_data = json.loads(MQTT_json_message)
  print(iot_json_data['sensor'])

  key = ""
  value = ""
  if int(iot_json_data['sensor']['sensorHumidity']) > 50 and int(iot_json_data['sensor']['sensorTemperature']) > 50:
    message = {
        'schema': {
            'type': 'struct',
            'fields': [
                {'field': 'name', 'type': 'string'},
                {'field': 'status', 'type': 'boolean'}
            ]
        },
        'payload': {
            'name': 'sangha',
            'status': True
        }
    }
    key = str(1).encode('utf-8')
    value = json.dumps(message).encode('utf-8')
    producer.send(topic, key=key, value=value)
  elif int(iot_json_data['sensor']['sensorHumidity']) < 50 and int(iot_json_data['sensor']['sensorTemperature']) < 50:
      message = {
          'schema': {
              'type': 'struct',
              'fields': [
                  {'field': 'name', 'type': 'string'},
                  {'field': 'status', 'type': 'boolean'}
              ]
          },
          'payload': {
              'name': 'jonggeun',
              'status': False
          }
      }
      key = str(2).encode('utf-8')
      value = json.dumps(message).encode('utf-8')
      producer.send(topic, key=key, value=value)

  # if int(iot_json_data['sensor']['sensorHumidity']) >50 and int(iot_json_data['sensor']['sensorTemperature'])>50:
  #   message = {
  #     'name': "sangha",
  #     'status': True
  #   }
  #   key = str(1).encode('utf-8')
  #   value = json.dumps(message).encode('utf-8')
  #   producer.send(topic, key=key, value=value)
  # elif int(iot_json_data['sensor']['sensorHumidity']) <50 and int(iot_json_data['sensor']['sensorTemperature'])<50:
  #   message = {
  #     'name': "jonggeun",
  #     'status': False
  #   }
  #   key = str(2).encode('utf-8')
  #   value = json.dumps(message).encode('utf-8')

  #   producer.send(topic, key=key, value=value)



client = mqtt.Client()

client.on_connect = on_connect
client.on_disconnect = on_disconnect
client.on_subscribe = on_subscribe
client.on_message = on_message

client.connect("146.56.170.70", 1883)

client.subscribe('iot/switch', 0)
client.loop_forever()