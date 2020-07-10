package com.tristu.validation.controller;

import com.tristu.validation.api.TransactionDto;
import com.tristu.validation.api.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("validate")
@AllArgsConstructor
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping("/transaction/{username}")
    public ResponseEntity<?> validateTransaction(@PathVariable String username,
                                                 @RequestBody TransactionDto transactionDto) {
        /**
         * Since the ideea of a logged user is not implemented yet, we consider the logged username sent through a path variable
         */
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        validationService.validateTransaction(username, transactionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
