package uz.pdp.simplecrud.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.dto.UserCreateDTO;
import uz.pdp.simplecrud.entity.Users;
import uz.pdp.simplecrud.exception.ResourceNotFoundException;
import uz.pdp.simplecrud.mapper.UsersMapper;
import uz.pdp.simplecrud.repozitory.CardRepository;
import uz.pdp.simplecrud.repozitory.UsersRepository;
import uz.pdp.simplecrud.service.UserService;

import java.lang.module.ResolutionException;
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

    @Override
    public ResponseDTO<Users> createUser(@NonNull UserCreateDTO userCreateDTO) {
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return ResponseDTO.<Users>builder()
                .code(200)
                .success(true)
                .message("User successfully created")
                .data(user)
                .build();
    }

    @Override
    public ResponseDTO<List<Users>> getAllUser() {
        List<Users> users = userRepository.findAll();
        return ResponseDTO.<List<Users>>builder()
                .code(200)
                .success(true)
                .message("User list successfully found")
                .data(users)
                .build();
    }

    @Override
    public ResponseDTO<UserCreateDTO> updateUser(@NonNull UserCreateDTO userCreateDTO, @NonNull Integer id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        user.setUpdatedAt(LocalDateTime.now());
        user.setName(userCreateDTO.getName());
        userRepository.save(user);
        return ResponseDTO.<UserCreateDTO>builder()
                .code(200)
                .success(true)
                .message("User successfully updated")
                .data(userCreateDTO)
                .build();
    }

    @Override
    public ResponseDTO<UserCreateDTO> deleteUser(@NonNull Integer userId) {
        Optional<Users> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            Users user = optional.get();
            cardRepository.deleteCardByUserId(user.getId());
            userRepository.delete(user);
            return ResponseDTO.<UserCreateDTO>builder()
                    .success(true)
                    .message("User successfully deleted")
                    .data(usersMapper.toDto(user))
                    .build();
        }
        throw new ResolutionException("User not found: " + userId);
    }
}
