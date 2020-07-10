package com.tristu.validation.api;

public interface UserService {

    UserDto getUserByUsername(String username);

    UserDto getUserByCnp(String username);

}
