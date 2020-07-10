package com.tristu.validation.api;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    @NonNull
    private TransactionType transactionType;

    private String payerIban;
    @NonNull
    private String payerCnp;

    private String payeeIban;
    @NonNull
    private String payeeCnp;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal amount;

}
