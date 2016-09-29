package ifaApi;

import java.io.IOException;
import java.util.List;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.rabbitmq.client.Channel;

//@Configuration
public class RabbitConfig {

	@Bean
	@Primary
	public ConnectionFactory rabbitConnectionFactory() {
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		List<ServiceInfo> serviceInfos = cloud.getServiceInfos();
		AmqpServiceInfo serviceInfo = (AmqpServiceInfo) cloud.getServiceInfo("IFACommunication");
		String serviceID = serviceInfo.getId();
		ConnectionFactory serviceConnector = cloud.getServiceConnector(serviceID, ConnectionFactory.class, null);

		Connection connection = serviceConnector.createConnection();
		Channel channel = connection.createChannel(false);
		try {
			channel.queueDeclare("bestellStatusQueue", false, false, false, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serviceConnector;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}