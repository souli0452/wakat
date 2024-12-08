package wakat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wakat.constant.UrlConstants;
import wakat.enumeration.StatutEnum;
import wakat.model.Projet;
import wakat.service.ProjetService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Copyright (c) 2022 switch_maker, All rights reserved.
 * @since : 2022/09/20 à 13:15
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjetController.class)
public class ProjetControllerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockMvc mvc;


    @MockBean
    private ProjetService projetService;

    private Projet projet;

    private UUID uuid2;
    @Autowired private ObjectMapper mapper;

    LocalDate localDate = LocalDate.now();
    LocalDate date =LocalDate.now().plusYears(1);


    @BeforeEach
    public void setUp() {
        /**
         * la condition avant le test
         */
        uuid2 = UUID.randomUUID();
        projet= Projet.builder()
                .code("hfj23j" )
                .libelle("application de gestion projet")
                .description("mise en place d'une application de gestion de temps")
                .dateDebut(LocalDate.now())
                .dateFin(LocalDate.now())
                .montant(new BigDecimal(10000))
                .organismeClient("Etat")
                .statutEnum(StatutEnum.ACTIF)
                .modules(null)
                .utilisateurs(null)
                .build();
    }

    /**
     * test unitaire sur la consultation d'un projet
     * @throws Exception
     */
    @Test
    public void testConsulterProjet() throws Exception {
        logger.info("\n recuperation du projet dont l'id est: " + uuid2 +  "\n le est code :" + projet.getCode() + "\n le libelle est :"+ projet.getLibelle());

        UUID uuid2 = UUID.randomUUID();
        given(projetService.consulterProjetById(uuid2)).willReturn(Optional.of((projet)));
        String url ="/api/projet/"+uuid2;

        /**
         * on lance la requete json GET à l'URI ET on attend la reponse sous format json
         */

        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect( jsonPath("code").value(projet.getCode()))
                .andExpect( jsonPath("libelle").value(projet.getLibelle()))
                .andExpect( jsonPath("description").value(projet.getDescription()));

        verify(projetService,times(1)).consulterProjetById(uuid2);

    }

    public static String asJsonString (final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * test unitaire pour la creation d'un projet
     */
   @Test
   public void testCreationProjetOK() throws  Exception{
       /**
        * Condition prealable avant le test
        */
      Projet projet = Projet.builder()
               .code("SM227895")
               .libelle("Switch")
               .description("Projet de test de switch maker")
               .montant(new BigDecimal(10000))
               .organismeClient("Etat")
               .statutEnum(StatutEnum.ACTIF)
               .modules(null)
               .utilisateurs(null)
               .build();

       Mockito.when(projetService.ajouterProjet(projet)).thenReturn(projet);
       /**
        * lancez une requête JSON POST à l'URI spécifiée.
        */
       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(UrlConstants.URL_PROJET)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(this.mapper.writeValueAsString(projet));
       logger.debug(mockRequest.toString());

       /**
        * On inspect si le status est de type Create, que le champ code est le meme lors de la creation ainsi que le libelle
        */
       mvc.perform(mockRequest)
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("code").value("SM227895"))
               .andExpect(jsonPath("libelle").value("Switch"));
       logger.info(projet.toString());
       verify(projetService,times(1) ).ajouterProjet(projet);



    }

    @DisplayName("JUnit test pour la suppression d'un projet")
    @Test
    public void testSupprimerProjetOK() throws Exception {

        // arrange
        UUID uuid = UUID.randomUUID();
        Mockito.when(projetService.supprimerProjet(uuid)).thenReturn(projet);

//act and assert
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete(UrlConstants.URL_PROJET+"/"+ uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(projet));

        mvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

//        verify
        verify(projetService,times(1) ).supprimerProjet(uuid);
    }
}