package wakat.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import wakat.model.Module;
import wakat.repository.ModuleRepository;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
 public class ModuleServiceTest {

   /*** Logger pour gerer les logs*/
private Logger logger=LoggerFactory.getLogger(ModuleServiceTest.class);

    @Mock
    private ModuleRepository moduleRepository;
    @InjectMocks
    private ModuleService moduleService;
    private  Module module;
    private UUID uuid;


    /**
    Creation d'un module pour tout les test ce
    ceci sera excecuté avant le lancement des test a cause de @BeforeEach */
    @BeforeEach
    public void setUp(){
        uuid = UUID.randomUUID();
        module = Module.builder()
                .libelle("test")
                .description("tesgdt")
                .projets(null)
                .tempstravail(null)
                .build();
        module.setId(uuid);
    }

    /**
     *  tester la  creation d'un module et verifié si l'on obtient ce que l' on vient de creer
     */
    @Test
    public  void testCreerModuleOK() throws Exception{
        logger.info("module crée: dont le libellé est " + module.getLibelle()+ " et la description "+module.getDescription());

        /*** précondition (recuperer le module cré)*/
        given(moduleRepository.save(module)).willReturn(module);
        Module modulesave=moduleService.creerModule(module);

        /*** l'action (comparer les données recuperer)*/
        assertNotNull(modulesave);
        assertEquals("test",modulesave.getLibelle());
        assertEquals("tesgdt",modulesave.getDescription());
        assertEquals(null,modulesave.getProjets());
        assertEquals(null,modulesave.getTempstravail());

        /***verifier le test*/
         Mockito.verify(moduleRepository,new Times(1)).save(module);

    }

    /**
     * tester si le module creer n'est pas deja present si oui l'on lève une exception
     */
    @Test
    public  void testExceptionOK(){
        logger.info("verifié dabord si le module  avec le libellé " +module.getLibelle() + " et description " + module.getDescription() + " deja present");

        /*** précondition (recuperer le module cré)*/
        given(moduleRepository.save(module)).willThrow(DataIntegrityViolationException.class);

        /*** l'action (lever l'exception)*/
        Throwable exception =assertThrows(DataIntegrityViolationException.class,
                ()->{Module module1=moduleService.creerModule(module);});

        /***verifier le test*/
        Mockito.verify(moduleRepository,new Times(1)).save(any(Module.class));

    }

    @DisplayName("consuletrModule")
    @Test

    public void testConsulterModuleOK(){
        logger.info("test consulter  " +module.toString());

        // ici on moque moduleRepository  et on  retourner l'objet module
        given(moduleRepository.findModuleById(module.getId())).willReturn(Optional.ofNullable((module)));
        Module rechercherModule = moduleService.consulterModule(module.getId());

        //ici on verifie que la variable rechercherModule n'est pas null
        assertNotNull(rechercherModule);

        // ici on verifie si la méthode consulterModule est appele une fois
        verify(moduleRepository,times(1)).findModuleById(module.getId());
    }

    /**
     * **** tester la  modification d'un module et verifié si l'on obtient ce que l' on vient de modifiée
     * @throws Exception
     */

    @Test
    public  void testModifierModuleOk() throws Exception{
       logger.info("module modifiée : dont le libellé est " + module.getLibelle()+ " et la description "+module.getDescription());

        /*** précondition (recuperer le module modifiée)*/
        given(moduleRepository.save(module)).willReturn(module);
        Module moduleEnregistrer=moduleService.modifierModule(module);

        /*** l'action (comparer les données recuperer)*/
        assertNotNull(moduleEnregistrer);
        assertEquals("test",moduleEnregistrer.getLibelle());
        assertEquals("tesgdt",moduleEnregistrer.getDescription());
        assertEquals(null,moduleEnregistrer.getProjets());
        assertEquals(null,moduleEnregistrer.getTempstravail());

        /***verifier le test*/
        Mockito.verify(moduleRepository, new Times(1)).save(module);

    }

    /**
     * Test Unitaire qui verifie la modification d'un module qui existe déjà
     */

    @Test
    public void testUnitaireModifierModuleException() {

        given(moduleRepository.save(module)).willThrow(DataIntegrityViolationException.class);
        Throwable exception = assertThrows(DataIntegrityViolationException.class,
                () -> {
                    Module module1 = moduleService.modifierModule(module);
                }
        );

       // ici on verifie si la methode ModifierModule a été appelée une seule fois
        verify(moduleRepository, times(1)).save(any(Module.class));
    }
}