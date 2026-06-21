package kg.aiken.FirstProject.Controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.aiken.FirstProject.Entity.User;
import kg.aiken.FirstProject.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(
            summary = "Создать пользователя",
            description = "Создает нового пользователя банковской системы. Email должен быть уникальным."
    )
    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    @Operation(
            summary = "Получить пользователя по идентификатору",
            description = "Возвращает информацию о пользователе по его уникальному идентификатору."
    )
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
            summary = "Поиск пользователя по email",
            description = "Выполняет поиск пользователя по адресу электронной почты."
    )
    @GetMapping("/search")
    public User getByEmail(@RequestParam String email) {
        return service.getByEmail(email);
    }

    @Operation(
            summary = "Получить список пользователей",
            description = "Возвращает список всех пользователей с поддержкой пагинации."
    )
    @GetMapping
    public Page<User> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

}