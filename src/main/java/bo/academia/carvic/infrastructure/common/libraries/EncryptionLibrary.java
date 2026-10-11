package bo.academia.carvic.infrastructure.common.libraries;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("encryptionLibrary")
public class EncryptionLibrary {

    private final PasswordEncoder passwordEncoder;

    public EncryptionLibrary(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hash(String rawText) {
        return passwordEncoder.encode(rawText);
    }

    public boolean verify(String rawText, String hashedText) {
        return passwordEncoder.matches(rawText, hashedText);
    }
}
