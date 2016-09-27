package ifaWrap;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@SendTo("/getOrderUpdate")
	public StatusMessage greeting() throws Exception {
		return StatusMessage.message;
	}

}