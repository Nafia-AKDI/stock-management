package com.hahn.stock.springSecurity.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUsersStatusRequest {
    private List<String> userIds;
//    private UserStatus status;
}
