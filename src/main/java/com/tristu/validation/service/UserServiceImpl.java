package com.tristu.validation.service;

import com.tristu.validation.api.UserDto;
import com.tristu.validation.api.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final String baseURL = "http://localhost:8082/users";

    public UserDto getUserByUsername(String username) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> user = restTemplate.getForEntity(baseURL + "/" + username, UserDto.class);
        if (!user.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return user.getBody();
    }

    public UserDto getUserByCnp(String cnp) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> user = restTemplate.getForEntity(baseURL + "/by-cnp/" + cnp, UserDto.class);
        if (!user.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return user.getBody();
    }
}
