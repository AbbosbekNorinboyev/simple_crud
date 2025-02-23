package uz.pdp.simplecrud.service;

import org.springframework.lang.NonNull;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.dto.UserCreateDTO;
import uz.pdp.simplecrud.entity.Users;

import java.util.List;

public interface UserService {
    ResponseDTO<Users> createUser(@NonNull UserCreateDTO userCreateDTO);

    ResponseDTO<Users> getUser(@NonNull Integer id);

    ResponseDTO<List<Users>> getAllUser();

    ResponseDTO<UserCreateDTO> updateUser(@NonNull UserCreateDTO userCreateDTO, @NonNull Integer id);

    ResponseDTO<UserCreateDTO> deleteUser(@NonNull Integer id);
}
