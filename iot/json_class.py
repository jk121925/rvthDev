import json
import datetime
class iotSensorData:
  def __init__(self, value:'iotSensorData' = None):
    if value and isinstance(value, iotSensorData):
      # super().__init__(value)
      self.sensorLight =value.sensorLight
      self.sensorAudio =value.sensorAudio
      self.sensorTemperature = value.sensorTemperature
      self.sensorHumidity = value.sensorHumidity
      self.sensorAcceleration = value.sensorAcceleration
      self.sensorCreatedAt = datetime.datetime.now()
    else:
      self.sensorLight =0
      self.sensorAudio =0
      self.sensorTemperature = 0
      self.sensorHumidity = 0
      self.sensorAcceleration = 0
      self.sensorCreatedAt = 0
  # def to_dict(self):
  #    sensor = {k: v for k,v in vars(self).items()}
  #    return json.dumps(sensor)
  def __str__(self):
     return str(self.to_dict())



class iotSensorDataBuilder():
  def __init__(self):
      self.instanceIotSensorData = iotSensorData()

  def set_sensor_light(self, value):
      self.instanceIotSensorData.sensorLight = value
      return self
  def set_sensor_audio(self, value):
      self.instanceIotSensorData.sensorAudio = value
      return self

  def set_sensor_temperature(self, value):
      self.instanceIotSensorData.sensorTemperature = value
      return self

  def set_sensor_humidity(self, value):
      self.instanceIotSensorData.sensorHumidity = value
      return self

  def set_sensor_acceleration(self, value):
      self.instanceIotSensorData.sensorAcceleration = value
      return self

  def get_instance(self):
      return self.instanceIotSensorData
