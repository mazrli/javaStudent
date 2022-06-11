package student.javastudent.Registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import student.javastudent.Email.EmailSender;
import student.javastudent.Login.LoginUser;
import student.javastudent.Login.LoginUserService;
import student.javastudent.Login.UserRole;
import student.javastudent.Registration.ConfirmationToken.ConfirmationToken;
import student.javastudent.Registration.ConfirmationToken.ConfirmationTokenService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private LoginUserService loginUserService;
    private EmailValidator emailValidator;
    private ConfirmationTokenService confirmationTokenService;
    private EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = loginUserService.signUpUser(
                new LoginUser(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        UserRole.USER
                )
        );

        String link = "http://localhost:8080/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildConfirmEmailEmail(request.getUsername(), link));

        return token;
    }

    public String buildConfirmEmailEmail(String name, String link) {
        return "<p>Hello " + name + ".</p>" +
                "<p>Please confirm your email by pressing <a href=\"" + link + "\">here</a>.</p>" +
                "<p>Have a nice day.</p>";
    }

    public String confirmToken(String token) {

        ConfirmationToken confirmationToken1 = confirmationTokenService.findByToken(token);
        if (confirmationToken1 == null) {
            return "error";
        }
        LocalDateTime confirmedAt = confirmationToken1.getConfirmedAt();
        if (confirmedAt != null) {
            throw new IllegalStateException("token already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken1.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        loginUserService.enableLoginUser(
                confirmationToken1.getLoginUser().getEmail()
        );
        confirmationTokenService.updateConfirmedAt(token, LocalDateTime.now());

        return "confirm";
    }
}
