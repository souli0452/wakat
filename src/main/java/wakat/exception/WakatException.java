package wakat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@AllArgsConstructor
public class WakatException {
    private String message;
    private HttpStatus httpStatus;
}
