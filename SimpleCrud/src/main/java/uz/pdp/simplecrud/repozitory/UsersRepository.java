package uz.pdp.simplecrud.repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.simplecrud.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}