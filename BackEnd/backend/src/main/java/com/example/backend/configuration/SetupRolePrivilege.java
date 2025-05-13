package com.example.backend.configuration;

import com.example.backend.entities.users.Role;
import com.example.backend.repository.RoleRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class SetupRolePrivilege implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRolesAndPrivileges();
    }

    private void loadRolesAndPrivileges() {

        String[] roleNames = new String[] {
                "TOURIST",
                "CONTRIBUTOR",
                "ANIMATOR",
                "CURATOR",
                "ADMIN"
        };

        Map<String, String> roleDescriptionMap = Map.of(
                "TOURIST", "Default user role, have permission to read Elements (POI, TOUR, CONTEST, EVENT), report Contents and participate to Contest or Event",
                "CONTRIBUTOR", "Contributor role, have permission to create POI and TOUR, loading Contents to POI",
                "ANIMATOR", "Animator role, have permission to create, update and/or delete Contest, managing Contest for declaring a winner and Validate the Contents of the Contest",
                "CURATOR", "Curator role, have permission to load Contents to POI or Tour, validate pending or reported Contents",
                "ADMIN", "Administrator role, is the SUPERVISOR, could do everything and managing the users"
        );

        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role role = new Role();
                role.setName(roleName);
                role.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(role);
            });
        });
    }

}
