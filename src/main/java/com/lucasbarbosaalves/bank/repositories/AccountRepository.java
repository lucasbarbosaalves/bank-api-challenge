package com.lucasbarbosaalves.bank.repositories;

import com.lucasbarbosaalves.bank.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
