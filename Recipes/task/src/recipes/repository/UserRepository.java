package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.Usr;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Usr, Long> {

    Optional<Usr> findByEmail(String email);

}
