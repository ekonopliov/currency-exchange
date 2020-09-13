package lt.kono.exchange.validations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Violation domain class
 *
 * @author ekonopliov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Violation {

  private String fieldName;

  private String message;

}