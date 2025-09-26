package com.security.auth.controller;


import com.security.auth.entity.RefereshToken;
import com.security.auth.model.UserInfoDTO;
import com.security.auth.response.JwtResponseDTO;
import com.security.auth.service.JwtService;
import com.security.auth.service.RefereshTokenService;
import com.security.auth.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefereshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDTO userInfoDTO){
        try{
            Boolean isUserSignUp = userDetailsService.signUpUser(userInfoDTO);
            if(Boolean.FALSE.equals(isUserSignUp)){
                return new ResponseEntity<>("Already Exists", HttpStatus.BAD_REQUEST);
            }
            String jwt = jwtService.generateToken(userInfoDTO.getUsername());
            RefereshToken refreshToken = refreshTokenService.createRefereshToken(userInfoDTO.getUsername());
                return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwt).token(refreshToken.getToken()).build(),HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
