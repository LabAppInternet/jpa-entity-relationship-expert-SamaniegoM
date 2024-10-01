package cat.tecnocampus.notes2425.domain;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name="note")
public record Note(@Id @GeneratedValue(strategy =GenerationType.IDENTITY) Long id,
                   @ManyToOne @JoinColumn(name = "owner_id", nullable = false) UserLab owner,
                   @NotNull String title,
                   @NotNull String content,
                   @CreatedDate @NotNull LocalDateTime creationDate,
                   @ManyToMany @JoinTable(name = "Note_Tag", joinColumns = @JoinColumn(name = "note_Id"), inverseJoinColumns = @JoinColumn(name = "tag_name")) Set<Tag> tags) {




}
