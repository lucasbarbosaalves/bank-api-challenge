package com.lucasbarbosaalves.bank.entities.transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDetailDTO(
        UUID id,
        TransactionType type,
        BigDecimal amount,
        LocalDateTime createdAt
) {
    public TransactionDetailDTO(Transaction transaction) {
        this(transaction.getId(), transaction.getType(), transaction.getAmount(), transaction.getCreatedAt());
    }
}