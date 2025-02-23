package uz.pdp.simplecrud.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.entity.Card;
import uz.pdp.simplecrud.entity.Users;
import uz.pdp.simplecrud.exception.ResourceNotFoundException;
import uz.pdp.simplecrud.mapper.CardMapper;
import uz.pdp.simplecrud.repozitory.CardRepository;
import uz.pdp.simplecrud.repozitory.UsersRepository;
import uz.pdp.simplecrud.service.CardService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final UsersRepository usersRepository;
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    @Override
    public ResponseDTO<Card> createCard(@NonNull CardCreateDTO cardCreateDTO) {
        List<Users> users = usersRepository.findAll();
        Card card = cardMapper.toEntity(cardCreateDTO);
        for (Users user : users) {
            if (user.getId().equals(cardCreateDTO.getUserId())) {
                Users userFound = usersRepository.findById(user.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + user.getId()));
                card.setUser(userFound);
                cardRepository.save(card);
            }
        }
        return ResponseDTO.<Card>builder()
                .code(200)
                .success(true)
                .message("Card successfully created")
                .data(card)
                .build();
    }

    @Override
    public ResponseDTO<Card> getCard(@NonNull Integer id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found: " + id));
        return ResponseDTO.<Card>builder()
                .code(200)
                .message("Card successfully created")
                .success(true)
                .data(card)
                .build();
    }

    @Override
    public ResponseDTO<List<Card>> getAllCard() {
        List<Card> cards = cardRepository.findAll();
        return ResponseDTO.<List<Card>>builder()
                .code(200)
                .message("Card list successfully found")
                .success(true)
                .data(cards)
                .build();
    }

    @Override
    public ResponseDTO<CardCreateDTO> updateCard(@NonNull CardCreateDTO cardCreateDTO,
                                                 @NonNull Integer cardId,
                                                 @NonNull Integer userId) {
        Optional<Users> optional = usersRepository.findById(userId);
        if (optional.isEmpty()) {
            ResponseEntity.badRequest().body(ResponseDTO.<CardCreateDTO>builder()
                    .code(-3)
                    .message("User not found")
                    .build());
        }
        Users users = optional.get();
        Optional<Card> op = cardRepository.getCardByUserIdAndCardId(users.getId(), cardId);
        if (op.isPresent()) {
            Card card = op.get();
            card.setCardNumber(cardCreateDTO.getCardNumber());
            card.setCardCode(cardCreateDTO.getCardCode());
            card.setBalance(cardCreateDTO.getBalance());
            card.setUpdatedAt(LocalDateTime.now());
            card.setUser(users);
            cardRepository.save(card);
            return ResponseDTO.<CardCreateDTO>builder()
                    .code(200)
                    .message("Card successfully updated")
                    .success(true)
                    .build();
        }
        throw new ResourceNotFoundException("Card not found: " + cardId);
    }
}
