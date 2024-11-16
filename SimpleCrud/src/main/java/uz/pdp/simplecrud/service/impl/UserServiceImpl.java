package uz.pdp.simplecrud.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.pdp.simplecrud.dto.ErrorDTO;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.dto.UserCreateDTO;
import uz.pdp.simplecrud.entity.Users;
import uz.pdp.simplecrud.mapper.UsersMapper;
import uz.pdp.simplecrud.repozitory.CardRepository;
import uz.pdp.simplecrud.repozitory.UsersRepository;
import uz.pdp.simplecrud.service.UserService;
import uz.pdp.simplecrud.validation.UserValidation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final CardRepository cardRepository;
    private final UsersMapper usersMapper;
    private final UsersRepository userRepository;
    private final UserValidation userValidation;

    @Override
    public ResponseDTO<Users> createUser(@NonNull UserCreateDTO userCreateDTO) {
        List<ErrorDTO> errorDTOList = userValidation.errorDTOS(userCreateDTO);
        if (!errorDTOList.isEmpty()) {
            return ResponseDTO.<Users>builder()
                    .code(-1)
                    .message("User validation error")
                    .errors(errorDTOList)
                    .build();
        }

        try {
            Users user = usersMapper.toEntity(userCreateDTO);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            log.info("User successfully saved");

            return ResponseDTO.<Users>builder()
                    .code(1)
                    .success(true)
                    .message("Ok")
                    .data(user)
                    .build();
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            return ResponseDTO.<Users>builder()
                    .code(-1)
                    .message("An error occurred while creating the user")
                    .build();
        }
    }

    @Override
    public ResponseDTO<Users> getUser(@NonNull Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        return ResponseDTO.<Users>builder()
                .code(1)
                .success(true)
                .message("OK")
                .data(user)
                .build();
    }

    @Override
    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public ResponseDTO<UserCreateDTO> updateUser(@NonNull UserCreateDTO userCreateDTO, @NonNull Integer id) {
        List<ErrorDTO> errorDTOS = userValidation.errorDTOS(userCreateDTO);
        if (!errorDTOS.isEmpty()) {
            return ResponseDTO.<UserCreateDTO>builder()
                    .code(-1)
                    .message("User validation error")
                    .errors(errorDTOS)
                    .build();
        }

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        user.setUpdatedAt(LocalDateTime.now());
        user.setName(userCreateDTO.getName());
        userRepository.save(user);

        return ResponseDTO.<UserCreateDTO>builder()
                .code(200)
                .success(true)
                .message("Success")
                .data(userCreateDTO)
                .build();
    }

    @Override
    public ResponseDTO<UserCreateDTO> deleteUser(@NonNull Integer userId) {
        Optional<Users> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            Users user = optional.get();
            this.cardRepository.deleteCardByUserId(user.getId());
            this.userRepository.delete(user);

            return ResponseDTO.<UserCreateDTO>builder()
                    .success(true)
                    .message("Success")
                    .data(usersMapper.toDto(user))
                    .build();
        }
        return ResponseDTO.<UserCreateDTO>builder()
                .message("User is not found")
                .build();
    }
}
