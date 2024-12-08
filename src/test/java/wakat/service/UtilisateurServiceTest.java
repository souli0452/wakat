package wakat.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import wakat.model.Utilisateur;
import wakat.repository.UtilisateurRepository;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {
    @InjectMocks
    private UtilisateurService utilisateurService;
    private Utilisateur utilisateur;
    private static Logger logger = LoggerFactory.getLogger(UtilisateurServiceTest.class);


    @Mock
    UtilisateurRepository utilisateurRepository;

    @BeforeEach
    public void setup(){
        utilisateur = Utilisateur.builder()
                .nom("sanou")
                .prenom("oumar")
                .email("oumar.sanou@gmail.com")
                .login("oumar123")
                .matricule("Bzrt6547890")
                .motDePasse("Mous67@sanou!-")
                .projets(null)
                .roles(null)
                .telephone(7276534490L)
                .tempstravail(null)
                .build();
    }
    /**
     Test unitaire pour la creation d'un nouvel utilisateur
     */

    @Test
    public void testCreerUtilisateurOK() throws Exception
    {

        given(utilisateurRepository.save(utilisateur)).willReturn(utilisateur);

        Utilisateur utilisateurSaved = utilisateurService.ajouterUtilisateur(utilisateur);
        assertNotNull(utilisateurSaved);
        assertEquals("sanou", utilisateurSaved.getNom());
        assertEquals("oumar", utilisateurSaved.getPrenom());
        assertEquals("oumar.sanou@gmail.com", utilisateurSaved.getEmail());
        assertEquals("oumar123", utilisateurSaved.getLogin());
        assertEquals("Bzrt6547890", utilisateurSaved.getMatricule());
        assertEquals("Mous67@sanou!-", utilisateurSaved.getMotDePasse());

        verify(utilisateurRepository,times(1)).save(utilisateur);
    }

    /**

    Ce Test genere  une exception si l'on essaie d'enregistrer un utisateur existant
     */

    @Test
    public void testEnregistrementUtilisateurDejaExistantException() {

        given(utilisateurRepository.save(utilisateur)).willThrow(DataIntegrityViolationException.class);
        Throwable exception = assertThrows(DataIntegrityViolationException.class,
                () -> {
                    Utilisateur utilisateur1 = utilisateurService.ajouterUtilisateur(utilisateur);
                }
        );
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
    }

    @Test
    public void testUtilisateurInexistantException(){



        UUID uuid = UUID.randomUUID();
        logger.info("Test suppression si utilisateur inexistant"+uuid);
        doThrow(DataIntegrityViolationException.class).when(utilisateurRepository).findUtilisateurById(uuid);

        /*
                rechercher un utilisateur donné et vérifier s'il n'existe pas
         */
        assertThrows(DataIntegrityViolationException.class,()->{
            utilisateurService.supprimerUtilisateur(uuid);
            /*
                générer une exception si id inexistant
         */
        });
        /*
        on verifie pour savoir si la methode findUtilisateurById(uuid) a été appelée une seule fois
         */
        verify(utilisateurRepository,times(1)).findUtilisateurById(uuid);
    }

    /**
     * Ce test permet de rechercher un utilisateur
     */


    @DisplayName("RechercherUtilisateur")
    @Test
    public void testConsulterUtilisateurOk() {

        logger.info("Test consulter Utilisateur par ID");
        /* on moque UtilisateurRepositry et on return l'objet utilisateur
         */
        given(utilisateurRepository.findById(utilisateur.getId())).willReturn(Optional.of(utilisateur));
        Utilisateur UtilisateurTrouve = utilisateurService.consulterUtilisateur(utilisateur.getId());
        //verifier que la variable UtilisateurTrouve n'est pas null ca renverai true sinon le test echoue
        assertNotNull(UtilisateurTrouve);
        //verifier si la méthode a été appelée une fois
        verify(utilisateurRepository, times(1)).findById(utilisateur.getId());

    }


    @Test
    public void testSupprimerUtilisateurOK() throws Exception{
        UUID uuid = UUID.randomUUID();
        given(utilisateurRepository.findUtilisateurById(uuid)).willReturn(Optional.of(utilisateur));

        utilisateurService.supprimerUtilisateur(uuid);
        verify(utilisateurRepository, times(1)).findUtilisateurById(uuid);
    }
}

