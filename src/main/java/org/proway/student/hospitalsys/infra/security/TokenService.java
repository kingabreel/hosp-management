package org.proway.student.hospitalsys.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.proway.student.hospitalsys.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private Set<String> invalidatedToken = new HashSet<>();

    public String gerenateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw  new RuntimeException("Error generating token: ", exception);
        }
    }

    public String validadeToken(String token){
        if (invalidatedToken.contains(token)) {
            throw new RuntimeException("Token has been invalidated");
        }

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (TokenExpiredException exception) {
            throw new RuntimeException("Token expired", exception);
        } catch (JWTCreationException exception) {

            throw new RuntimeException("Error validating token: ", exception);
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public void invalidateToken(String token) {
        invalidatedToken.add(token);
    }

}
