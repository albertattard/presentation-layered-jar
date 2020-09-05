package demo.micronaut;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@DisplayName( "Message controller" )
public class MessageControllerTest {

  @Inject
  @Client( "/" )
  private RxHttpClient client;

  @Test
  @DisplayName( "should return the message" )
  public void shouldReturnTheOffices() {
    final Message expected = new Message( "Layered JARS are great!!" );

    final HttpRequest<String> request = HttpRequest.GET( "/" );
    final Message actual = client.toBlocking().retrieve( request, Message.class );
    assertEquals( expected, actual );
  }
}
