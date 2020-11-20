package pl.Lukasz.charity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @Column(unique = true)
    @Email
    String email;
    String password;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> role;
    Boolean isBlocked = false;
}
