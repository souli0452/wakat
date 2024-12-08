package wakat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wakat.exception.ErreurSaisieException;
import wakat.model.Module;
import wakat.repository.ModuleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleService {

    /*** Logger pour gerer les logs*/
    private  Logger logger= LoggerFactory.getLogger(getClass());
    private ModuleRepository moduleRepository;


    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * methode créer un  module
     */
    public Module creerModule(Module module){
        logger.info(" creation de  module dont le libellé est : "+module.getLibelle());

        /*** lever une exception en cas de champs mal saisi*/
        if(!module.getLibelle().isEmpty()||module.getDescription().isEmpty()){
            return  moduleRepository.save(module);
        }else throw new ErreurSaisieException("M001","vous aviez mal saisi un champ");
    }

    /**
     ****** implementation de la methode consulterModule par identifiant  *****
     *
     */
    public Module consulterModule( UUID id){
        logger.info("recupper module dont  l'identifiant  est " +id);
        return moduleRepository.findModuleById(id).get();
    }

    /**
     *********Implementation de la méthode modifierModule
     *
     * @param module
     * @return
     */

  /*  public Module modifierModule(Module module) {
        Module modules = this.consulterModule(module.getId());
            return moduleRepository.save(module);

    }*/

    /**
     * Méthode pour enregistrer ou mettre à jour un module
     * @param module Le module qu'il faut enregistrer ou mettre à jour
     * @param idModule l'id du module à modifier (Si enregistrement, l'id est null)
     */
    public void modifierModule(Module module, UUID idModule){

        // Cas de mise à jour d'un module
        if (null != idModule) {
            Optional<Module> moduleOptional = moduleRepository.findModuleById(idModule);

            // Vérification que le module existe en BDD
            if (moduleOptional.isPresent()) {
                Module moduleAEnregistre = moduleOptional.get();

                // Vérification contraintes métiers
                if (!module.getLibelle().isEmpty() || module.getDescription().isEmpty()) {
                    modifierInfosModule(module, moduleAEnregistre);
                    moduleRepository.save(moduleAEnregistre);
                } else {
                    throw new ErreurSaisieException("M001", "Vous avez mal renseigner un champs");
                }

            } else {
                // Problème, il n'y a pas de module en BDD pour l'id passé en paramètre
                throw new RuntimeException("module non present");
            }
        }

    }

    private void modifierInfosModule(Module module, Module moduleAEnregistre) {
        moduleAEnregistre.setDescription(module.getDescription());
        moduleAEnregistre.setLibelle(module.getLibelle());
    }


}