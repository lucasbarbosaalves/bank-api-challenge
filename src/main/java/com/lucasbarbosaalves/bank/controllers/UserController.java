package com.lucasbarbosaalves.bank.controllers;

import com.lucasbarbosaalves.bank.entities.users.CreateUserDTO;
import com.lucasbarbosaalves.bank.entities.users.User;
import com.lucasbarbosaalves.bank.entities.users.UserResponseDTO;
import com.lucasbarbosaalves.bank.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints para gerenciamento de usuários")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Cria um novo usuário e sua conta bancária associada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos (ex: CPF, e-mail) ou usuário já existente", content = @Content)
    })
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid CreateUserDTO userDTO) {
        User newUser = userService.createUser(userDTO);
        UserResponseDTO responseDTO = new UserResponseDTO(newUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}