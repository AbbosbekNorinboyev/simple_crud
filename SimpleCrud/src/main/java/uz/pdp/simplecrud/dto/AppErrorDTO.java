package uz.pdp.simplecrud.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AppErrorDTO {
    private String filed;
    private String message;
}
