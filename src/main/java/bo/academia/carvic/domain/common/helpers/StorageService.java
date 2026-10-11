package bo.academia.carvic.domain.common.helpers;

import java.io.InputStream;

public interface StorageService {
    String store(InputStream fileStream, String originalFilename, String directory);
    void delete(String fileUrl);
}
