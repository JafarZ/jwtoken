package com.example.jwttoken.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ws_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WSUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long Id;

    @Column(name= "user_name", nullable = false, unique = true)
    private String username;

    @Column(name= "password", nullable = false)
    private String password;

    @Column(name= "is_disabled", nullable = false)
    private boolean isDisabled;

    @Column(name= "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name= "is_locked", nullable = false)
    private boolean isLocked;

    @Column(name ="hash", nullable = false)
    private byte[] hash;
}
