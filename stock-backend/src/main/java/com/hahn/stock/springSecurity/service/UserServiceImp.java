package com.hahn.stock.springSecurity.service;

import com.hahn.stock.dto.UserDTO;
import com.hahn.stock.entity.ConfirmationTokenEntity;
import com.hahn.stock.entity.UserEntity;
import com.hahn.stock.repository.UserRepository;
import com.hahn.stock.springSecurity.jwt.JwtConfig;
import com.hahn.stock.springSecurity.security.ConfirmationTokenService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService, UserDetailsService {


    public static final String IN_THE_DATABASE = " in the database";
    public static final String ALREADY_EXISTS = " already exists";
    public static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";
    public static final String USER_WITH_EMAIL = "User with email: ";
    public static final String USER_WITH_NAME = "User with name: ";
    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final JwtService jwtService;
//    @Value("${spring.profiles.active}")
//    private String profile;
//    @Value("${mail.BASE}")
//    private String baseUrl;

    @Override
    public List<UserDTO> getUsers() {
        log.info("[getUsers] fetching users based on authentication");
        return userRepository.findAll().stream()
                .map(UserDTO::map)
                .toList();

        // If the user's role does not match any of the above, return an empty list
    }

    @Transactional
    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        UserEntity userEntity = UserDTO.map(userDTO);

        userRepository
                .findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException(USER_WITH_EMAIL + userDTO.getEmail() + ALREADY_EXISTS);
                });

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            userEntity.setPassword(generateRandomPassword());
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        UserDTO mappedUser = UserDTO.map(userRepository.save(userEntity));
        mappedUser.setCreatedDate(LocalDateTime.now());

        return mappedUser;
    }

    public static String generateRandomPassword() {
        final String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public UserDTO getUser(String authorization) {
        String token = null;
        if (jwtConfig.getTokenPrefix() != null)
            token = authorization.replace(jwtConfig.getTokenPrefix(), "");
        else token = authorization;
        Claims claimsJws = this.jwtService.getClaimsFromToken(token);
        String email = claimsJws.getSubject();

        UserEntity userEntity = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));

        return UserDTO.map(userEntity);
    }


    @Override
    public UserDTO updateUser(UserDTO userDTO) throws Exception {
        log.info("[updateUser] userDTO: {}", userDTO);
        UserEntity userEntity = UserDTO.map(userDTO);


        UserEntity user = userRepository.findById(userDTO.getId())
                .orElseThrow(
                        () -> new Exception("Cannot find the partner")
                );

        userEntity.setPassword(user.getPassword());
        userEntity.setPasswordResetToken(user.getPasswordResetToken());
        userEntity.setCreatedBy(user.getCreatedBy());
        userEntity.setCreatedDate(user.getCreatedDate());

        return UserDTO.map(userRepository.save(userEntity));
    }


    @Transactional
    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null) {
            userRepository.deleteById(id);
        }
    }

    @Transactional
    public ResponseEntity<Boolean> checkTokenExpiration(String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            return ResponseEntity
                    .ok()
                    .body(false);
        }
        confirmationTokenService.setConfirmedAt(token);

        return ResponseEntity
                .ok()
                .body(true);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));

        userRepository.save(user);
        log.info("user found in the database with email: {} ", email);
        return user;
    }
}
