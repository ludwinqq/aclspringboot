package bo.academia.carvic.infrastructure.common.libraries;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Component("fileUploadLibrary")
public class FileUploadLibrary {

    private final Path rootLocation = Paths.get("uploads");

    public String upload(InputStream fileStream, String originalFilename, String directory) {
        try {
            if (fileStream == null) throw new IllegalArgumentException("El archivo no puede estar vacío");
            
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueName = UUID.randomUUID().toString() + extension;
            
            Path destinationFolder = this.rootLocation.resolve(directory);
            Files.createDirectories(destinationFolder);
            
            Files.copy(fileStream, destinationFolder.resolve(uniqueName), StandardCopyOption.REPLACE_EXISTING);
            
            return directory + "/" + uniqueName; // Retorna la ruta relativa para guardar en BD
        } catch (IOException e) {
            throw new RuntimeException("Error en la librería al escribir el archivo en disco", e);
        }
    }
}
