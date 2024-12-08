package wakat.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import wakat.enumeration.StatutEnum;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode( of= "matricule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "utilisateur")
@Builder

public class Utilisateur extends AbstractId{

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$" , message = "le nom doit contenir uniquement des caracteres")
    @Size(max = 50,message = "la taille des maximale est de 50 caractere")
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Size(max = 50,message ="la taille des maximale est de 50 caractere" )
    @Pattern(regexp = "^[a-zA-Z]+$" , message = "le prenom doit contenir uniquement des caracteres")
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @Email
    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @NotNull
    @Column(name = "login", nullable = false,unique = true)
    private String login;

    @NotNull
    @Setter(onMethod_ = @JsonProperty)
    @Getter(onMethod_ =@JsonIgnore )
    @Pattern(regexp = "^(?=.*[0-9])(?=.*^[a-zA-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}+$" , message = "Le mot de passe doit contenir au moins 8 caractères incluant au moins : 1 majuscule et un caractère spécial")
    @Column(name = "mot_de_passe", nullable = false)

    @JsonIgnore
    private  String motDePasse;


    @ManyToMany
    @JoinTable(
            name = "utilisateur_projet",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn( name = "projet_id")
    )
    private Set<Projet> projets;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TempsTravail> tempstravail;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role>  roles= new ArrayList<>();

    @Column(name = "statut", nullable = false)
    private StatutEnum statutEnum;


}
