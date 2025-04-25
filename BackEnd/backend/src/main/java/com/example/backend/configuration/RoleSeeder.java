package com.example.backend.configuration;

import com.example.backend.entities.Role;
import com.example.backend.entities.RoleType;
import com.example.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleType[] roleNames = new RoleType[] {RoleType.TOURIST, RoleType.CONTRIBUTOR, RoleType.ANIMATOR, RoleType.CURATOR, RoleType.ADMIN};
        Map<RoleType, String> roleDescriptionMap = Map.of(
                RoleType.TOURIST, "Default user role",
                RoleType.CONTRIBUTOR, "Contributor role",
                RoleType.ANIMATOR, "Animator role",
                RoleType.CURATOR, "Curator role",
                RoleType.ADMIN, "Administrator role"
        );

        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Role> optional = roleRepository.findByName(roleName);

            optional.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(roleToCreate);
            });
        });
    }
}
