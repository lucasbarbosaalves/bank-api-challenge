package com.lucasbarbosaalves.bank.controllers;

import com.lucasbarbosaalves.bank.entities.account.AccountBalanceDTO;
import com.lucasbarbosaalves.bank.entities.account.DepositRequestDTO;
import com.lucasbarbosaalves.bank.entities.transactions.TransactionDetailDTO;
import com.lucasbarbosaalves.bank.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Endpoints para consulta de informações da conta")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}/balance")
    @Operation(summary = "Consulta o saldo atual de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content)
    })
    public ResponseEntity<AccountBalanceDTO> getBalance(
            @Parameter(description = "ID da conta a ser consultada") @PathVariable UUID accountId) {
        BigDecimal balance = accountService.getAccountBalance(accountId);
        return ResponseEntity.ok(new AccountBalanceDTO(balance));
    }

    @GetMapping("/{accountId}/statement")
    @Operation(summary = "Consulta o extrato (histórico de transações) de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content)
    })
    public ResponseEntity<List<TransactionDetailDTO>> getStatement(
            @Parameter(description = "ID da conta para obter o extrato") @PathVariable UUID accountId) {
        List<TransactionDetailDTO> statement = accountService.getAccountStatement(accountId);
        return ResponseEntity.ok(statement);
    }

    @PostMapping("/{accountId}/deposit")
    @Operation(summary = "Realiza um depósito em uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor de depósito inválido (deve ser positivo)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deposit(
            @Parameter(description = "ID da conta que receberá o depósito") @PathVariable UUID accountId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto com o valor a ser depositado") @RequestBody @Valid DepositRequestDTO depositRequest) {
        accountService.deposit(accountId, depositRequest.amount());
        return ResponseEntity.ok().build();
    }
}