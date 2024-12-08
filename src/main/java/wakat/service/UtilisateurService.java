package wakat.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wakat.enumeration.StatutEnum;
import wakat.exception.UserNotFoundException;
import wakat.model.Utilisateur;
import wakat.repository.UtilisateurRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
@Service
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private static Logger logger = LoggerFactory.getLogger(UtilisateurService.class);

    /**
     * Service Utilisateur constructeur
     *
     * @param utilisateurRepository
     */
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

        /**
         Cette methode permet de creer un nouvel utilisateur en prenant en parametre un objet utilisateur
         et retourne l'objet creer
         */
        public Utilisateur ajouterUtilisateur (Utilisateur utilisateur){
            utilisateur.setStatutEnum(StatutEnum.ACTIF);
            utilisateur.setProjets(new HashSet<>());
            utilisateur.setRoles(new ArrayList<>());
            utilisateur.setTempstravail(new ArrayList<>());
            logger.info(utilisateur.toString());
            utilisateur.setStatutEnum(StatutEnum.ACTIF);
            return utilisateurRepository.save(utilisateur);
        }

    /**
     Cette methode permet de supprimer utilisateur en fonction de son id
     */
    public Utilisateur supprimerUtilisateur(UUID id){
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurById(id).get();
        logger.info(utilisateur.getNom());
        //on ne supprime pas définitivement l'utilisateur, on change juste son statut
        utilisateur.setStatutEnum(StatutEnum.INACTIF);
        return utilisateurRepository.save(utilisateur);

        }
    /**
     * methode de recherche d'un  utilisateur en prenant en paramètre id et return l'utilisateur par son ID
     */

        public Utilisateur consulterUtilisateur (UUID id){
            logger.info("Consulter utilisateur par id "+ id);
            return utilisateurRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user by id " + id + "was not found"));
        }


    }


