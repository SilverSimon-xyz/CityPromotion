package com.example.backend.entities.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserToken")
@Table(name = "tokens")
public class RefreshToken {

    @Id
    @Column(name = "token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    private boolean revoked;
    private boolean expired;

    private Date issuedAt;
    private Date expiryAt;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

}
