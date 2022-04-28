package com.zaheer.quizbackend.models.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="password_token")
public class PasswordToken {

    private static final int EXPIRATION = 1;

    public PasswordToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusDays(EXPIRATION);
        this.active = true;
    }

    @Id
    @SequenceGenerator(name="password_token_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "password_token_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private String token;
    private LocalDateTime expiryDate;
    private boolean active;
}
