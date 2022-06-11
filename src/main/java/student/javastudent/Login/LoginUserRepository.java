package student.javastudent.Login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

    Optional<LoginUser> findByEmail(String email);



    @Transactional
    @Modifying
    @Query("UPDATE LoginUser u " +
            "SET u.enabled = TRUE " +
            "WHERE u.email = ?1")
    void enableLoginUser(String email);

}
