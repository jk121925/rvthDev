package kafka.producer;

import kafka.producer.HardWareUsage.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class ProducerApplication {
	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);


		final String TOPIC_NAME = "garden_sensor_data";
		String bootstrapServer = "localhost" + ":9092";
		System.out.println("bootstrap server : " + bootstrapServer);
//
//
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
//		/*
//		JSONSERIALIZER ADD_TYPE_INFO_HEADERS can serializer infer type of JSON Object
//		 */
		properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,HardWareUsageDAO.class.getName());
		KafkaProducer<String, HardWareUsageDAO> producer = new KafkaProducer<>(properties);


		// object to HardWareUsageDAO
		TotalCpuDetail cpuDetail = null;
		TotalMemDetail memDetail = null ;
		TotalDiskDetail diskDetail = null;
		TotalNetworkDetail networkDetail = null;
		List<TopProcessDetail> topRateProcess = new ArrayList<>();
		HardWareUsageDAO hardWareUsageDAO = new HardWareUsageDAO();
		//---------------------------------------------------
		hardWareUsageDAO.setEC2Number("JKMAC");
		//--------------------------------------------------- 
		// parsing "TOP COMMAND"
		int staringPoint=0;
		while (true) {
			if (staringPoint == 0) {
				staringPoint++;
				continue;
			}

			String sendOutStr = ""; // the output string to be sent to kafka broker
			String temp;
			Process p;


			// TOP COMMAND HANDLER
			try {

				p = Runtime.getRuntime().exec("top -l 2");
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

				

				while ((temp = br.readLine()) != null) {
					
					String[] temp_str = temp.split("\\s+");
					// // PARSING
					if (temp_str[0].equals("CPU")) { // parsing CPU
						cpuDetail = new TotalCpuDetail(Float.parseFloat(temp_str[4].replace("%", "")), Float.parseFloat(temp_str[6].replace("%", "")));
					
					
					} else if (temp_str.length >6 && temp_str[6].equals("PhysMem:")) { // parsing Phys5Mem
						memDetail = new TotalMemDetail(Float.parseFloat(temp_str[3].replace("M", "").replace("(", "")), Float.parseFloat(temp_str[5].replace("M","")));
						System.out.println(memDetail);
					} else if (temp_str.length >4 && temp_str[0].equals("Networks:")){
						networkDetail = new TotalNetworkDetail(Integer.parseInt(temp_str[2].split("/")[0]),Integer.parseInt(temp_str[4].split("/")[0]));
						System.out.println(networkDetail);
					}
					
					else if (temp_str.length > 3 && temp_str[0].equals("Disks:")){
						diskDetail = new TotalDiskDetail(Float.parseFloat(temp_str[1].split("/")[0])/100000,Float.parseFloat(temp_str[3].split("/")[0])/100000);
						break;
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}



			hardWareUsageDAO.setCPU(cpuDetail).setDISK(diskDetail).setMEM(memDetail).setNETWORK(networkDetail);


			// sending kafka
			ProducerRecord<String, HardWareUsageDAO> record = new ProducerRecord<String, HardWareUsageDAO>(TOPIC_NAME, hardWareUsageDAO);

			try {
				producer.send(record);
				System.out.println(hardWareUsageDAO);
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}



		}
	}
}
