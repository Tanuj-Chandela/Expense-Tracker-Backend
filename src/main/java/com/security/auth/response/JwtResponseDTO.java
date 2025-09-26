package com.security.auth.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtResponseDTO {

    private String accessToken;
    private String token;
}