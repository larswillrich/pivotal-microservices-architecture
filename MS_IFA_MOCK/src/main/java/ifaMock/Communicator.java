package ifaMock;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RabbitListener(queues = "bestellStatusQueue")
public class Communicator {

	private final static String QUEUE_NAME = "bestellStatusQueue";

	@Autowired
	private Bestellung listener;

	@PostConstruct
	public void populateMovieCache() {
		sendMessage("hello bestellStatusQueue from mock!!");
	}

	@RabbitHandler
	public void process(@Payload String message) {
		if (message.compareTo("incrementStatus") != 0)
			return;

		System.out.println(" [x] Received '" + message + "'");
		String incrementBestellStatus = listener.increment().toString();
		sendMessage("forApi:" + incrementBestellStatus + ":" + listener.getPercentage());
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String message) {
		this.rabbitTemplate.convertAndSend(QUEUE_NAME, message);
		System.out.println(" [x] mock Sent '" + message + "'");
	}

}
