package spring.io.rest.recipes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiMessageResponse {
    Long generatedId;
    String message;
    LocalDateTime timestamp;

    public ApiMessageResponse(String message){
        this.generatedId = null;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiMessageResponse(Long generatedId){
        this.generatedId = generatedId;
        this.message = "Action successful";
        this.timestamp = LocalDateTime.now();
    }

    public ApiMessageResponse(){
        this.generatedId = null;
        this.message = "Action successful";
        this.timestamp = LocalDateTime.now();
    }

    public static ApiMessageResponse defaultSuccessResponse(){
        return new ApiMessageResponse();
    }

    public static ApiMessageResponse defaultCreationSuccessResponse(Long generatedId){
        return new ApiMessageResponse(generatedId);
    }
}
