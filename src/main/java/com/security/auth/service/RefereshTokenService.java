package com.security.auth.service;

import com.security.auth.entity.RefereshToken;
import com.security.auth.entity.UserInfo;
import com.security.auth.repository.RefereshTokenRepository;
import com.security.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefereshTokenService {

    @Autowired
    private RefereshTokenRepository refereshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public RefereshToken createRefereshToken(String username){
        UserInfo userInfo = userRepository.findByUsername(username);
        RefereshToken refereshToken = RefereshToken.builder()
                .userInfo(userInfo)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // 1 day expiry
                .build();
        return refereshTokenRepository.save(refereshToken);
    }

    public Optional<RefereshToken> findByToken(String token){
        return refereshTokenRepository.findByToken(token);
    }

    public RefereshToken verifyExpiration(RefereshToken token){
        if(token.getExpiryDate().compareTo(Instant.now()) < 0){
            refereshTokenRepository.delete(token);
            throw new RuntimeException("Token has expired. Please login again.");
        }
        return token;
    }
}
