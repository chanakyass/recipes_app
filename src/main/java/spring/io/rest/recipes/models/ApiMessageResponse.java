package spring.io.rest.recipes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiMessageResponse {
    String message;
    LocalDateTime timestamp;

    public static ApiMessageResponse defaultSuccessResponse(){
        return new ApiMessageResponse("Action Successful", LocalDateTime.now());
    }
}
