package com.example.backend.utility;

import com.example.backend.dto.PointOfInterestDto;
import com.example.backend.dto.RoleDto;
import com.example.backend.entities.PointOfInterest;
import com.example.backend.entities.Privilege;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.dto.Account;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Account mapUserToAccount(User user) {
        return Account.build(user);
    }

    public PointOfInterestDto mapPOIToDto(PointOfInterest pointOfInterest) {
        PointOfInterestDto pointOfInterestDto = new PointOfInterestDto();
        pointOfInterestDto.setId(pointOfInterest.getId());
        pointOfInterestDto.setName(pointOfInterest.getName());
        pointOfInterestDto.setDescription(pointOfInterest.getDescription());
        pointOfInterestDto.setAuthor(pointOfInterest.getAuthor().getName());
        pointOfInterestDto.setLatitude(pointOfInterest.getLatitude());
        pointOfInterestDto.setLongitude(pointOfInterest.getLongitude());
        pointOfInterestDto.setOpenTime(pointOfInterest.getOpenTime());
        pointOfInterestDto.setCloseTime(pointOfInterest.getCloseTime());
        pointOfInterestDto.setType(pointOfInterest.getType());
        return pointOfInterestDto;
    }

    public RoleDto mapRoleToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setDescription(role.getDescription());
        roleDto.setUsers(role.getUsers().stream().map(User::getEmail).toList());
        roleDto.setPrivileges(role.getPrivileges().stream().map(Privilege::getName).toList());
        return roleDto;
    }
}
