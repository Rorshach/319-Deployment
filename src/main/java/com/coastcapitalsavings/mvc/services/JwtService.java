package com.coastcapitalsavings.mvc.services;

import com.coastcapitalsavings.mvc.models.Employee;

import com.coastcapitalsavings.mvc.repositories.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;


public class JwtService {

    private static final String ISSUER = "in.sdqali.jwt";
    private SecretKeyProvider secretKeyProvider;
    public static final String USERNAME = "username";
    private final EmployeeRepository employeeRepository;

    @Autowired
    public JwtService(SecretKeyProvider secretKeyProvider, EmployeeRepository er) {
        this.secretKeyProvider = secretKeyProvider;
        this.employeeRepository = er;
    }

    /**
     * Tokenize a valid Employee object to be sent by client
     * @param e
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String tokenFor(Employee e) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now().plusHours(2).toInstant(UTC));
        return Jwts.builder()
                .setSubject(e.getEmail())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Optional<Employee> verify(String token) throws IOException, URISyntaxException {
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return employeeRepository.getUser(claims.getBody().getSubject().toString());
    }

    /**
     * Produces a secret key based on a strong password stored in file, which is then used to build JWT token
     */
    @Component
    private class SecretKeyProvider {
        public byte[] getKey() throws URISyntaxException, IOException {
            return Files.readAllBytes(Paths.get(this.getClass().getResource("/src/main/resources/jwt.key").toURI()));
        }
    }
}
