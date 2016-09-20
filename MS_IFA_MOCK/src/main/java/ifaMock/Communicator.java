package ifaMock;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//rabbitMQ
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class Communicator {

	private final static String QUEUE_NAME = "BestellStatusQueue";
	Connection connection = null;
	Channel channel = null;
	private Listener listener;
	
	public Communicator(Listener l) {
		this.listener = l;
		startRabbit();
		
		listForMessages();
	}

	public void sendMessage(String message){
		try {
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(" [x] Sent '" + message + "'");
	}
	
    private void listForMessages(){
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                throws IOException {
              String message = new String(body, "UTF-8");
              
              if (message.compareTo("incrementStatus") != 0) return;
              
              System.out.println(" [x] Received '" + message + "'");
              String incrementBestellStatus = listener.incrementBestellStatus();
              sendMessage("STATUS: " + incrementBestellStatus);
            }
          };
          try {
			channel.basicConsume(QUEUE_NAME, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	private void closeConnection(){
		try {
			channel.close();
			connection.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	private void startRabbit() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = null;
		channel = null;

		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
