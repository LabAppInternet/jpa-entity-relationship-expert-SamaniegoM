package cat.tecnocampus.notes2425.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="permission")
public record Permission(@ManyToOne @Id @JoinColumn(name="user_Id") UserLab owner,
                         @ManyToOne @Id @JoinColumn(name="note_Id") Note note,
                         @NotNull boolean canRead,
                         @NotNull boolean canEdit) {


}
