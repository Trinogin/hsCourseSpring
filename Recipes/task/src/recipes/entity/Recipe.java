package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import recipes.utils.StringListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long recipeId;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String category;

    @Column
    private LocalDateTime date;

    @Column
    @NotBlank
    private String description;

    @Column
    @Size(min = 1)
    @Convert(converter = StringListConverter.class)
    private List<String> ingredients;

    @Column
    @Size(min = 1)
    @Convert(converter = StringListConverter.class)
    private List<String> directions;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usrId", nullable = false)
    private Usr usr;

}
