package ifaApi;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@SendTo("/getOrderUpdate")
	public String greeting() throws Exception {
		return Bestellung.getAllOrdersAsJSON();
	}

}