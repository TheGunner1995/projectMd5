package academy.repository.role;

import academy.model.RoleName;
import academy.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IRoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleName name);
}
