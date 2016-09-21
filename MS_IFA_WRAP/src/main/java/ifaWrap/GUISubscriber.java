package ifaWrap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Component
public class GUISubscriber implements Listener {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	Communicator comm = new Communicator(this);
	
    @RequestMapping("/getStatus")
    public StatusMessage getStatus() {
    	return StatusMessage.message;
    }
    
    @RequestMapping("/incrementStatus")
    public String incrementStatus() {
    	comm.sendMessage("incrementStatus");
    	return "incremented Status";
    }

	@Override
	public void setBestellStatus(String message) {
		StatusMessage.message.setName(message);
	}

	@Override
	public void setProgressBarStatus(int percentage) {
		StatusMessage.message.setPercentage(percentage);
	}
	
	@Override
	public void sendUpdate(){
		template.convertAndSend("/topic/greetings", StatusMessage.message);
	}
}