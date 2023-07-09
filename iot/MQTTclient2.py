import paho.mqtt.client as mqtt
from kafka import KafkaProducer
import json
from pprint import pprint

bootstrap_servers = 'localhost:9092'
topic = 'rovothome_temp'

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
  temp_message = str(msg.payload.decode("utf-8"))
  json_message = json.loads(temp_message)
  pprint(json_message)
  key = f'key-{"sangha"}'.encode('utf-8')
  value = f'value-{temp_message}'.encode('utf-8')
  producer.send(topic, key=key, value=value)



client = mqtt.Client()

client.on_connect = on_connect
client.on_disconnect = on_disconnect
client.on_subscribe = on_subscribe
client.on_message = on_message

client.connect("146.56.170.70", 1883)

client.subscribe('iot/switch', 0)
client.loop_forever()