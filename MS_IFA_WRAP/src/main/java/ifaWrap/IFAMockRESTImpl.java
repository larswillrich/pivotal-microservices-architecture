package ifaWrap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class IFAMockRESTImpl {
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}