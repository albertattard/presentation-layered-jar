package demo.micronaut;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class Message {

  private String message;
}
