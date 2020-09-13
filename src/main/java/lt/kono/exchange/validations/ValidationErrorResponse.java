package lt.kono.exchange.validations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation failure response domain class
 *
 * Returns list of detailed violations information
 *
 * @author ekonopliov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {

  private List<Violation> violations = new ArrayList<>();

}