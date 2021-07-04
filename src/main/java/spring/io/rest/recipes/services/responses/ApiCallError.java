package spring.io.rest.recipes.services.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCallError {
    private int statusCode;
    private String URI;
    private String message;
    private LocalDateTime timestamp;
    private List<String> details;
}
