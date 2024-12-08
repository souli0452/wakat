package wakat.controller;

import org.junit.jupiter.api.BeforeEach;
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
import wakat.model.Utilisateur;
import wakat.service.UtilisateurService;
import wakat.utilitaire.TestUtil;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UtilisateurController.class)
class UtilisateurControllerTest {

    @Autowired
private MockMvc mvc;
    @MockBean
    private UtilisateurService utilisateurService;


    private    UUID uuid;

    private static Logger logger = LoggerFactory.getLogger(UtilisateurControllerTest.class);


    Utilisateur utilisateur;
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
    @Test
    public  void creerUtilisateurMvcTestOK() throws Exception
    {
        /**
         * phase d'initialisation dans laquelle on moque le service et on retourne l'objet cree
         */
        given(utilisateurService.ajouterUtilisateur(utilisateur)).willReturn(utilisateur);
        logger.info(utilisateur.toString());
        /**
         * envoi dune requetes POST afin de creer un utilisateur avec mockmvc et verifiaction du statut code
         * et du resultat de la requete
         */
        mvc.perform( MockMvcRequestBuilders
                        .post(UrlConstants.URL_UTILISATEUR)
                        .content(TestUtil.asJsonString(utilisateur))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value("oumar123"))
                .andExpect(jsonPath("matricule").value("Bzrt6547890"))
                .andExpect(jsonPath("nom").value("sanou"))
                .andExpect(jsonPath("prenom").value("oumar"))
                .andExpect(jsonPath("email").value("oumar.sanou@gmail.com"));
        /**
         * verification pour savoir si on est rentre une et une seule fois dans la methode ajouterUtilisateur()
         *
         */
        verify(utilisateurService,times(1)).ajouterUtilisateur(utilisateur);
    }


    @Test
    public void testSupprimerUtilisateurOK() throws Exception {

       /**
        generer un identifiant pour l'utilisateur à supprimer
        */
        UUID uuid = UUID.randomUUID();
        /*
        on retourne l'utilisateur modifier lorsqu'on appelle la methode utilisateurService.supprimerUtilisateur(uuid)
         */
        Mockito.when(utilisateurService.supprimerUtilisateur(uuid)).thenReturn(utilisateur);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete(UrlConstants.URL_UTILISATEUR+"/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mvc.perform(mockRequest)
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        /*
        on verifie pour savoir si la methode supprimerUtilisateur(uuid) a été appelée une seule fois
         */
        verify(utilisateurService,times(1) ).supprimerUtilisateur(uuid);
    }




    /**
     * Ce test permet de rechercher un utilisateur
     */

    @Test
    public void testConsulterUtilisateurOk() throws Exception{

        uuid = UUID.randomUUID();
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

        utilisateur.setId(uuid);

        //On moque l' UtilisateurService et on retourne l'objet utilisateur

        given(utilisateurService.consulterUtilisateur(uuid)).willReturn(utilisateur);
        String url = UrlConstants.URL_UTILISATEUR_LIEN + uuid;
        logger.info(url);

         /* on  verifiaction du statut  et du resultat de la requete */

        mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nom").value("sanou"))
                        .andExpect(jsonPath("prenom").value("oumar"))
                                .andExpect(jsonPath("email").value("oumar.sanou@gmail.com"))
                                        .andExpect(jsonPath("login").value("oumar123"))
                                                .andExpect(jsonPath("matricule").value("Bzrt6547890"))
                                                                //.andExpect(jsonPath("projets").value(null))
                                                                       // .andExpect(jsonPath("roles").value(null))
                                                                                .andExpect(jsonPath("telephone").value(7276534490L));
                                                                                        //.andExpect(jsonPath("tempstravail").value(null));
        //verifier si la méthode consulterUtilisateur a été appelée une fois
    verify(utilisateurService, times(1)).consulterUtilisateur(utilisateur.getId());

    }


}