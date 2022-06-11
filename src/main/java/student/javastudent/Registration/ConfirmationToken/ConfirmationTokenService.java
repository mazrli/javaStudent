package student.javastudent.Registration.ConfirmationToken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken findByToken(String token) {
        Optional<ConfirmationToken> optionalToken = confirmationTokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            return optionalToken.get();
        }
        return null;
    }

    public void updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        confirmationTokenRepository.updateConfirmedAt(token, confirmedAt);
    }
}
