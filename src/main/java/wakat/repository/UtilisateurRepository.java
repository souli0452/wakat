package wakat.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wakat.model.Utilisateur;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    void deleteById(UUID id);//définir la méthode deledeById(UUID id) pour s'injecter dans les services


    Optional<Utilisateur> findUtilisateurById(UUID id);//méthode rechercher un utilisateur en fonction de son id

}
