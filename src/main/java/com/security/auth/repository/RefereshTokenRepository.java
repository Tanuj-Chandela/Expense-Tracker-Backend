package com.security.auth.repository;

import com.security.auth.entity.RefereshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefereshTokenRepository extends CrudRepository<RefereshToken, Long> {
    Optional<RefereshToken> findByToken(String token);
}
