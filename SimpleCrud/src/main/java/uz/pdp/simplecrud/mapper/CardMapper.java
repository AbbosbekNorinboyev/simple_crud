package uz.pdp.simplecrud.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.entity.Card;

@Component
public class CardMapper {
    public Card toEntity(CardCreateDTO createDTO) {
        return null;
    }

    CardCreateDTO toDto(Card card) {
        return null;
    }
}
