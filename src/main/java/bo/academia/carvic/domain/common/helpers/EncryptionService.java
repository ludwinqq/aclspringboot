package bo.academia.carvic.domain.common.helpers;

public interface EncryptionService {
    String hash(String rawText);
    boolean verify(String rawText, String hashedText);
}
