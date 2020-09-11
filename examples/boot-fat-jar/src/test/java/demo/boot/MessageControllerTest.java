package demo.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Message controller")
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("should return the message")
  public void shouldReturnTheOffices() throws Exception {
    final Message expected = new Message("Layered JARS are great!!");

    mockMvc.perform(get("/"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isMap())
      .andExpect(jsonPath("$.message", is(expected.getMessage())))
    ;
  }
}
