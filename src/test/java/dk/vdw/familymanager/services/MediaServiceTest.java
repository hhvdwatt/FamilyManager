package dk.vdw.familymanager.services;

import dk.vdw.familymanager.model.Media;
import dk.vdw.familymanager.repositories.MediaRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class MediaServiceTest {
    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    @Autowired
    private MediaService mediaService;
    String imgFile = "static/Screenshot 2022-02-10 at 14.56.45.png";
    String pdfFile = "static/jeff.nippard-fav.exercises.pdf";

    Media imgMedia,pdfMedia;


    @BeforeEach
    void setUp() throws IOException {
        Resource img = new ClassPathResource(imgFile);
        Resource pdf = new ClassPathResource(pdfFile);

        imgMedia = new Media();
        imgMedia.setFilename(img.getFilename());
        imgMedia.setDescription("Diablo 2 Resurrected Screenshot");
        imgMedia.setMimeType(MimeTypeUtils.IMAGE_PNG_VALUE);
        imgMedia.setBase64EncodedObject(Base64.getEncoder().encodeToString(img.getInputStream().readAllBytes()));

        pdfMedia = new Media();
        pdfMedia.setFilename(pdf.getFilename());
        pdfMedia.setDescription("Jeff Nippard");
        pdfMedia.setBase64EncodedObject(Base64.getEncoder().encodeToString(pdf.getInputStream().readAllBytes()));
    }

    @Test
    void when_saveObject_then_ObjectIsSaved() throws IOException {
        //given
        Resource img = new ClassPathResource(imgFile);
        Resource pdf = new ClassPathResource(pdfFile);

        //when
        var imgId = mediaService.saveObject(img.getFilename(), MimeTypeUtils.IMAGE_PNG_VALUE,
                "Diablo 2 Resurrected Screenshot",
                new ClassPathResource(imgFile).getInputStream().readAllBytes());
        var pdfId = mediaService.saveObject(pdf.getFilename(),MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE,
               "Jeff Nippard",
                new ClassPathResource(pdfFile).getInputStream().readAllBytes());

        //then
        assertTrue(imgId>0);
        assertTrue(pdfId>0);
    }

    @Test
    void getMediaById() {
    }

    @Test
    void deleteAll() {
    }
}