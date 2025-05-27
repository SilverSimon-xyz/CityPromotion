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
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

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
                "TOURIST", "Default user role, have permission to read Point of Interest, Contest e Content and can participate to Contest.",
                "CONTRIBUTOR", "Contributor role, have permission to create Point of Interest and loading Contents to Point of Interest",
                "ANIMATOR", "Animator role, have permission to create, update and/or delete Contest, managing Contest for declaring a winner and Validate the Participants of the Contest",
                "CURATOR", "Curator role, have permission to load Contents to Point of Interest, validate Contents that are in pending state",
                "ADMIN", "Administrator role, can create, read, update and delete Point of Interest, Contents and Contest plus manage the Users and Roles"
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
