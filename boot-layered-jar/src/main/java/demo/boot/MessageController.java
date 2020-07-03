package demo.boot;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessageController {

  @GetMapping( "/" )
  public Message message() {
    return new Message( "Layered JARS are great!!" );
  }

}
