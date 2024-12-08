package wakat.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wakat.model.Utilisateur;
import wakat.service.UtilisateurService;

import java.util.UUID;

import static wakat.constant.UrlConstants.URL_UTILISATEUR;
import static wakat.constant.UrlConstants.URL_UTILISATEUR_LIEN;

@RestController
@RequestMapping(URL_UTILISATEUR)
public class UtilisateurController {
    private static Logger logger = LoggerFactory.getLogger(UtilisateurController.class);
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
/**
Cette methode permet de creer un nouvel utilisateur en retournant l'objet crée suivi
 d'un lien pour acceder à  l'element crée
 */
    @PostMapping
    public ResponseEntity<?> creerUtilisateur(@RequestBody Utilisateur utilisateur) {
    Utilisateur utilisateurSaved = utilisateurService.ajouterUtilisateur(utilisateur);
    utilisateurSaved.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).creerUtilisateur(new Utilisateur())).withRel(URL_UTILISATEUR_LIEN+ utilisateurSaved.getId()));
    logger.info(String.valueOf(utilisateurSaved.getLinks()));
    return ResponseEntity.status(HttpStatus.OK).body(utilisateurSaved);

}

/* méthode de suppression d'un utilisateur*/
@DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerUtilisateur(@PathVariable("id")UUID id) throws Exception {

        logger.info("Suppression d'un utilisateur"+id);
          Utilisateur utilisateur= utilisateurService.supprimerUtilisateur(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(utilisateur);// retourner l'utilisateur

    }
    /**
     * methode de recherche d'un  utilisateur en prenant en paramètre id et return le Status Ok
     */
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> consulterUtilisateur(@PathVariable("id") UUID id) {
        logger.info("Consulter utilisateur par id "+ id);
        Utilisateur utilsateur = utilisateurService.consulterUtilisateur(id);
        logger.info("consulter utilisateur : nom "+ utilsateur.getNom());
        return ResponseEntity.status(HttpStatus.OK).body(utilsateur);
    }
}
