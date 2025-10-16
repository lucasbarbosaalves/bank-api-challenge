package com.lucasbarbosaalves.bank.entities.users;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        UUID accountId,
        Long accountNumber
) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getAccount().getId(), user.getAccount().getAccountNumber());
    }
}