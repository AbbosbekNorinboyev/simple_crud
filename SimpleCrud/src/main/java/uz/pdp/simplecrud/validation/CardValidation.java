package uz.pdp.simplecrud.validation;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardValidation {
    public List<ErrorDTO> errorDTOS(CardCreateDTO cardCreateDTO) {
        ArrayList<ErrorDTO> errors = new ArrayList<>();
        if (StringUtils.isBlank(cardCreateDTO.getCardNumber())) {
            errors.add(new ErrorDTO("cardNumber", "can not be null or empty"));
        }
        if (StringUtils.isBlank(cardCreateDTO.getCardCode())) {
            errors.add(new ErrorDTO("cardCode", "can not be null or empty"));
        }
        if (StringUtils.isBlank(String.valueOf(cardCreateDTO.getBalance()))) {
            errors.add(new ErrorDTO("balance", "can not be null or empty"));
        }
        return errors;
    }
}
