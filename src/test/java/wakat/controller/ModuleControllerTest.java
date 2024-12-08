package wakat.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wakat.UnitTest;
import wakat.model.Module;
import wakat.service.ModuleService;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ModuleController.class)

class ModuleControllerTest {

    /*** logger pour gerer les logs*/
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ModuleService moduleService;
    private Module module;
    private UUID idModule;

    /**
     * Creer un module pour gerer tout les test
     */
    @BeforeEach
    public void setUp() {
        idModule = UUID.randomUUID();
        module = Module.builder()
                .libelle("test")
                .description("tesgdt")
                .projets(null)
                .tempstravail(null)
                .build();
        module.setId(idModule);
    }

    /**   test pour verifier si la reponse obtenue est celle qu'on s'attendais**/
    @Test
    public void testCreerModuleOk() throws Exception {
        logger.info("module crée: dont le libellé est " + module.getLibelle()+ " et la description "+module.getDescription());
        /**
         * précondition (recuperer le module cré)
         */
        given(moduleService.creerModule(module)).willReturn(module);
        /**
         * l'action (afficher le corps)
         */
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/module")
                                .content(UnitTest.asJsonString(module))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("libelle").value("test"))
                .andExpect(jsonPath("description").value("tesgdt"));
        /**
         *verifier le test
         */
        verify(moduleService, times(1)).creerModule(module);
    }

    /**   test pour verifier si le module existe**/

    @Test
    public void testConsulterModuleOK() throws Exception {
        
        logger.info("test d'intingration consulter le module dont l'id "+ idModule + " " +module.toString());

        // ici on moque moduleService  et on  retourner l'objet module
        given(moduleService.consulterModule(idModule)).willReturn(module);
        String url = "/api/module/" + idModule;

        //  ici on vérifie le statut et le resultat de la requete
        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("libelle").value("test"))
                .andExpect(jsonPath("description").value("tesgdt"));

        // ici on verifie si la méthode consulterModule est appele une fois
       verify(moduleService, times(1)).consulterModule(module.getId());

    }

//Test modifier module
    @Test
    public void testModifierModuleOk() throws Exception {
        logger.info("module modifiée : dont le libellé est " + module.getLibelle()+ " et la description "+module.getDescription());
        /**
         * précondition (recuperer le module modifiée)
         */
        given(moduleService.modifierModule(module)).willReturn(module);
        /**
         * l'action (afficher le corps)
         */
        mvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/module/" + idModule)
                                .content(UnitTest.asJsonString(module))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("libelle").value("test"))
                .andExpect(jsonPath("description").value("tesgdt"));
        /**
         *verifie si la méthode est appelée une seule fois
         */
        verify(moduleService, times(1)).modifierModule(module, idModule);
    }

}