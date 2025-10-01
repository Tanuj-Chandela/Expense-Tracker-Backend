package com.security.auth.service;

import com.security.auth.entity.UserInfo;
import com.security.auth.eventProducer.UserInfoEvent;
import com.security.auth.eventProducer.UserInfoProducer;
import com.security.auth.model.UserInfoDTO;
import com.security.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private  final PasswordEncoder passwordEncoder;

    @Autowired
    private  final UserInfoProducer userInfoProducer;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException {
        log.debug("Entering loadUserByUsername with username: {}", username);
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetail(user);
    }

    private  UserInfo checkIfUserExists(UserInfoDTO userInfoDTO){
        return userRepository.findByUsername(userInfoDTO.getUsername());
    }

    public Boolean signUpUser(UserInfoDTO userInfoDTO){
        // we can validate .. from validation class defined in our utils folder

        if(Objects.nonNull(checkIfUserExists((userInfoDTO)))){
            return false;
        }
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoDTO.getUsername(), userInfoDTO.getPassword(), new HashSet<>()));
        log.info("User registered successfully with username: {}", userInfoDTO.getUsername());
        //push event to queue for sending email
        UserInfoEvent userInfoEvent = userInfoEventPublisher(userInfoDTO,userId);
        userInfoProducer.sendEventToKafka(userInfoEvent);
        return true;
    }

    private  UserInfoEvent userInfoEventPublisher(UserInfoDTO userInfoDTO, String userId){
        return  UserInfoEvent
                .builder()
                .userId(userId)
                .email(userInfoDTO.getEmail())
                .firstName(userInfoDTO.getFirstName())
                .phoneNumber(userInfoDTO.getPhoneNumber())
                .lastName(userInfoDTO.getLastName())
                .build();
    }

}
