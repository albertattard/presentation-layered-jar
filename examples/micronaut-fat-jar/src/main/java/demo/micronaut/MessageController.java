package demo.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller()
public class MessageController {

  @Get
  public Message message() {
    return new Message( "Layered JARS are great!!" );
  }

}
