package wakat.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wakat.model.Module;
import wakat.service.ModuleService;
import java.util.UUID;
import static wakat.constant.UrlConstants.URL_MODULE;
import static wakat.constant.UrlConstants.URL_SLASH;

@RestController
@Api("module API")
@RequestMapping(URL_MODULE)

public class ModuleController {

    /*** Logger pour les logs*/
    private  Logger logger = LoggerFactory.getLogger(getClass());
    private final ModuleService moduleService;
    private  Module module;
    private  UUID uuid;
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    /**
    Endpoint de la méthode creer un module
     */

    @ApiOperation(value = "Récupère un module grâce à son ID à condition que celui-ci existe!", notes = "Renvoie un module selon l'ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Module Récupéré avec succès"),
            @ApiResponse(code = 201, message = "la requête est prise en charge"),
            @ApiResponse(code = 401, message = "l'accès non autorisé"),
            @ApiResponse(code = 403, message = "accès interdite"),
            @ApiResponse(code = 404, message = "Module n'a pas été trouvé")})

    @PostMapping
    public ResponseEntity<Module> creerModule(@RequestBody Module module){
        logger.info(" creation de  module dont le libellé est : "+module.getLibelle());
        Module moduleEnregistre=moduleService.creerModule(module);

        /*** recuperer un lien pour acceder au module cré*/
        moduleEnregistre.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).creerModule(new Module())).withRel(URL_MODULE +URL_SLASH + moduleEnregistre.getId())) ;

        return new ResponseEntity<>(moduleEnregistre, HttpStatus.OK);
    }

    // Les annotations concernant le Swagger
    @ApiOperation(value = "Récupère un module grâce à son ID à condition que celui-ci existe!", notes = "Renvoie un module selon l'ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Module Récupéré avec succès"),
            @ApiResponse(code = 201, message = "la requête est prise en charge"),
            @ApiResponse(code = 401, message = "l'accès non autorisé"),
            @ApiResponse(code = 403, message = "accès interdite"),
            @ApiResponse(code = 404, message = "Module n'a pas été trouvé")})

    /**
     ****** la méthode consulter un module par identifiant *****
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<Module> consulterModuleById(@PathVariable("id") UUID id){
        logger.info("Récuperation du module  dont l'id est" + uuid );
        Module module = moduleService.consulterModule(id);
        return new ResponseEntity<>(module, HttpStatus.OK);

    }

    /**
     * La méthode permet de mettre à jour les informations d'un module ******
     * @param id l'id dumodule à mettre à jour
     * @param module les informatons du module à mettre à jour
     */


    @ApiOperation(value = "Récupère un module grâce à son ID à condition que celui-ci existe!", notes = "Renvoie un module selon l'ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Module Récupéré avec succès"),
            @ApiResponse(code = 201, message = "la requête est prise en charge"),
            @ApiResponse(code = 401, message = "l'accès non autorisé"),
            @ApiResponse(code = 403, message = "accès interdite"),
            @ApiResponse(code = 404, message = "Module n'a pas été trouvé")})

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifierModule(@PathVariable UUID id,@RequestBody Module  module) {
        moduleService.modifierModule(module, id);
    }

}


