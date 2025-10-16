package com.lucasbarbosaalves.bank.services;

import com.lucasbarbosaalves.bank.entities.transactions.TransactionDetailDTO;
import com.lucasbarbosaalves.bank.entities.account.Account;
import com.lucasbarbosaalves.bank.entities.transactions.Transaction;
import com.lucasbarbosaalves.bank.entities.transactions.TransactionType;
import com.lucasbarbosaalves.bank.repositories.AccountRepository;
import com.lucasbarbosaalves.bank.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public BigDecimal getAccountBalance(UUID accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Conta não encontrada.");
        }
        return transactionService.getBalance(accountId);
    }

    public List<TransactionDetailDTO> getAccountStatement(UUID accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByCreatedAtAsc(accountId);
        return transactions.stream()
                .map(TransactionDetailDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deposit(UUID accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        Transaction credit = new Transaction(UUID.randomUUID(), account, TransactionType.CREDIT, amount);
        transactionRepository.save(credit);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}