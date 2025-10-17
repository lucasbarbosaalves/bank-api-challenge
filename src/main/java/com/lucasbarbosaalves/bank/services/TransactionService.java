package com.lucasbarbosaalves.bank.services;

import com.lucasbarbosaalves.bank.exception.ResourceNotFoundException;
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

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void executeTransfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta de origem não encontrada com o ID: " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta de destino não encontrada com o ID: " + toAccountId));


        BigDecimal fromAccountBalance = getBalance(fromAccountId);
        if (fromAccountBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente.");
        }


        UUID logicalId = UUID.randomUUID();


        Transaction debit = new Transaction(logicalId, fromAccount, TransactionType.DEBIT, amount);
        transactionRepository.save(debit);


        Transaction credit = new Transaction(logicalId, toAccount, TransactionType.CREDIT, amount);
        transactionRepository.save(credit);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    /**
     * Calcula o saldo de uma conta somando todas as suas transações no ledger.
     * Este é o método mais seguro e preciso para obter o saldo.
     * https://pt.wikipedia.org/wiki/Ledger
     */
    public BigDecimal getBalance(UUID accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByCreatedAtAsc(accountId);

        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction tx : transactions) {
            if (tx.getType() == TransactionType.CREDIT) {
                balance = balance.add(tx.getAmount());
            } else if (tx.getType() == TransactionType.DEBIT) {
                balance = balance.subtract(tx.getAmount());
            }
        }
        return balance;
    }
}