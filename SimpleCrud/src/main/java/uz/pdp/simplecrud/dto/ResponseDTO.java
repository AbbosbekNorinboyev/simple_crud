package uz.pdp.simplecrud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private int code;
    private String message;
    private Boolean success;
    private T data;
    private List<ErrorDTO> errors;

}