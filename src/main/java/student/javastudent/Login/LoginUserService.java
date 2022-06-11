package student.javastudent.Login;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import student.javastudent.Registration.ConfirmationToken.ConfirmationToken;
import student.javastudent.Registration.ConfirmationToken.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoginUserService implements UserDetailsService {
    private final LoginUserRepository loginUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return loginUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No such email.")
                );
    }

    public String signUpUser(LoginUser loginUser) {
        boolean userExists = loginUserRepository.findByEmail(loginUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already exists.");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(loginUser.getPassword());
        loginUser.setPassword(encodedPassword);

        loginUserRepository.save(loginUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                loginUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableLoginUser(String email) {
        loginUserRepository.enableLoginUser(email);
    }



}
