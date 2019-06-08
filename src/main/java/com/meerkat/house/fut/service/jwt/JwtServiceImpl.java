package com.meerkat.house.fut.service.jwt;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import com.meerkat.house.fut.model.account.Account;
import com.meerkat.house.fut.repository.AccountRepository;
import com.meerkat.house.fut.utils.CurrentInfoUtils;
import com.meerkat.house.fut.utils.FutConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtServiceImpl {

    @Value("${jwt.signature.key}")
    private String jwtSignatureKey;

    private final AccountRepository accountRepository;

    @Autowired
    public JwtServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String setJwtToken(Account account, Date expireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setClaims(setClaims(account, expireTime))
                .signWith(SignatureAlgorithm.HS512, setSignatureKey())
                .compact();
    }

    private Claims setClaims(Account account, Date expireTime) {
        Claims claims = Jwts.claims()
                .setSubject(FutConstant.JWT_SUB)
                .setIssuer(FutConstant.JWT_ISS)
                .setExpiration(expireTime);

        claims.put(FutConstant.JWT_ID, account.getId());
        claims.put(FutConstant.JWT_EMAIL, account.getEmail());
        claims.put(FutConstant.JWT_SOCIAL, account.getSocial());
        claims.put(FutConstant.JWT_ROLE, setRoles(account.getId()));

        return claims;
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
            key = jwtSignatureKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("[JWT] Set signature key. Cause : {}", e.toString());
            throw new RestException();
        }

        return key;
    }

    public boolean isUsable(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(setSignatureKey())
                    .parseClaimsJws(accessToken);
            return isValidUser(claims);
        } catch (Exception e) {
            log.error("[AUTH] Access token error. Parsing failure. Cause : {}", e.toString());
            throw new RestException(ResultCode.AUTH_TOKEN_IS_INVALID);
        }
    }

    private boolean isValidUser(Jws<Claims> claims) {
        String id = (String) claims.getBody().get(FutConstant.JWT_ID);
        String email = (String) claims.getBody().get(FutConstant.JWT_EMAIL);
        String social = (String) claims.getBody().get(FutConstant.JWT_SOCIAL);

        if (null == id || null == social) {
            log.error("Token has not id or social values");
            return false;
        }

        Account account = accountRepository.findByIdAndEmailAndSocial(id, email, social);
        if (null == account) {
            return false;
        }

        CurrentInfoUtils.setUid(account.getUid());

        return true;
    }
}
