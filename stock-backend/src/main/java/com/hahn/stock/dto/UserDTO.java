package com.hahn.stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hahn.stock.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String email;
    private String fullName;
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    @JsonProperty(access = WRITE_ONLY)
    private String passwordResetToken;
    private LocalDateTime createdDate;
    @JsonProperty(access = WRITE_ONLY)
    private LocalDateTime updatedDate;
    @JsonProperty(access = WRITE_ONLY)
    private String createdBy;
    @JsonProperty(access = WRITE_ONLY)
    private String updatedBy;

    public static UserDTO map(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .fullName(userEntity.getFullName())
                .createdDate(userEntity.getCreatedDate())
                .createdDate(userEntity.getCreatedDate())
                .build();
    }

    public static UserEntity map(UserDTO userDTO) {


        return UserEntity.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .createdDate(userDTO.getCreatedDate())
                .build();
    }
}