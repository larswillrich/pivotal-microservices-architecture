package ifaWrap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Component
public class GUISubscriber implements Listener {
	
	Communicator comm = new Communicator(this);
	
	String lastStatus = "noch kein Status";
	
    @RequestMapping("/getStatus")
    public String getStatus() {
    	return lastStatus;
    }
    
	@Autowired
	private SimpMessagingTemplate template;
	
    @RequestMapping("/incrementStatus")
    public String incrementStatus() {
    	comm.sendMessage("incrementStatus");
    	
    	
    	
    	return "incremented Status";
    }

	@Override
	public void setBestellStatus(String message) {
		this.lastStatus = message;
		template.convertAndSend("/topic/greetings", lastStatus);
	}
}