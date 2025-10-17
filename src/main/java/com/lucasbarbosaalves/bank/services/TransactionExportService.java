package com.lucasbarbosaalves.bank.services;

import com.lucasbarbosaalves.bank.entities.transactions.Transaction;
import com.lucasbarbosaalves.bank.entities.transactions.TransactionType;
import com.lucasbarbosaalves.bank.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável por gerar relatórios e exportações de dados.
 */
@Service
@RequiredArgsConstructor
public class TransactionExportService {

    private final TransactionRepository transactionRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public String generateCsvForAccount(UUID accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByCreatedAtAsc(accountId);

        StringBuilder csvContent = new StringBuilder();
        csvContent.append("Data/Hora,Tipo,Valor,ID da Transacao\n");

        for (Transaction tx : transactions) {
            csvContent.append(String.format("%s,%s,%s,%s\n",
                    tx.getCreatedAt().format(FORMATTER),
                    tx.getType() == TransactionType.CREDIT ? "CREDITO" : "DEBITO",
                    tx.getAmount().toPlainString(),
                    tx.getId()));
        }

        return csvContent.toString();
    }
}