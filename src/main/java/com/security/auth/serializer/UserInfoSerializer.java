package com.security.auth.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.auth.eventProducer.UserInfoEvent;
import com.security.auth.model.UserInfoDTO;
import org.apache.kafka.common.serialization.Serializer;

public class UserInfoSerializer implements Serializer<UserInfoEvent> {

    @Override
    public byte[] serialize(String arg0, UserInfoEvent arg1) {
        byte[] resVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
             resVal = objectMapper.writeValueAsString(arg1).getBytes();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return  resVal;
     }
}
