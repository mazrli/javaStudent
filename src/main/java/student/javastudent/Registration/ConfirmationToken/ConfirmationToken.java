package student.javastudent.Registration.ConfirmationToken;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.javastudent.Login.LoginUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ConfirmationToken")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "login_user_id"
    )
    private LoginUser loginUser;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, LoginUser loginUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.loginUser = loginUser;
    }
}
