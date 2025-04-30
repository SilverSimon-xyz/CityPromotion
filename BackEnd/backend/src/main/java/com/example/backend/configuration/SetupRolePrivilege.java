package com.example.backend.configuration;

import com.example.backend.entities.users.Privilege;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.PrivilegeType;
import com.example.backend.entities.users.RoleType;
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
        this.loadRolesAndPrivileges();
    }

    private void loadRolesAndPrivileges() {

        PrivilegeType[] privilegeNames = new PrivilegeType[] {
                PrivilegeType.PRIVILEGE_LOGIN,
                PrivilegeType.PRIVILEGE_AUTHENTICATE,
                PrivilegeType.PRIVILEGE_READ,
                PrivilegeType.PRIVILEGE_AUTHORIZED,
                PrivilegeType.PRIVILEGE_PARTICIPATE,
                PrivilegeType.PRIVILEGE_REPORT,
                PrivilegeType.PRIVILEGE_SAVE_INFO,
                PrivilegeType.PRIVILEGE_DELETE,
                PrivilegeType.PRIVILEGE_UPDATE,
                PrivilegeType.PRIVILEGE_CREATE,
                PrivilegeType.PRIVILEGE_LOAD,
                PrivilegeType.PRIVILEGE_ENDING,
                PrivilegeType.PRIVILEGE_VALIDATOR,
                PrivilegeType.PRIVILEGE_SUPERVISOR
        };
        Arrays.stream(privilegeNames).forEach(privilegeName -> {
            Optional<Privilege> optional = privilegeRepository.findByName(privilegeName);
            optional.ifPresentOrElse(System.out::println, () -> {
                Privilege privilegeToCreate = new Privilege();
                privilegeToCreate.setName(privilegeName);
                privilegeRepository.save(privilegeToCreate);
            });
        });

        RoleType[] roleNames = new RoleType[] {
                RoleType.TOURIST,
                RoleType.CONTRIBUTOR,
                RoleType.ANIMATOR,
                RoleType.CURATOR,
                RoleType.ADMIN
        };

        Map<RoleType, String> roleDescriptionMap = Map.of(
                RoleType.TOURIST, "Default user role, have permission to read Elements (POI, TOUR, CONTEST, EVENT), report Contents and participate to Contest or Event",
                RoleType.CONTRIBUTOR, "Contributor role, have permission to create POI and TOUR, loading Contents to POI",
                RoleType.ANIMATOR, "Animator role, have permission to create, update and/or delete Contest, managing Contest for declaring a winner and Validate the Contents of the Contest",
                RoleType.CURATOR, "Curator role, have permission to load Contents to POI or Tour, validate pending or reported Contents",
                RoleType.ADMIN, "Administrator role, is the SUPERVISOR, could do everything and managing the users"
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
