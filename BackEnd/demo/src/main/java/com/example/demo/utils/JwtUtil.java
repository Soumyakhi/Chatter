package com.example.demo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {
    private Map<String,String> validTokens=new ConcurrentHashMap<>();
    private static final String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf=";
    public String generateToken(String userId) {
        String jwt=Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        validTokens.put(userId,jwt);
        return jwt;
    }
    public String extractUserId(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        catch (Exception e){
            return "invalid";
        }
    }
    public boolean validateToken(String token, Long userId) {
        String extractedUserId = extractUserId(token);
        return validTokens.containsKey(extractedUserId) && extractedUserId.equals(userId.toString()) && validTokens.get(extractedUserId).equals(token);
    }
    public boolean isLoggedin(String userId){
        if(validTokens.containsKey(userId)){
            return true;
        }
        return false;
    }
    public void removeToken(String userId){
        validTokens.remove(userId);
    }
}
