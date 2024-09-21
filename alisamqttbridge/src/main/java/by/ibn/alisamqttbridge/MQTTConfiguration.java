package by.ibn.alisamqttbridge;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class MQTTConfiguration {

	private Logger log = LoggerFactory.getLogger(MQTTConfiguration.class);

	@Autowired
	@Lazy
	private MqttConnectOptions mqttConnectOptions;
	
	@Bean
	public IMqttClient mqttClient(
			@Value("${mqtt.clientId}") String clientId,
			@Value("${mqtt.hostname}") String hostname, 
			@Value("${mqtt.port}") int port) throws MqttException {
		
		String serverURI = "tcp://" + hostname + ":" + port;
		
		log.trace("MQTT Connection:");
		log.trace(" Server URI: {}", serverURI);
		log.trace(" ClientId: {}", clientId);
		log.trace(" User: {}", mqttConnectOptions.getUserName());

		IMqttClient mqttClient = new MqttClient(serverURI, clientId);

		mqttClient.connect(mqttConnectOptions);

		return mqttClient;
	}	

	@Bean
	@ConfigurationProperties(prefix = "mqtt")
	public MqttConnectOptions mqttConnectOptions() {
		return new MqttConnectOptions();
	}	
}
