package com.lucasbarbosaalves.bank.entities.account;

import java.math.BigDecimal;

public record AccountBalanceDTO(
        BigDecimal balance
) {}