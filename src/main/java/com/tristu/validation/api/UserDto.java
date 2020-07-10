package com.tristu.validation.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private long id;
    private String name;
    private String username;
    private String iban;
    private String cnp;
    private Integer walletId;

}
