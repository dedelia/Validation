package com.tristu.validation.api;

public interface ValidationService {

    void validateTransaction(String username, TransactionDto transactionDto);

}
