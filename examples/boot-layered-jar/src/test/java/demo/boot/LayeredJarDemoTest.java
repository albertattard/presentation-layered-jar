package demo.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName( "Layered Jar demo" )
@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
public class LayeredJarDemoTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName( "should return 200 when retrieving the message" )
  public void shouldReturn200WhenRetrievingMessage() {
    assertThat( restTemplate.getForEntity( "/", String.class ) )
      .matches( r -> r.getStatusCode() == HttpStatus.OK );
  }
}
