package uz.pdp.simplecrud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class UserCreateDTO implements Serializable {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    private String surname;
    @NotBlank(message = "email can not be null or empty")
    private String email;
    @NotBlank(message = "password can not be null or empty")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}