package uz.pdp.simplecrud.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.dto.ErrorDTO;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.entity.Card;
import uz.pdp.simplecrud.entity.Users;
import uz.pdp.simplecrud.mapper.CardMapper;
import uz.pdp.simplecrud.repozitory.CardRepository;
import uz.pdp.simplecrud.repozitory.UsersRepository;
import uz.pdp.simplecrud.service.CardService;
import uz.pdp.simplecrud.validation.CardValidation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final UsersRepository usersRepository;
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;
    private final CardValidation cardValidation;

    @Override
    public ResponseDTO<Card> createCard(@NonNull CardCreateDTO cardCreateDTO) {
        List<ErrorDTO> errorDTOS = cardValidation.errorDTOS(cardCreateDTO);
        if (!errorDTOS.isEmpty()) {
            ResponseDTO.<Card>builder()
                    .code(-1)
                    .message("Card Validation Error")
                    .errors(errorDTOS)
                    .build();
        }

        try {
            Card card = cardMapper.toEntity(cardCreateDTO);
            cardRepository.save(card);
            log.info("User successfully saved");

            return ResponseDTO.<Card>builder()
                    .code(1)
                    .success(true)
                    .message("Ok")
                    .data(card)
                    .build();
        } catch (Exception e) {
            log.error("Error creating card: {}", e.getMessage(), e);
            return ResponseDTO.<Card>builder()
                    .code(-1)
                    .message("An error occurred while creating the user")
                    .build();
        }
    }

    @Override
    public ResponseDTO<Card> getCard(@NonNull Integer id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found: " + id));
        return ResponseDTO.<Card>builder()
                .code(1)
                .success(true)
                .message("Ok")
                .data(card)
                .build();
    }

    @Override
    public List<Card> getAllCard() {
        return cardRepository.findAll();
    }

    @Override
    public ResponseEntity<ResponseDTO<CardCreateDTO>> updateCard(@NonNull CardCreateDTO cardCreateDTO,
                                                                 @NonNull Integer cardId,
                                                                 @NonNull Integer userId) {
        List<ErrorDTO> errorDTOS = cardValidation.errorDTOS(cardCreateDTO);
        if (!errorDTOS.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseDTO.<CardCreateDTO>builder()
                    .code(-1)
                    .message("Card validation error")
                    .errors(errorDTOS)
                    .build());
        }
        Optional<Users> optional = this.usersRepository.findById(userId);
        if (optional.isEmpty()) {
            ResponseEntity.badRequest().body(ResponseDTO.<CardCreateDTO>builder()
                    .code(-3)
                    .message("User not found")
                    .build());
        }
        Users users = optional.get();
        Optional<Card> op = this.cardRepository.getCardByUserIdAndCardId(users.getId(), cardId);
        if (op.isPresent()) {
            Card card = op.get();
            card.setCardNumber(cardCreateDTO.getCardNumber());
            card.setCardCode(cardCreateDTO.getCardCode());
            card.setBalance(cardCreateDTO.getBalance());
            card.setUpdatedAt(LocalDateTime.now());
            card.setUser(users);
            cardRepository.save(card);
            return ResponseEntity.ok(ResponseDTO.<CardCreateDTO>builder()
                    .code(200)
                    .success(true)
                    .message("UPDATED")
                    .build());
        }
        return ResponseEntity.ok(ResponseDTO.<CardCreateDTO>builder()
                .code(404)
                .message("Card is not found")
                .build());
    }
}
