package uz.pdp.simplecrud.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardValidation {
    public List<ErrorDTO> validate(CardCreateDTO cardCreateDTO) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (cardCreateDTO.getBalance() < 0) {
            errors.add(new ErrorDTO("balance", "balance can not be negative"));
        }
        return errors;
    }
}
