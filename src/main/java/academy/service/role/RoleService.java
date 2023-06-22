package academy.service.role;

import academy.model.RoleName;
import academy.model.Roles;
import academy.repository.role.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final IRoleRepository roleRepository;
    @Override
    public Iterable<Roles> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Roles> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Roles save(Roles roles) {
        return roleRepository.save(roles);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<Roles> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
