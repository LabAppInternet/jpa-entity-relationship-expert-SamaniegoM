package cat.tecnocampus.notes2425.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="user_Lab")
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public record UserLab(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id,
        @Column(unique = true)@NotNull String username,
        @Column(unique = true)@NotNull String email) {
}
