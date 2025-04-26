package com.example.backend.configuration;

import com.example.backend.entities.Privilege;
import com.example.backend.entities.Role;
import com.example.backend.entities.enums.PrivilegeType;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.repository.PrivilegeRepository;
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
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {
        this.loadPrivileges();
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

    private void loadPrivileges() {
        PrivilegeType[] privilegeNames = new PrivilegeType[] {PrivilegeType.READ_PRIVILEGE, PrivilegeType.WRITE_PRIVILEGE};

        Arrays.stream(privilegeNames).forEach(privilegeName -> {
            Optional<Privilege> optional = privilegeRepository.findByName(privilegeName);

            optional.ifPresentOrElse(System.out::println, () -> {
                Privilege privilegeToCreate = new Privilege();
                privilegeToCreate.setName(privilegeName);
                privilegeRepository.save(privilegeToCreate);
            });
        });
    }

}
