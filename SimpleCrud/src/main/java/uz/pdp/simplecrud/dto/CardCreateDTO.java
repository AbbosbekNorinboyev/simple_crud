package uz.pdp.simplecrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CardCreateDTO implements Serializable {
    @NotBlank(message = "cardNumber can not be null or empty")
    private String cardNumber;
    @NotNull(message = "cardCode can not be null or empty")
    private String cardCode;
    @NotNull(message = "balance can not be null or empty")
    private Double balance;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}