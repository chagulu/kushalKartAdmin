package com.kushalkart.util;

import com.kushalkart.entity.AdminUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class AdminJwtService {
   private final String SECRET_KEY = "mysecretmysecretmysecretmysecret";

   // Simple in-memory blacklist (for demo; consider Redis or DB for production)
   private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

   public AdminJwtService() {
   }

   private Key getSignKey() {
      return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
   }

   public String generateToken(AdminUser user) {
      return Jwts.builder()
              .setSubject(user.getUsername())
              .claim("role", user.getRole().name())
              .setIssuedAt(new Date())
              .setExpiration(Date.from(Instant.now().plus(1L, ChronoUnit.DAYS)))
              .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
              .compact();
   }

   // Add token to blacklist to invalidate it
   public void blacklistToken(String token) {
       blacklistedTokens.add(token);
   }

   // Check if token is blacklisted
   public boolean isTokenBlacklisted(String token) {
       return blacklistedTokens.contains(token);
   }

   public boolean validateToken(String token) {
      if (isTokenBlacklisted(token)) {
          // Token revoked
          return false;
      }
      try {
         Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token);
         return true;
      } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | ExpiredJwtException e) {
         e.printStackTrace();
         return false;
      }
   }

   public Claims extractAllClaims(String token) {
      return Jwts.parserBuilder()
                 .setSigningKey(this.getSignKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
   }

   public String extractUsername(String token) {
      return this.extractAllClaims(token).getSubject();
   }

   public String extractRole(String token) {
      return (String)this.extractAllClaims(token).get("role", String.class);
   }
}
