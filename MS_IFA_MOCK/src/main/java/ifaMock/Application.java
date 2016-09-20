package ifaMock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		final Bestellung bestellung = new Bestellung();
		new Communicator(new Listener() {
			
			@Override
			public String incrementBestellStatus() {
				bestellung.increment();
				return bestellung.now().toString();
			}
		});
	}
}