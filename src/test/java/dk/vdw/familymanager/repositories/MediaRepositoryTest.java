package dk.vdw.familymanager.repositories;

import dk.vdw.familymanager.model.Media;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MediaRepositoryTest {

    @Autowired
    MediaRepository mediaRepository;
    Media imgMedia;
    String imgFile = "static/Screenshot 2022-02-10 at 14.56.45.png";

    @BeforeEach
    void setUp() throws IOException {
        var img = new ClassPathResource(imgFile);
        imgMedia = new Media();
        imgMedia.setId(1L);
        imgMedia.setFilename(img.getFilename());
        imgMedia.setDescription("Diablo 2 Resurrected Screenshot");
        imgMedia.setMimeType(MimeTypeUtils.IMAGE_PNG_VALUE);
        imgMedia.setSize(img.getFile().length());
        imgMedia.setBase64EncodedObject(Base64.getEncoder().encodeToString(img.getInputStream().readAllBytes()));
    }

    @AfterEach
    void tearDown(){
        mediaRepository.deleteAll();
        imgMedia = null;
    }

    @Test
    void saveMedia(){
        mediaRepository.save(imgMedia);
        var optionalMedia = mediaRepository.getMediaByFilename(imgMedia.getFilename());

        assertEquals(imgMedia.getMimeType(),optionalMedia.get().getMimeType());
        assertEquals(imgMedia.getSize(),optionalMedia.get().getSize());
        assertFalse(optionalMedia.get().isDeleted());
    }


    @Test
    void getMediaById() {
        //given
        var savedMedia = mediaRepository.save(imgMedia);
        var returnedMedia = mediaRepository.getMediaById(1L);

        //then
        assertTrue(returnedMedia.isPresent());
        assertEquals(savedMedia.getId(),returnedMedia.get().getId());
        assertEquals(savedMedia.getSize(),returnedMedia.get().getSize());
        assertEquals(savedMedia.getMimeType(),returnedMedia.get().getMimeType());
        assertEquals(savedMedia.getBase64EncodedObject(),returnedMedia.get().getBase64EncodedObject());
    }

    @Test
    void getMediaByFilename() {
        //given
        var savedMedia = mediaRepository.save(imgMedia);
        var returnedMedia = mediaRepository.getMediaByFilename(imgMedia.getFilename());

        //then
        assertTrue(returnedMedia.isPresent());
        assertEquals(savedMedia.getId(),returnedMedia.get().getId());
        assertEquals(savedMedia.getSize(),returnedMedia.get().getSize());
        assertEquals(savedMedia.getMimeType(),returnedMedia.get().getMimeType());
        assertEquals(savedMedia.getBase64EncodedObject(),returnedMedia.get().getBase64EncodedObject());
    }

    @Test
    void markMediaForDeletion() {
        //given
        var savedMedia = mediaRepository.save(imgMedia);

        //when
        mediaRepository.markMediaForDeletion();

        //then
        var returnedMedia = mediaRepository.getMediaById(savedMedia.getId());
        assertTrue(returnedMedia.get().isDeleted());
        assertNotNull(returnedMedia.get().getDeletionTime());
    }
}