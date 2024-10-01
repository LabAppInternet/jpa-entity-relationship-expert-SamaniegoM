package cat.tecnocampus.notes2425.domain;

import jakarta.persistence.*;

@Entity
@Table (name="tag")
public record Tag(@Id String name) {


}
