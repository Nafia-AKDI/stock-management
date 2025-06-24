package com.hahn.stock.springSecurity.service;


import com.hahn.stock.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers();

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO) throws Exception;

    void deleteUser(String id);

    ResponseEntity<Boolean> checkTokenExpiration(String token);

    UserDTO getUser(String authorization);


}
