package student.javastudent.Registration.ConfirmationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.nio.file.OpenOption;
import java.time.LocalDateTime;
import java.util.Optional;
@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);


    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken t " +
            "SET t.confirmedAt = ?2 " +
            "WHERE t.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
