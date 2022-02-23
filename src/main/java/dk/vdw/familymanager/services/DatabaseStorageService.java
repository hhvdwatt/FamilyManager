package dk.vdw.familymanager.services;

import dk.vdw.familymanager.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
@Service
public class DatabaseStorageService implements StorageService{
    private final MediaService mediaService;

    public DatabaseStorageService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            mediaService.saveObject(file.getName(),
                    StringUtils.isNotBlank(file.getContentType())? file.getContentType():
                            MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE,
                    "NOT-SET",
                    file.getBytes());
        }
        catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {
        mediaService.deleteAll();
    }
}
