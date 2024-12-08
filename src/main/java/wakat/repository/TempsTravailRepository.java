package wakat.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wakat.model.TempsTravail;

import java.util.UUID;
@Repository
public interface TempsTravailRepository extends JpaRepository<TempsTravail, UUID> {
}
