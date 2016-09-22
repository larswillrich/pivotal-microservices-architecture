package ifaWrap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Subscriber {

	@CrossOrigin(origins = "*")
	@RequestMapping("/subscribe")
	public StatusMessage getStatus() {

		return StatusMessage.message;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/helloWorld")
	public String helloWorld() {
		return "hello World";
	}
}