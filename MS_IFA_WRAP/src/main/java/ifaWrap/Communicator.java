package ifaWrap;

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
	private GUISubscriber guiSubscriber;

	@PostConstruct
	public void populateMovieCache() {
		sendMessage("hello bestellStatusQueue from wrap");
		guiSubscriber.setCallBackForIncrementStatus(this);
	}

	@RabbitHandler
	public void process(@Payload String message) {
		if (!message.startsWith("forWrap:"))
			return;
		message = message.replace("forWrap:", "");
		System.out.println(" [x] Received '" + message + "'");

		String[] split = message.split(":");
		guiSubscriber.setBestellStatus(split[0]);
		guiSubscriber.setProgressBarStatus(Integer.valueOf(split[1]));
		guiSubscriber.sendUpdate();
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String message) {
		this.rabbitTemplate.convertAndSend(QUEUE_NAME, message);
		System.out.println(" [x] wrap Sent '" + message + "'");
	}
}
