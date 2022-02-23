package dk.vdw.familymanager.repositories;

import dk.vdw.familymanager.model.Media;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MediaRepository extends CrudRepository<Media,Long> {
    Optional<Media> getMediaById(long id);
    Optional<Media> getMediaByFilename(String filename);
    @Modifying(flushAutomatically = true)
    @Query("Update Media m set m.deleted = true, m.deletionTime= CURRENT_TIMESTAMP")
    int markMediaForDeletion();

}
