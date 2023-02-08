package com.dfoff.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bcrypt {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public BCryptPasswordEncoder getInstance() {
        return bCryptPasswordEncoder;
    }

}
