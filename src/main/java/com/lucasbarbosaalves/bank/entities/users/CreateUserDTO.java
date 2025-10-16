package com.lucasbarbosaalves.bank.entities.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateUserDTO(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Document cannot be blank") @CPF(message = "Invalid document format for CPF") String document,
        @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Password cannot be blank") @Size(min = 8, message = "Password must have at least 8 characters") String password
) {
}