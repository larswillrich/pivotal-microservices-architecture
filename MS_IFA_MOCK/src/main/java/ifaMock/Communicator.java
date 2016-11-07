package ifaMock;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableScheduling
@RabbitListener(queues = "bestellStatusQueue")
public class Communicator {

	private final static String QUEUE_NAME = "bestellStatusQueue";

	@PostConstruct
	public void populateMovieCache() {
		sendMessage("hello bestellStatusQueue from mock!!");
	}

	@RabbitHandler
	public void process(@Payload String message) {

		System.out.println(" [x] IFA Received '" + message + "'");

		Bestellung bestellung = null;

		// increment Order
		if (message.startsWith("incrementStatus=")) {
			String id = message.replace("incrementStatus=", "");
			bestellung = Bestellung.bestellungen.get(id);
			bestellung.increment();
		}

		// new Order
		if (message.startsWith("newOrder")) {
			String id = message.replace("newOrder", "");
			bestellung = new Bestellung();
			Bestellung.bestellungen.put(bestellung.getID(), bestellung);
		}

		// get Order by ID
		if (message.startsWith("getOrder=")) {
			String id = message.replace("getOrder=", "");
			bestellung = Bestellung.bestellungen.get(id);
		}
				
		// get Order by ID
		if (message.startsWith("deleteOrder=")) {
			String id = message.replace("deleteOrder=", "");
			Bestellung.bestellungen.remove(id);
		}

		if (bestellung == null)
			return;

		// send order
		ObjectMapper mapper = new ObjectMapper();
		try {
			sendMessage(mapper.writeValueAsString(bestellung));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String message) {
		this.rabbitTemplate.convertAndSend(QUEUE_NAME, message);
		System.out.println(" [x] mock Sent '" + message + "'");
	}
}
