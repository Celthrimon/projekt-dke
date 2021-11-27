package at.jku.notificationservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Testcontroller {
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

}
