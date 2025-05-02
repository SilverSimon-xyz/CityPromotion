package com.example.backend.utility;

import com.example.backend.dto.*;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
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
        pointOfInterestDto.setCreatedAt(pointOfInterest.getCreatedAt());
        pointOfInterestDto.setUpdatedAt(pointOfInterest.getUpdatedAt());

        return pointOfInterestDto;
    }

    public RoleDto mapRoleToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setDescription(role.getDescription());

        roleDto.setUsers(role.getUsers()
                .stream()
                .map(User::getEmail)
                .toList());
        roleDto.setPrivileges(role.getPrivileges()
                .stream()
                .map(privilege -> privilege.getName().name())
                .toList());
        return roleDto;
    }

    public ContestDto mapContestToDto(Contest contest) {
        ContestDto contestDto = new ContestDto();

        contestDto.setId(contest.getId());
        contestDto.setName(contest.getName());
        contestDto.setDescription(contest.getDescription());
        contestDto.setAuthor(contest.getAuthor().getName());
        contestDto.setRules(contest.getRules());
        contestDto.setGoal(contest.getGoal());
        contestDto.setPrize(contest.getPrize());
        contestDto.setDeadline(contest.getDeadline());
        contestDto.setActive(contest.getActive());
        contestDto.setNumberOfParticipant(contest.getNumberOfParticipant());
        contestDto.setCreatedAt(contest.getCreatedAt());
        contestDto.setDataUpdated(contest.getDataUpdated());

        return contestDto;
    }

    public MultimediaContentDto mapContestToDto(MultimediaContent media) {
        MultimediaContentDto mediaDto = new MultimediaContentDto();

        mediaDto.setId(media.getId());
        mediaDto.setTitle(media.getTitle());
        mediaDto.setDescription(media.getDescription());
        mediaDto.setAuthor(media.getAuthor().getName());
        mediaDto.setData(media.getData());
        mediaDto.setCreatedAt(media.getCreatedAt());
        mediaDto.setUpdatedAt(media.getDataUpdated());
        mediaDto.setStatus(media.getStatus());

        return mediaDto;
    }
}
