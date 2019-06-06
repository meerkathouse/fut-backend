package com.meerkat.house.fut.service.jwt;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.utils.FutConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class JwtServiceImpl {

    @Value("${jwt.secret_key}")
    private String jwtSecretKey;

    public String setJwtToken(Account account) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(account.getId())
                .claim("payload", setPayload(account))
                .signWith(SignatureAlgorithm.HS512, setSignatureKey())
                .compact();
    }

    private HashMap<String, Object> setPayload(Account account) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(FutConstant.JWT_ID, account.getId());
        payload.put(FutConstant.JWT_EMAIL, account.getEmail());
        payload.put(FutConstant.JWT_SOCIAL, account.getSocial());
        payload.put(FutConstant.JWT_ROLE, setRoles(account.getId()));

        return payload;
    }

    private List<String> setRoles(String id) {
        //  TODO.
        //  find roles from database
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("PLAYER");
        roles.add("MANAGER");

        return roles;
    }

    private byte[] setSignatureKey() {
        byte[] key = null;

        try {
            key = jwtSecretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("[JWT] Set signature key. Cause : {}", e.toString());
            throw new RestException();
        }

        return key;
    }
}
