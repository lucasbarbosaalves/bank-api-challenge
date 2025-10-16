package com.lucasbarbosaalves.bank.entities.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositRequestDTO(@NotNull @Positive BigDecimal amount) {
}