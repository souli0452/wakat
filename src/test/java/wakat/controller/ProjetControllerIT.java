package wakat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wakat.constant.UrlConstants;
import wakat.enumeration.StatutEnum;
import wakat.model.Projet;
import wakat.service.ProjetService;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjetControllerIT {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProjetService projetService;

    private Projet projet;
    /**
     * test d'integration d'enregistrement d'un projet
     */
//    @Test
    public void testEnregistrerProjetOK() throws Exception {
            /**
             * Condition prealable avant le test
             */
        Projet projet = Projet.builder()
                .code("SM227895")
                .libelle("Switch maker")
                .description("Projet de test de switch maker")
                .montant(new BigDecimal(10000))
                .organismeClient("Etat")
                .statutEnum(StatutEnum.ACTIF)
                .modules(null)
                .utilisateurs(null)
                .build();
        when(projetService.ajouterProjet(projet)).thenReturn(projet);
        /**
         * convertion du projet en String
         */
        String expected = om.writeValueAsString(projet);
        logger.info(expected);

        /**
         * lancez une requête POST via TesRestemplate à l'URI spécifiée et recuperer la réponse sous forme de chaîne.
         */
        ResponseEntity<String> response = restTemplate.postForEntity(UrlConstants.URL_PROJET, projet, String.class);
        logger.info(response.toString());
        logger.debug(response.toString());

        /**
         * Nous verifions si le code du HttpStatus est de 201
         */
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(projetService, times(1)).ajouterProjet(any(Projet.class));

    }


}
