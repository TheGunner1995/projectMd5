package academy.service.role;

import academy.model.RoleName;
import academy.model.Roles;
import academy.service.IGenericService;

import java.util.Optional;

public interface IRoleService extends IGenericService<Roles, Long> {
    Optional<Roles> findByName(RoleName name);
}
