package com.security.auth.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.security.auth.entity.UserInfo;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {
    private String firstName; // first_name

    private String lastName; //last_name

    private Long phoneNumber;

    private String email; // email
}
