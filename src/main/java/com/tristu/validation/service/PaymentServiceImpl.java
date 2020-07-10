package com.tristu.validation.service;

import com.tristu.validation.api.PaymentService;
import com.tristu.validation.api.TransactionDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final String baseURL = "http://localhost:8082/transactions";

    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransactionDto> response = restTemplate.postForEntity(baseURL + "/save", transactionDto, TransactionDto.class);
        return response.getBody();
    }
}
