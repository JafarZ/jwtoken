package com.example.jwttoken.controller;

import com.example.jwttoken.model.User;
import com.example.jwttoken.model.WSUser;
import com.example.jwttoken.repository.WSUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TokenController {

    @Autowired
    WSUserRepository wsUserRepository;

    @PostMapping("getToken")
    public User login(@RequestParam("username") String username, @RequestParam("password") String password) {

        WSUser wsUser = wsUserRepository.findByUsername(username);

        User user = new User();

        if (getEncryptedPassword(password,wsUser.getHash()).equals(wsUser.getPassword()) ) {
            System.out.println("password match");
            String token = getJWTToken(username);

            user.setUser(username);
            user.setToken(token);
        } else {
            System.out.println("password doesn't match");
            user.setUser(username);
        }

        return user;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("myId")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    private static String get_SHA_512_SecurePassword(String password, byte[] salt) {


        String encryptedPassword = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = stringBuilder.toString();
        } catch (Exception ex) {
            //return PasswordEncryptionException;
            ex.printStackTrace();
        }
        return encryptedPassword;
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String getEncryptedPassword(String password, byte[] hash) {

        String encPassword;

        encPassword = get_SHA_512_SecurePassword(password, hash);

        return encPassword;
    }

}
