package spring.io.rest.recipes.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProxyDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String email;
}
