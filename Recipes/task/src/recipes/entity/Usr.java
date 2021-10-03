package recipes.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long usrId;

    @Column
    @NotBlank
    @Email
    private String email;

    @Column
    @NotBlank
    @Size(min = 8)
    private String password;

    @Column
    private boolean isActive;

    @Column
    private String roles;

    @OneToMany(mappedBy = "usr")
    @ToString.Exclude
    private List<Recipe> userRecipes;

}
