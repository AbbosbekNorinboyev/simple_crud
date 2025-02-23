package uz.pdp.simplecrud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.simplecrud.dto.CardCreateDTO;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.entity.Card;
import uz.pdp.simplecrud.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseDTO<Card> createCard(@RequestBody @Valid CardCreateDTO cardCreateDTO) {
        return cardService.createCard(cardCreateDTO);
    }

    @GetMapping("/{id}")
    public ResponseDTO<Card> getCard(@PathVariable Integer id) {
        return cardService.getCard(id);
    }

    @GetMapping
    public ResponseDTO<List<Card>> getAllCard() {
        return cardService.getAllCard();
    }

    @PutMapping("/{cardId}/{userId}")
    public ResponseDTO<CardCreateDTO> updateCard(@RequestBody @Valid CardCreateDTO cardCreateDTO,
                                                 @PathVariable Integer cardId,
                                                 @PathVariable Integer userId) {
        return cardService.updateCard(cardCreateDTO, cardId, userId);
    }
}
