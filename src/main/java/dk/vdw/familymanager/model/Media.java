package dk.vdw.familymanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.MimeType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "tblMedia")
public class Media {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @NotEmpty
    private String filename;
    @NotEmpty
    @Lob
    @Column(columnDefinition="CLOB")
    private String base64EncodedObject;
    @NotEmpty
    private String mimeType;
    private boolean deleted;
    private Timestamp deletionTime;
    private long size;

}
