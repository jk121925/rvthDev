import json, random, datetime
from json_class import iotSensorData, iotSensorDataBuilder
from pprint import pprint

class iotSwitchData(iotSensorData):
  def __init__(self):
    super().__init__()
    self.switchBtnA = 0
    self.switchBtnB = 0
    self.switchBtnC = 0
    self.switchLedR = 0
    self.switchLedG = 0
    self.switchLedB = 0
  def iotSensorDataInjection(self,value):
      super().__init__(value)
      return self
  def to_dict(self):
      sensor = {k: v for k, v in vars(self).items() if k.__contains__('sensor')}
      switch = {k: v for k, v in vars(self).items() if k.__contains__('switch')}
      serialized_sensor_data = dict(map(lambda item: (item[0], item[1].isoformat()) if isinstance(item[1], datetime.datetime) else item, sensor.items()))
      serialized_switch_data = switch
      ret = {'sensor': serialized_sensor_data, 'switch': switch}
      return json.dumps(ret)
        
      
  

class iotSwitchDataBuilder:
  def __init__(self):
      self.instanceIotSwitchData = iotSwitchData()
  def set_iotSensorData(self,value):
      self.instanceIotSwitchData.iotSensorDataInjection(value)
      return self
  def set_switch_btn_a(self, value):
      self.instanceIotSwitchData.switchBtnA = value
      return self
  def set_switch_btn_b(self, value):
      self.instanceIotSwitchData.switchBtnB = value
      return self
  def set_switch_btn_c(self, value):
      self.instanceIotSwitchData.switchBtnC = value
      return self
  def set_switch_LED_R(self, value):
      self.instanceIotSwitchData.switchLedR = value
      return self
  def set_switch_LED_G(self, value):
      self.instanceIotSwitchData.switchLedG = value
      return self
  def set_switch_LED_B(self, value):
      self.instanceIotSwitchData.switchLedB = value
      return self
  def get_instance(self):
      return self.instanceIotSwitchData

def genRendomData():
  iotSensorData = iotSensorDataBuilder()\
    .set_sensor_acceleration(random.randint(1,256))\
    .set_sensor_audio(random.randint(1,256))\
    .set_sensor_humidity(random.randint(1,256))\
    .set_sensor_light(random.randint(1,256))\
    .set_sensor_temperature(random.randint(1,256))\
    .get_instance()

  iotSwitchData = iotSwitchDataBuilder()\
    .set_iotSensorData(iotSensorData)\
    .set_switch_LED_R(random.randint(1,256))\
    .set_switch_LED_G(random.randint(1,256))\
    .set_switch_LED_B(random.randint(1,256))\
    .set_switch_btn_a(random.randint(1,256))\
    .set_switch_btn_b(random.randint(1,256))\
    .set_switch_btn_c(random.randint(1,256))\
    .get_instance()
  return iotSwitchData
  

