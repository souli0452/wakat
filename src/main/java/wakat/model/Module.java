package wakat.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode( of= "libelle")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "module")

public class Module extends AbstractId{

    @NotBlank(message = " le champ libelle ne doit pas etre vide")
    @Size(max = 50, message= "le libelle doit etre unique")
    @Column(name = "libelle" ,nullable = false, unique = true )
    private String libelle;

    @NotBlank(message = " le champ description ne doit pas etre vide")
    @Size(min = 5, max = 300,message = "la description doit avoir au minimum 5 caractère")
    @Column(name = "description", nullable =false )

    private String description;
    @ManyToMany
    @JoinTable(
            name = "module_projet",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn( name = "projet_id")
    )

    private Set<Projet> projets;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TempsTravail> tempstravail;

}

