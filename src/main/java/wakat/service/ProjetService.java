package wakat.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wakat.enumeration.StatutEnum;
import wakat.model.Projet;
import wakat.repository.ProjetRepository;
import java.util.Optional;
import java.util.UUID;

/**
 * @version : 1.0
 * Copyright (c) 2022 switch_maker, All rights reserved.
 * @since : 2022/09/20 à 13:15
 */


@Service
public class  ProjetService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    /**
     * fonctionalité créer un  projet

     */
    public Projet ajouterProjet(Projet projet){
        projet.setStatutEnum(StatutEnum.ACTIF);
        return projetRepository.save(projet);
    }

    /**
     *
     * @param id
     * @return: permet de retourner un projet par son id
     */
    public Optional<Projet> consulterProjetById(UUID id) {
        return projetRepository.findPprojetById(id);
    }
    /**
     * fonctionalité supprimer un projet
     */

    public Projet supprimerProjet(UUID id){
        Projet projet= projetRepository.findById(id).get();
        projet.setStatutEnum(StatutEnum.INACTIF);
       return projetRepository.save(projet);
    }

}
