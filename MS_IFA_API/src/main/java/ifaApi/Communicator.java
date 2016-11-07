package ifaApi;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableScheduling
@RabbitListener(queues = "bestellStatusQueue")
public class Communicator {

	private final static String QUEUE_NAME = "bestellStatusQueue";

	@PostConstruct
	public void populateMovieCache() {
	}

	@Autowired
	RESTAPI api;

	@RabbitHandler
	public void process(@Payload String message) {

		System.out.println(" [x] API Received '" + message + "'");

		// Get Order from json object
		ObjectMapper mapper = new ObjectMapper();
		Bestellung order = null;
		try {
			order = mapper.readValue(message, Bestellung.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (order == null)
			return;

		Bestellung.bestellungen.put(order.getID(), order);

		api.sendUpdate();
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String message) {
		this.rabbitTemplate.convertAndSend(QUEUE_NAME, message);
		System.out.println(" [x] api Sent '" + message + "'");
	}

	public Bestellung getOrderAfterTime(String id) {
		Bestellung b = null;
		int counter = 0;
		do {
			b = Bestellung.bestellungen.get(id);
			if (counter++ == 40)
				break;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (b == null);
		return b;
	}
}
