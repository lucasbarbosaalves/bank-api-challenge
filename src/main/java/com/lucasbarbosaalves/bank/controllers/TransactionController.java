package com.lucasbarbosaalves.bank.controllers;

import com.lucasbarbosaalves.bank.entities.transactions.TransferRequestDTO;
import com.lucasbarbosaalves.bank.services.TransactionExportService;
import com.lucasbarbosaalves.bank.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Endpoints para realizar transações e exportar extratos")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionExportService transactionExportService;

    @PostMapping("/transfer")
    @Operation(summary = "Realiza uma transferência de valor entre duas contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor inválido ou saldo insuficiente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada", content = @Content)
    })
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequestDTO transferRequest) {
        transactionService.executeTransfer(
                transferRequest.fromAccountId(),
                transferRequest.toAccountId(),
                transferRequest.amount()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/export/csv/{accountId}")
    @Operation(summary = "Exporta o histórico de transações de uma conta para um arquivo CSV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV gerado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content)
    })
    public ResponseEntity<String> exportTransactionsToCsv(
            @Parameter(description = "ID da conta para gerar o extrato") @PathVariable UUID accountId) {
        String csvData = transactionExportService.generateCsvForAccount(accountId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "extrato-conta-" + accountId + ".csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }
}