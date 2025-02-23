package uz.pdp.simplecrud.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.entity.Card;
import uz.pdp.simplecrud.entity.Users;
import uz.pdp.simplecrud.repozitory.UsersRepository;

@Component
@RequiredArgsConstructor
public class CardMapper {

    private final UsersRepository usersRepository;

    public Card toEntity(CardCreateDTO cardCreateDTO) {
        Users user = usersRepository.findById(cardCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found from mapper: " + cardCreateDTO.getUserId()));
        return Card.builder()
                .cardNumber(cardCreateDTO.getCardNumber())
                .cardCode(cardCreateDTO.getCardCode())
                .balance(cardCreateDTO.getBalance())
                .user(user)
                .createdAt(cardCreateDTO.getCreatedAt())
                .updatedAt(cardCreateDTO.getUpdatedAt())
                .deletedAt(cardCreateDTO.getDeletedAt())
                .build();
    }

    public CardCreateDTO toDto(Card card) {
        return CardCreateDTO.builder()
                .cardNumber(card.getCardNumber())
                .cardCode(card.getCardCode())
                .balance(card.getBalance())
                .userId(card.getUser().getId())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .deletedAt(card.getDeletedAt())
                .build();
    }
}
