package ifaWrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class GUISubscriber {

	@Autowired
	private SimpMessagingTemplate template;

	Communicator comm;

	@CrossOrigin(origins = "*")
	@RequestMapping("/getStatus")
	public StatusMessage getStatus() {
		return StatusMessage.message;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/incrementStatus")
	public String incrementStatus() {
		comm.sendMessage("incrementStatus");
		return "incremented Status";
	}

	public void setBestellStatus(String message) {
		StatusMessage.message.setName(message);
	}

	public void setProgressBarStatus(int percentage) {
		StatusMessage.message.setPercentage(percentage);
	}

	public void sendUpdate() {
		template.convertAndSend("/getOrderUpdate", StatusMessage.message);
	}

	public void setCallBackForIncrementStatus(Communicator communicator) {
		this.comm = communicator;
	}
}