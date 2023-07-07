package com.example.cmd.domain.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Component
public class PasswordConverter implements AttributeConverter<String,String> {

    @Override
    public String convertToDatabaseColumn(String raw) {
        return encode(raw);
    }

    @Override
    public String convertToEntityAttribute(String encoded) {
        return encoded;
    }


    public static String encode(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
