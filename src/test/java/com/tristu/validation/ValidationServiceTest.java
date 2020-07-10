package com.tristu.validation;

import com.tristu.validation.api.*;
import com.tristu.validation.service.ValidationServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationServiceTest {

    private static final UserDto VALID_PAYER = UserDto.builder().username("deliatristu").name("Delia Tristu").cnp("2951009800533").iban("RO87RZBR9496777641988849").build();
    private static final TransactionDto VALID_TRANSACTION = TransactionDto.builder().transactionType(TransactionType.IBAN_TO_IBAN)
            .payerIban("RO79RZBR4597693686711687")
            .payerCnp("2951009800533")
            .payeeIban("RO52RZBR9181288511154499")
            .payeeCnp("1951009800533")
            .description("Cip")
            .amount(BigDecimal.TEN)
            .build();

    private final UserService userService = mock(UserService.class);
    private final PaymentService paymentService = mock(PaymentService.class);
    private final ValidationService validationService = new ValidationServiceImpl(userService, paymentService);

    @Test
    public void testValidPayerTransaction() {
        when(userService.getUserByUsername(VALID_PAYER.getUsername())).thenReturn(VALID_PAYER);
        validationService.validateTransaction(VALID_PAYER.getUsername(), VALID_TRANSACTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCNP() {
        VALID_PAYER.setCnp("3951009800533");

        when(userService.getUserByUsername(VALID_PAYER.getUsername())).thenReturn(VALID_PAYER);
        validationService.validateTransaction(VALID_PAYER.getUsername(), VALID_TRANSACTION);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIBANn() {
        VALID_PAYER.setIban("PI87RZBR9496777641988849");

        when(userService.getUserByUsername(VALID_PAYER.getUsername())).thenReturn(VALID_PAYER);
        validationService.validateTransaction(VALID_PAYER.getUsername(), VALID_TRANSACTION);

    }
}
