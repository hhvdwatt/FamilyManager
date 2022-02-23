package dk.vdw.familymanager.services;

import dk.vdw.familymanager.model.Media;
import dk.vdw.familymanager.repositories.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.Objects;

@Service
@Slf4j
public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public long saveObject(String filename, String mimeType,String description, byte[] object){
        if(StringUtils.isEmpty(filename) || (Objects.isNull(object)))
            throw new RuntimeException(
                    MessageFormat.format("Cannot save an empty binary object to the database!\n[{0}]",
                    500));
        var media = new Media();
        media.setFilename(filename);
        media.setMimeType(mimeType);
        media.setDescription(description);
        media.setSize(object.length);
        media.setBase64EncodedObject(Base64.getEncoder().encodeToString(object));
        var id = (mediaRepository.save(media)).getId();
        return id;
    }

    public long saveObject(Media media){
        if(Objects.isNull(media))
            throw new RuntimeException(
                    MessageFormat.format("Cannot save an empty binary object to the database!\n[{0}]",
                            500));
        var id = (mediaRepository.save(media)).getId();
        return id;
    }

    public Media getMediaById(long id){
        var result = mediaRepository.getMediaById(id);
        if(result.isEmpty())
            throw new RuntimeException(MessageFormat.format("Cannot retrieve [{0}] by id [{1}], does not exist!",
                    Media.class,id));
        return result.get();
    }

    public void deleteAll() {
        var numRecords = mediaRepository.markMediaForDeletion();
    }
}
