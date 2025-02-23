package uz.pdp.simplecrud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.simplecrud.dto.ResponseDTO;
import uz.pdp.simplecrud.dto.UserCreateDTO;
import uz.pdp.simplecrud.entity.Users;
import uz.pdp.simplecrud.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseDTO<Users> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @GetMapping("/{id}")
    public ResponseDTO<Users> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @GetMapping
    public ResponseDTO<List<Users>> getAllUser() {
        return userService.getAllUser();
    }

    @PutMapping("/{id}")
    public ResponseDTO<UserCreateDTO> updateUser(@RequestBody @Valid UserCreateDTO dto, @PathVariable Integer id) {
        return userService.updateUser(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<UserCreateDTO> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
