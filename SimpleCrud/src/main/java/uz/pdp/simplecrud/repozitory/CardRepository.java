package uz.pdp.simplecrud.repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.simplecrud.entity.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByIdAndDeletedAtIsNull(Integer id);

    @Query(value = "select * from card as c where user_id=?1 and id=?2", nativeQuery = true)
    Optional<Card> getCardByUserIdAndCardId(Integer userId, Integer cardId);

    @Query(value = "delete from card as c where c.user_id=?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteCardByUserId(Integer userId);
}