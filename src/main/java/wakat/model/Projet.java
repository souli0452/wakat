package wakat.model;

import lombok.*;
import wakat.enumeration.StatutEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of ="code")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "projet")
@Builder
public class Projet extends AbstractId {

    @NotNull
    @Size(max=50)
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max=50)
    @Column(name = "libelle", nullable = false,unique = true)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "le libelle ne doit contenir que des caracteres")
    private String libelle;

    @NotNull
    @Size( max = 300)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @NotNull
    @Column(name = "organisme_client", nullable = false)
    private String organismeClient;

    @Column(name = "statut", nullable = false)
    private StatutEnum statutEnum;



    @OneToMany(mappedBy = "projets", cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    @Transient
    private Set<Module> modules;

    @Transient
    private Set<Utilisateur> utilisateurs;


    public Projet(String code, String libelle, String description, LocalDate dateDebut, LocalDate dateFin, StatutEnum statutEnum, BigDecimal montant, String organismeClient) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
        this.organismeClient = organismeClient;
        this.modules = modules;
        this.utilisateurs = utilisateurs;
        this.statutEnum = statutEnum;
    }
    @Override
    public String toString() {
        return "Projet{" +
                "code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", montant=" + montant +
                ", organismeClient='" + organismeClient + '\'' +
                ", statutEnum=" + statutEnum +
                ", id=" + id +
                '}';
    }
}
