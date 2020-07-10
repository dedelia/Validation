package com.tristu.validation.service;

import com.tristu.validation.api.*;
import lombok.AllArgsConstructor;
import nl.garvelink.iban.IBAN;
import nl.garvelink.iban.Modulo97;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private static final String PAYER = "Payer";
    private static final String PAYEE = "Payee";

    private static final List<String> countryCodeList = Collections.singletonList("RO");

    private final UserService userService;
    private final PaymentService paymentService;

    @Override
    public void validateTransaction(String username, TransactionDto transactionDto) {
        UserDto currentUser = userService.getUserByUsername(username);
        validateUser(currentUser);

        switch (transactionDto.getTransactionType()) {
            case IBAN_TO_IBAN:
                validateIBANToIBAN(transactionDto);
                break;
            case IBAN_TO_WALLET:
                validateIBANToWALLET(transactionDto);
                break;
            case WALLET_TO_IBAN:
                validateWalletToIBAN(currentUser, transactionDto);
                break;
            case WALLET_TO_WALLET:
                validateWalletToWallet(currentUser, transactionDto);
                break;
            default:
                break;
        }
        paymentService.saveTransaction(transactionDto);

    }

    private void validateIBANToIBAN(TransactionDto transactionDto) {
        validateIBAN(PAYER, transactionDto.getPayerIban());
        validateCNP(PAYER, transactionDto.getPayerCnp());

        validateIBAN(PAYEE, transactionDto.getPayeeIban());
        validateCNP(PAYEE, transactionDto.getPayeeCnp());
    }

    private void validateIBANToWALLET(TransactionDto transactionDto) {
        validateIBAN(PAYER, transactionDto.getPayerIban());
        validateCNP(PAYER, transactionDto.getPayerCnp());

        validateWalletByCnp(PAYEE, transactionDto.getPayeeCnp());

    }

    private void validateWalletToIBAN(UserDto user, TransactionDto transactionDto) {
        validateIBAN(PAYEE, transactionDto.getPayeeIban());
        validateCNP(PAYEE, transactionDto.getPayeeCnp());

        validateWalletId(PAYER, user.getWalletId());
    }

    private void validateWalletToWallet(UserDto user, TransactionDto transactionDto) {
        validateWalletId(PAYER, user.getWalletId());
        validateWalletByCnp(PAYEE, transactionDto.getPayeeCnp());
    }

    private void validateIBAN(String roleInTransaction, String iban) {
        if (!Modulo97.verifyCheckDigits(iban)) {
            throw new IllegalArgumentException(roleInTransaction + "-- IBAN not valid --");
        }

        IBAN currentIBAN = IBAN.parse(iban);
        if (!countryCodeList.contains(currentIBAN.getCountryCode())) {
            throw new IllegalArgumentException(roleInTransaction + "-- IBAN not part of permitted countries --");
        }
    }

    private void validateCNP(String roleInTransaction, String cnp) {
        if (!(cnp.startsWith("1") || cnp.startsWith("2"))) {
            throw new IllegalArgumentException(roleInTransaction + "-- CNP is not valid --");
        }
        if (cnp.length() != 13) {
            throw new IllegalArgumentException(roleInTransaction + "-- CNP is not valid --");
        }
    }

    private void validateUser(UserDto currentUser) {
        if (!(currentUser.getIban() != null && currentUser.getCnp() != null && currentUser.getName() != null)) {
            throw new IllegalArgumentException("-- Payer is not valid --");
        }
    }

    private void validateWalletId(String roleInTransaction, Integer walletId) {
        if (walletId == null) {
            throw new IllegalArgumentException(roleInTransaction + "-- wallet id is not valid --");
        }
    }

    private void validateWalletByCnp(String roleInTransaction, String cnp) {
        UserDto user = userService.getUserByCnp(cnp);
        if (user == null) {
            throw new IllegalArgumentException(roleInTransaction + "-- USER not found --");
        }
        validateWalletId(roleInTransaction, user.getWalletId());
    }
}
