package wakat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wakat.model.Projet;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, UUID> {

   Optional<Projet> findPprojetById(UUID id);

}
