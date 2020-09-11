package demo.boot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  @GetMapping("/")
  public Message message() {
    return new Message("Layered JARS are great!!");
  }

}
