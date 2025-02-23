package uz.pdp.simplecrud.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.simplecrud.dto.UserCreateDTO;
import uz.pdp.simplecrud.entity.Users;

@Component
public class UsersMapper {
    public Users toEntity(UserCreateDTO userCreateDTO) {
        return Users.builder()
                .name(userCreateDTO.getName())
                .surname(userCreateDTO.getSurname())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .createdAt(userCreateDTO.getCreatedAt())
                .updatedAt(userCreateDTO.getUpdatedAt())
                .deletedAt(userCreateDTO.getDeletedAt())
                .build();
    }

    public UserCreateDTO toDto(Users user) {
        return UserCreateDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }
}
