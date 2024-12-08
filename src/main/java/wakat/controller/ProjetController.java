package wakat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wakat.model.Projet;
import wakat.service.ProjetService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static wakat.constant.UrlConstants.URL_PROJET;
import static wakat.constant.UrlConstants.URL_PROJET_LIEN;
@RestController
@RequestMapping(URL_PROJET)
public class ProjetController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private  ProjetService projetService;

    public ProjetController(ProjetService projetService) {

        this.projetService = projetService;


    }

    /**
     * Creation du enpoint DELETE pour supprimer un projet
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Projet supprimerProjet(@PathVariable("id") UUID id)
    {
        logger.info("suppression de projet par l'id");
        return  projetService.supprimerProjet(id);
    }

    /**
     * Creation du ENDPOINT  post pour creer un projet
      */
    @PostMapping
    public ResponseEntity<?> creerProjet(@RequestBody Projet projet) {
        logger.info(projet.toString());
        logger.info(projet.getMontant().toString());
        logger.info(String.valueOf((projet.getMontant().compareTo(BigDecimal.ZERO)>=1)));
        String res = String.valueOf((projet.getMontant().compareTo(BigDecimal.ZERO)>=1));


        if (res == "true") {
            Projet saveProjet = projetService.ajouterProjet(projet);
            saveProjet.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).creerProjet(new Projet())).withRel(URL_PROJET_LIEN + saveProjet.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(saveProjet);
            } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Montant inferieur a 0");

        }

        }

    /**
     * {@code GET  /projets/:id} : "Endpoint permettant de recuperer un projet à partir de son id.
     * @param id the id of the projet to retrieve.
     * @return the {@link org.springframework.http.ResponseEntity} with status
     * {@code 200 (OK)} si la recupration du projet a reussi.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Projet>> consulterProjetById(@PathVariable("id") UUID id) {

        Optional<Projet> projet = projetService.consulterProjetById(id);

        return  new ResponseEntity<>(projet,HttpStatus.OK);


    }


}
