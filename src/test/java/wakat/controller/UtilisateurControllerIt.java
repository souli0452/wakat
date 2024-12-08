package wakat.controller;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wakat.constant.UrlConstants;
import wakat.model.Utilisateur;
import wakat.service.UtilisateurService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UtilisateurControllerIt {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;






    /**
     * test d'enregistrement d'un utilisateur en verifiant si le code du status est le meme
     */

//    @Test
    public void testItEnregistrerUtilisateurtOK() throws Exception {

/**
* Construction d'un objet utilsateur a partir de lombook
*/
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

        when(utilisateurService.ajouterUtilisateur(utilisateur)).thenReturn(utilisateur);
/**
 * ici on envoie une requete POST via TestRestemplate et on recupere la reponse
 */
        ResponseEntity<String> response = testRestTemplate.postForEntity(UrlConstants.URL_UTILISATEUR,utilisateur, String.class);
/**
 *  on verife si le statut de la reponse est du  200
 */
     assertEquals(HttpStatus.OK, response.getStatusCode());


        verify(utilisateurService, times(1)).ajouterUtilisateur(any(Utilisateur.class));

    }

}


