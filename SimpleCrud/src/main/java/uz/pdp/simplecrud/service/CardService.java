package uz.pdp.simplecrud.service;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.entity.Card;

import java.util.List;

public interface CardService {
    ResponseDTO<Card> createCard(@NonNull CardCreateDTO cardCreateDTO);

    ResponseDTO<Card> getCard(@NonNull Integer id);

    List<Card> getAllCard();

    ResponseEntity<ResponseDTO<CardCreateDTO>> updateCard(@NonNull CardCreateDTO cardCreateDTO,
                                                          @NonNull Integer cardId,
                                                          @NonNull Integer userId);
}
