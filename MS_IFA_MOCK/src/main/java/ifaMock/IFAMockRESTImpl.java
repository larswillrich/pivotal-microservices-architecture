package ifaMock;

import org.springframework.web.bind.annotation.RestController;
import ifaMock.Bestellung.STATUS;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class IFAMockRESTImpl {
	
	Bestellung bestellung = new Bestellung(STATUS.values()[0]);
	
    @RequestMapping("/getStatus")
    public String getStatus() {
    	return bestellung.now().toString();
    }
    
    @RequestMapping("/incrementStatus")
    public String incrementStatus() {
        return bestellung.increment().toString();
    }
}