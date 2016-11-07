package ifaApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class RESTAPI {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	Communicator comm;

	@CrossOrigin(origins = "*")
	@RequestMapping("/getStatus")
	public Bestellung getStatus(@RequestParam("id") String id) {
		System.out.println("REST: getStatus");
		Bestellung.bestellungen.remove(id);
		comm.sendMessage("getOrder=" + id);
		return comm.getOrderAfterTime(id);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/getAll")
	public String getAllOrders() {
		System.out.println("REST: getAll");
		return Bestellung.getAllOrdersAsJSON();
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/newOrder")
	public void newOrder() {
		System.out.println("REST: newOrder");
		comm.sendMessage("newOrder");
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/deleteOrder")
	public void deleteOrder(@RequestParam("id") String id) {
		System.out.println("REST: deleteOrder");
		Bestellung.bestellungen.remove(id);
		comm.sendMessage("deleteOrder=" + id);
		sendUpdate();
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/incrementStatus")
	public Bestellung incrementStatus(@RequestParam("id") String id) {
		System.out.println("REST: incrementStatus");
		Bestellung.bestellungen.remove(id);
		comm.sendMessage("incrementStatus=" + id);
		return comm.getOrderAfterTime(id);
	}

	public void sendUpdate() {
		template.convertAndSend("/getOrderUpdate", Bestellung.getAllOrdersAsJSON());
	}
}