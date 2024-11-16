package uz.pdp.simplecrud.validation;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import uz.pdp.simplecrud.dto.ErrorDTO;
import uz.pdp.simplecrud.dto.UserCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidation {
    public List<ErrorDTO> errorDTOS(UserCreateDTO userCreateDTO) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (StringUtils.isBlank(userCreateDTO.getName())) {
            errors.add(new ErrorDTO("name", "name can not be null or empty"));
        }
        if (StringUtils.isBlank(userCreateDTO.getEmail())) {
            errors.add(new ErrorDTO("email", "email can not be null or empty"));
        }
        if (StringUtils.isBlank(userCreateDTO.getPassword())) {
            errors.add(new ErrorDTO("password", "password can not be null or empty"));
        }
        return errors;
    }
}
