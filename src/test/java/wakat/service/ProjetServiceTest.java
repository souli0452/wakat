package wakat.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import wakat.enumeration.StatutEnum;
import wakat.model.Projet;
import wakat.repository.ProjetRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProjetServiceTest {

    private Logger logger =  LoggerFactory.getLogger(getClass());

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetService projetService;

    private Projet projet;



    LocalDate localDate = LocalDate.now();
    LocalDate date =LocalDate.now().plusYears(1);

    private UUID uuid2 = UUID.randomUUID();


    @Test
    public void testConsulterProjet() {
        /**
         * la condition avant test
         */
        projet = Projet.builder()
                .code("djjf23D")
                .libelle("application de gestion de projet")
                .description("mise en place d'une application de gestion de temps")
                .dateDebut(LocalDate.now())
                .dateFin(LocalDate.now())
                .montant(new BigDecimal(100000))
                .organismeClient("uemoa")
                .modules(null)
                .utilisateurs(null)
                .build();
        logger.info("\n recuperation du projet dont l'id est: " + uuid2 +  "\n le est code :" + projet.getCode() + "\n le libelle est :"+ projet.getLibelle());

        given(projetRepository.findPprojetById(uuid2)).willReturn(Optional.of(projet));

        Optional<Projet> projet1 = projetService.consulterProjetById(uuid2);


        /**
         * on verifie que le projet recuperé n'est pas null
         */
        Assertions.assertNotNull(projet1);

        assertEquals("application de gestion de projet", projet.getLibelle());
        assertEquals("mise en place d'une application de gestion de temps", projet.getDescription());
        assertEquals(new BigDecimal(100000), projet.getMontant());
     verify(projetRepository,times(1)).findPprojetById(uuid2);

    }
    /**
     * la methode setUp s'execute lors qu'on demarre l'appli
     */
    @BeforeEach
    public void setup(){
        projet = Projet.builder()
                .code("SM227895")
                .libelle("Switch maker")
                .description("Projet de test de switch maker")
                .dateDebut(localDate)
                .dateFin(date)
                .montant(new BigDecimal(10000))
                .organismeClient("Etat")
                .statutEnum(StatutEnum.ACTIF)
                .modules(null)
                .utilisateurs(null)
                .build();
    }

    /**
     * test unitaire sur la creation d'un projet
     */
    @Test
    public void testCreationProjetOK(){
        /**
         * prerequis dans laquelle on mon mock le repository et on retourne le projet Creer
         */
        given(projetRepository.save(projet)).willReturn(projet);

        Projet projetSave = projetService.ajouterProjet(projet);
        logger.info(projetSave.toString());

        /**
         * Nous verifions si le projet creer n'est pas null
         */
        assertNotNull(projetSave);
        assertEquals("SM227895",projetSave.getCode());
        assertEquals("Switch maker",projetSave.getLibelle());
        assertEquals("Projet de test de switch maker",projetSave.getDescription());
        assertEquals(new BigDecimal(10000),projetSave.getMontant());
        assertEquals(StatutEnum.ACTIF,projetSave.getStatutEnum());
        assertEquals(localDate,projetSave.getDateDebut());
        assertEquals(date,projetSave.getDateFin());
        verify(projetRepository, times(1)).save(any(Projet.class));
    }


    /**
     * test unitaires verifians les exceptions
     */
    @Test
    public void exceptionProjetOK() {

        given(projetRepository.save(projet)).willThrow(DataIntegrityViolationException.class);

        /**
         * nous verifions si la methode contient des exceptions de type DataIntegrity
         */
        Throwable exception = assertThrows(DataIntegrityViolationException.class,
                () -> {
                    Projet enregistrerProjet = projetService.ajouterProjet(projet);
                }

        );
        logger.debug(exception.toString());


        verify(projetRepository, times(1)).save(any(Projet.class));
    }


    @Test
    public void testSupprimerProjetOK(){
        // arrange
        UUID uuid = UUID.randomUUID();
        given(projetRepository.findById(uuid)).willReturn(Optional.of(projet));

//        act and assert
        Projet supprimerProjet = projetService.supprimerProjet(uuid);
        logger.info("suppression du projet dont le libellé est: "+ projet.getLibelle());
//        verify

        verify(projetRepository, times(1)).findById(uuid);
    }


    }